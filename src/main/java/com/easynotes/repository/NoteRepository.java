package com.easynotes.repository;

import com.easynotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Pradeep Anjanappa
 */

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
