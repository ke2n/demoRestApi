package com.exam.demoApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    int countByRegionCode(String id);
}
