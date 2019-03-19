package com.exam.demoApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;

public interface SupportInfoRepository extends JpaRepository<SupportInfo, Integer> {

    SupportInfo findByRegion(Region region);
}
