package com.D2D.technical_test.service;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteServiceTest {

    @Test
    void computeWordStats_countsAndSortsProperly() {
        String text = "note is just a note";
        LinkedHashMap<String, Integer> stats = NoteService.computeWordStats(text);
        assertThat(stats).containsExactly(
                org.assertj.core.api.Assertions.entry("note", 2),
                org.assertj.core.api.Assertions.entry("a", 1),
                org.assertj.core.api.Assertions.entry("is", 1),
                org.assertj.core.api.Assertions.entry("just", 1)
        );
    }

    @Test
    void computeWordStats_handlesPunctuationAndCase() {
        String text = "Note, NOTE! note's; notes";
        LinkedHashMap<String, Integer> stats = NoteService.computeWordStats(text);
        assertThat(stats.get("note")).isEqualTo(2);
        assertThat(stats.get("note's")).isEqualTo(1);
        assertThat(stats.get("notes")).isEqualTo(1);
    }

    @Test
    void computeWordStats_emptyText_returnsEmptyMap() {
        LinkedHashMap<String, Integer> stats = NoteService.computeWordStats("   ");
        assertThat(stats).isEmpty();
    }
}
