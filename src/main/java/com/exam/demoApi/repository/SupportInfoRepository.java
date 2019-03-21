package com.exam.demoApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;

/**
 * @author yunsung Kim
 */
public interface SupportInfoRepository extends JpaRepository<SupportInfo, Integer> {

    Optional<SupportInfo> findByRegion(Region region);
}
