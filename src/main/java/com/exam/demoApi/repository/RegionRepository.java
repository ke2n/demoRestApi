package com.exam.demoApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.Region;

/**
 * @author yunsung Kim
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {

    List<Region> findByRegionName(String regionName);
}
