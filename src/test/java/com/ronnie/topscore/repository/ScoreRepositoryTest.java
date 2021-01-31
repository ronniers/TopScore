package com.ronnie.topscore.repository;

import com.ronnie.topscore.entity.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
@DirtiesContext
public class ScoreRepositoryTest {

    IScoreRepository scoreRepository;

    public ScoreRepositoryTest(IScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Test
    void testSaveScore() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        Double score = 10.0;
        Date date = formatter.parse("4 Feb 2021 15:12:00");
        String player = "player";
        Score scoreEntity = new Score(score, date, player);

        Score retScore = scoreRepository.save(scoreEntity);

        Assertions.assertNotNull(retScore);
        Assertions.assertEquals(1, retScore.getId());
        Assertions.assertEquals(date, retScore.getDateTime());
        Assertions.assertEquals(score, retScore.getScore());
        Assertions.assertEquals(player, retScore.getPlayer());
    }
}
