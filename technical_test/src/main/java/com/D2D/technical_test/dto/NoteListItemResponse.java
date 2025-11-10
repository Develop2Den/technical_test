package com.D2D.technical_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class NoteListItemResponse {
    private String id;
    private String title;
    private Instant createdDate;
}
