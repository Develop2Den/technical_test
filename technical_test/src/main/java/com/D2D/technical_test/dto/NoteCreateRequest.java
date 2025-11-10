package com.D2D.technical_test.dto;

import com.D2D.technical_test.model.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class NoteCreateRequest {
    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "text must not be blank")
    private String text;

    private Set<Tag> tags;
}
