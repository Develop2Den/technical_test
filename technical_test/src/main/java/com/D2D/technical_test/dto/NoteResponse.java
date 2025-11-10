package com.D2D.technical_test.dto;

import com.D2D.technical_test.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
public class NoteResponse {
    private String id;
    private String title;
    private Instant createdDate;
    private String text;
    private Set<Tag> tags;
}
