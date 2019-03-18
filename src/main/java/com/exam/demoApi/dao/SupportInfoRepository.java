package com.exam.demoApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.SupportInfo;

public interface SupportInfoRepository extends JpaRepository<SupportInfo, Integer> {

}
