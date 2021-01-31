package com.ronnie.topscore.assembler;

import com.ronnie.topscore.controller.ScoreController;
import com.ronnie.topscore.entity.Score;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class ScoreAssembler implements RepresentationModelAssembler<Score, EntityModel<Score>> {

    @Override
    public EntityModel<Score> toModel(Score score) {
        System.out.println(score);
        return EntityModel.of(score, //
                linkTo(methodOn(ScoreController.class).getScore(score.getId())).withSelfRel());
    }
}
