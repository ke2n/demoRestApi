package com.exam.demoApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    Region findByRegionName(String regionName);
}
