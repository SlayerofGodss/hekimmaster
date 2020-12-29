package com.hiraparl.hekimmaster.repository;

import com.hiraparl.hekimmaster.domain.Day;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Day entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayRepository extends JpaRepository<Day, Long>, JpaSpecificationExecutor<Day> {
}
