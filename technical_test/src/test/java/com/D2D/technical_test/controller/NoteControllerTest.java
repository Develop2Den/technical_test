package com.D2D.technical_test.controller;

import com.D2D.technical_test.dto.*;
import com.D2D.technical_test.model.Tag;
import com.D2D.technical_test.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    @Test
    void create_whenValid_returns201() throws Exception {
        NoteCreateRequest req = new NoteCreateRequest();
        req.setTitle("Title");
        req.setText("Some text");
        req.setTags(EnumSet.of(Tag.BUSINESS));

        NoteResponse resp = new NoteResponse("1", "Title", Instant.now(), "Some text", EnumSet.of(Tag.BUSINESS));
        Mockito.when(noteService.create(Mockito.any())).thenReturn(resp);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/notes/1")))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.title", is("Title")));
    }

    @Test
    void create_whenInvalid_returns400() throws Exception {
        NoteCreateRequest req = new NoteCreateRequest();
        req.setTitle(" ");
        req.setText("");

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.title", notNullValue()))
                .andExpect(jsonPath("$.fields.text", notNullValue()));
    }

    @Test
    void list_returnsPagedListItems() throws Exception {
        PagedResponse<NoteListItemResponse> page = new PagedResponse<>(
                List.of(new NoteListItemResponse("1", "T1", Instant.now())),
                0, 10, 1, 1, true
        );
        Mockito.when(noteService.list(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title", is("T1")));
    }

    @Test
    void stats_returnsMap() throws Exception {
        LinkedHashMap<String, Integer> stats = new LinkedHashMap<>();
        stats.put("note", 2);
        stats.put("is", 1);
        Mockito.when(noteService.stats("1")).thenReturn(stats);

        mockMvc.perform(get("/api/notes/1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note", is(2)))
                .andExpect(jsonPath("$.is", is(1)));
    }
}
