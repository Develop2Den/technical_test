package com.D2D.technical_test.service;

import com.D2D.technical_test.dto.*;
import com.D2D.technical_test.model.Note;
import com.D2D.technical_test.model.Tag;
import com.D2D.technical_test.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteResponse create(NoteCreateRequest request) {
        Note note = Note.builder()
                .title(request.getTitle())
                .text(request.getText())
                .tags(request.getTags() == null ? EnumSet.noneOf(Tag.class) : EnumSet.copyOf(request.getTags()))
                .createdDate(Instant.now())
                .build();
        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    public PagedResponse<NoteListItemResponse> list(Integer page, Integer size, Tag tag) {
        int p = page == null || page < 0 ? 0 : page;
        int s = size == null || size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(p, s, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Note> pageData = (tag == null)
                ? noteRepository.findAll(pageable)
                : noteRepository.findAllByTags(tag, pageable);
        List<NoteListItemResponse> items = pageData.getContent().stream()
                .map(n -> new NoteListItemResponse(n.getId(), n.getTitle(), n.getCreatedDate()))
                .toList();
        return new PagedResponse<>(items, pageData.getNumber(), pageData.getSize(), pageData.getTotalElements(), pageData.getTotalPages(), pageData.isLast());
    }

    public NoteResponse get(String id) {
        Note note = noteRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return toResponse(note);
    }

    public NoteResponse update(String id, NoteUpdateRequest request) {
        Note existing = noteRepository.findById(id).orElseThrow(NoSuchElementException::new);
        existing.setTitle(request.getTitle());
        existing.setText(request.getText());
        existing.setTags(request.getTags() == null ? EnumSet.noneOf(Tag.class) : EnumSet.copyOf(request.getTags()));
        Note saved = noteRepository.save(existing);
        return toResponse(saved);
    }

    public void delete(String id) {
        if (!noteRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        noteRepository.deleteById(id);
    }

    private static final Pattern WORD_SPLIT = Pattern.compile("[^A-Za-z0-9']+");

    public LinkedHashMap<String, Integer> stats(String id) {
        Note note = noteRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return computeWordStats(note.getText());
    }

    public static LinkedHashMap<String, Integer> computeWordStats(String text) {
        if (text == null || text.isBlank()) {
            return new LinkedHashMap<>();
        }
        String[] tokens = WORD_SPLIT.split(text.toLowerCase());
        Map<String, Integer> counts = new HashMap<>();
        for (String t : tokens) {
            if (t.isBlank()) continue;
            counts.merge(t, 1, Integer::sum);
        }
        return counts.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue());
                    if (cmp != 0) return cmp;
                    return a.getKey().compareTo(b.getKey());
                })
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

    private static NoteResponse toResponse(Note n) {
        return new NoteResponse(n.getId(), n.getTitle(), n.getCreatedDate(), n.getText(), n.getTags());
    }
}
