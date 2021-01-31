package com.ronnie.topscore.controller;

import com.ronnie.topscore.assembler.ScoreAssembler;
import com.ronnie.topscore.entity.Score;
import com.ronnie.topscore.repository.IScoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ScoreController.class, ScoreAssembler.class})
@WebMvcTest
public class ScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IScoreRepository repository;

    @Test
    void testAddScore() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double score = 10.0;
        String dateStr = "04 Feb 2021 15:12:00";
        Date date = formatter.parse(dateStr);
        String player = "player";
        Score scoreEntity = new Score(score, date, player);
        scoreEntity.setId(1L);

        String createJson = String.format("{\"player\":\"%s\",\"score\":%f,\"dateTime\":\"%s\"}",
                player,
                score,
                dateStr);

        Mockito.lenient().when(repository.save(any(Score.class))).thenReturn(scoreEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/score")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/score/1"));
    }

    @Test
    void testGetScore() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double score = 10.0;
        String dateStr = "04 Feb 2021 15:12:00";
        Date date = formatter.parse(dateStr);
        String player = "player";
        Score scoreEntity = new Score(score, date, player);
        scoreEntity.setId(1L);

        String createJson = String.format("{\"player\":\"%s\",\"score\":%f,\"dateTime\":\"%s\"}",
                player,
                score,
                dateStr);

        System.out.println(scoreEntity);
        Mockito.lenient().when(repository.findById(any(Long.class))).thenReturn(Optional.of(scoreEntity));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/score/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc. perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/score/1"));
    }

    @Test
    void test_getScoreList_empty() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double score = 10.0;
        List<Score> scoreList = new ArrayList<>();
        String createJson = "{}";
        Page page = new PageImpl(scoreList);

        Mockito.lenient().when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/score")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded").doesNotHaveJsonPath())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/score"))
                .andExpect(jsonPath("page.size").value(0))
                .andExpect(jsonPath("page.totalElements").value(0))
                .andExpect(jsonPath("page.totalPages").value(1))
                .andExpect(jsonPath("page.number").value(0));
    }

    @Test
    void test_getScoreList() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double score = 10.0;
        List<Score> scoreList = new ArrayList<>();
        String createJson = "{}";
        Page page;

        for(int i = 0; i < 5; i++) {
            String dateStr = "04 Feb 2021 15:12:00";
            Date date = formatter.parse(dateStr);
            String player = "player";
            Score scoreEntity = new Score(score, date, player);
            scoreEntity.setId((long) i);

            scoreList.add(scoreEntity);
        }
        page = new PageImpl(scoreList);

        Mockito.lenient().when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/score")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.scoreList").isArray())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/score"))
                .andExpect(jsonPath("page.size").value(5))
                .andExpect(jsonPath("page.totalElements").value(5))
                .andExpect(jsonPath("page.totalPages").value(1))
                .andExpect(jsonPath("page.number").value(0));
    }

    @Test
    void test_getPlayerHistory_playerNoRecord() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double[] score = new Double[]{10.0, 5.0, 15.0, 12.0, 14.0};
        List<Score> scoreList = new ArrayList<>();
        String createJson = "{}";

        Mockito.lenient().when(repository.findAll(any(Specification.class))).thenReturn(new ArrayList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/player/player")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void test_getPlayerHistory() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Double[] score = new Double[]{10.0, 5.0, 15.0, 12.0, 14.0};
        List<Score> scoreList = new ArrayList<>();
        String createJson = "{}";

        for(int i = 0; i < 5; i++) {
            String dateStr = "04 Feb 2021 15:12:00";
            Date date = formatter.parse(dateStr);
            String player = "player";
            Score scoreEntity = new Score(score[i], date, player);
            scoreEntity.setId((long) i);

            scoreList.add(scoreEntity);
        }

        Mockito.lenient().when(repository.findAll(any(Specification.class))).thenReturn(scoreList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/player/player")
                .accept(MediaType.APPLICATION_JSON)
                .content(createJson)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("topScore.score").value(15.0))
                .andExpect(jsonPath("lowScore.score").value(5.0))
                .andExpect(jsonPath("averageScore").value(11.2))
                .andExpect(jsonPath("playerScoreList").isArray());
    }
}
