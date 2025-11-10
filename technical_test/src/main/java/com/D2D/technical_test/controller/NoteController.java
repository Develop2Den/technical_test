package com.D2D.technical_test.controller;

import com.D2D.technical_test.dto.*;
import com.D2D.technical_test.model.Tag;
import com.D2D.technical_test.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Validated
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> create(@Valid @RequestBody NoteCreateRequest request) {
        NoteResponse created = noteService.create(request);
        return ResponseEntity.created(URI.create("/api/notes/" + created.getId())).body(created);
    }

    @GetMapping
    public PagedResponse<NoteListItemResponse> list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "tag", required = false) Tag tag
    ) {
        return noteService.list(page, size, tag);
    }

    @GetMapping("/{id}")
    public NoteResponse get(@PathVariable String id) {
        return noteService.get(id);
    }

    @PutMapping("/{id}")
    public NoteResponse update(@PathVariable String id, @Valid @RequestBody NoteUpdateRequest request) {
        return noteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        noteService.delete(id);
    }

    @GetMapping("/{id}/stats")
    public LinkedHashMap<String, Integer> stats(@PathVariable String id) {
        return noteService.stats(id);
    }
}
