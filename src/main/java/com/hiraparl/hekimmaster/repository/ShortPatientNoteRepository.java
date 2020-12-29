package com.hiraparl.hekimmaster.repository;

import com.hiraparl.hekimmaster.domain.ShortPatientNote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShortPatientNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShortPatientNoteRepository extends JpaRepository<ShortPatientNote, Long>, JpaSpecificationExecutor<ShortPatientNote> {
}
