package com.ronnie.topscore.controller;

import com.ronnie.topscore.assembler.ScoreAssembler;
import com.ronnie.topscore.entity.Score;
import com.ronnie.topscore.model.PlayerHistoryResponseModel;
import com.ronnie.topscore.model.ScoreTimeModel;
import com.ronnie.topscore.repository.IScoreRepository;
import com.ronnie.topscore.exception.ScoreNotFoundException;
import com.ronnie.topscore.model.ScoreListRequestModel;
import com.ronnie.topscore.specification.ScoreSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScoreController {
    private final IScoreRepository scoreRepository;

    private final ScoreAssembler assembler;

    ScoreController(IScoreRepository scoreRepository, ScoreAssembler assembler) {
        this.scoreRepository = scoreRepository;
        this.assembler = assembler;
    }

    /**
     * Create score
     * @param score containing player, score and time.
     * @return score object with score ID.
     */
    @PostMapping("/score")
    public EntityModel<Score> createScore(@RequestBody Score score) {
        return assembler.toModel(scoreRepository.save(score));
    }

    /***
     * Gets the list of scores depending on the param combination
     * @param model containing the conditions
     * @return list of scores
     */
    @GetMapping("/score")
    public PagedModel getScoreList(@RequestBody ScoreListRequestModel model,
                                   Pageable pageable,
                                   PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Score> scores = scoreRepository.findAll(
                Specification
                        .where(model.playerList == null ? null
                                : ScoreSpecifications.playerIsInListIgnoreCase(model.playerList))
                        .and(model.startDate == null ? null
                                : ScoreSpecifications.afterStartDate(model.startDate))
                        .and(model.endDate == null ? null
                                : ScoreSpecifications.beforeEndDate(model.endDate)),
                pageable
        );

        return pagedResourcesAssembler.toModel(scores);
    }

    /**
     * Get Score by ID
     * @param id of the score
     * @return score data
     */
    @GetMapping("/score/{id}")
    public EntityModel<Score> getScore(@PathVariable Long id) {
        return assembler.toModel(scoreRepository.findById(id)
                .orElseThrow(() -> new ScoreNotFoundException(id)));
    }

    /***
     * Gets player's history containing top score, low score, average and the list of scores.
     * @param player name
     * @return player's history
     */
    @GetMapping("/player/{player}")
    PlayerHistoryResponseModel getPlayerHistory(@PathVariable String player) {
        Specification<Score> playerSpecification = ScoreSpecifications.playerEqualsIgnoreCase(player);
        List<Score> scoreList = scoreRepository.findAll(Specification.where(playerSpecification));

        if(scoreList.size() > 0) {
            Score topScore = Collections.max(scoreList, Comparator.comparing(t -> t.getScore()));
            Score lowScore = Collections.min(scoreList, Comparator.comparing(l -> l.getScore()));
            Double averageScore = scoreList.stream().mapToDouble(s -> s.getScore()).average().getAsDouble();

            List<ScoreTimeModel> playerHistoryList = scoreList.stream()
                    .map(s -> new ScoreTimeModel(s.getScore(), s.getDateTime()))
                    .collect(Collectors.toList());

            return new PlayerHistoryResponseModel(
                    new ScoreTimeModel(topScore.getScore(), topScore.getDateTime()),
                    new ScoreTimeModel(lowScore.getScore(), lowScore.getDateTime()),
                    averageScore, playerHistoryList);
        }

        return null;
    }

    /**
     * Delete Score by ID
     * @param id of the score
     */
    @DeleteMapping("/score/{id}")
    public void deleteScore(@PathVariable Long id) {
        scoreRepository.deleteById(id);
    }
}
