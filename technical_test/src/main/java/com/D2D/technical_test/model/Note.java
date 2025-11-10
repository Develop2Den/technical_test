package com.D2D.technical_test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private String title;

    private Instant createdDate;

    private String text;

    private Set<Tag> tags;
}
