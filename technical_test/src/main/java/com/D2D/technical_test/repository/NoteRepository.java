package com.D2D.technical_test.repository;

import com.D2D.technical_test.model.Note;
import com.D2D.technical_test.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
    Page<Note> findAllByTags(Tag tag, Pageable pageable);
}
