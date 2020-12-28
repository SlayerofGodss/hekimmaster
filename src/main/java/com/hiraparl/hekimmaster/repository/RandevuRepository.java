package com.hiraparl.hekimmaster.repository;

import com.hiraparl.hekimmaster.domain.Randevu;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Randevu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RandevuRepository extends JpaRepository<Randevu, Long>, JpaSpecificationExecutor<Randevu> {
}
