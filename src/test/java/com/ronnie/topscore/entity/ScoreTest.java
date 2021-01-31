package com.ronnie.topscore.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
@DirtiesContext
public class ScoreTest {

    TestEntityManager entityManager;

    public ScoreTest(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void testAddScore() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        Double score = 10.0;
        Date date = formatter.parse("4 Feb 2021 15:12:00");
        String player = "player";
        Score scoreEntity = new Score(score, date, player);

        entityManager.persist(scoreEntity);

        Assertions.assertEquals(1, scoreEntity.getId());
    }
}
