package com.hiraparl.hekimmaster.repository;

import com.hiraparl.hekimmaster.domain.PatientNote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PatientNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Long>, JpaSpecificationExecutor<PatientNote> {
}
