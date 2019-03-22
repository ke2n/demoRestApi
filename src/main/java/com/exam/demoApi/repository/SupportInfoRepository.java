package com.exam.demoApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;

/**
 * @author yunsung Kim
 */
public interface SupportInfoRepository extends JpaRepository<SupportInfo, Integer> {

    @Query("select a from SupportInfo a join fetch a.region")
    List<SupportInfo> findAll();

    Optional<SupportInfo> findByRegion(Region region);
}
