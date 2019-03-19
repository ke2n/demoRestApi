package com.exam.demoApi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.mapper.SupportMapper;
import com.exam.demoApi.repository.RegionRepository;
import com.exam.demoApi.repository.SupportInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupportInfoService {

    private final SupportInfoRepository supportInfoRepository;

    private final RegionRepository regionRepository;

    public ResultInfo add(ResultInfo resultInfo) {
        SupportInfo supportInfo = SupportMapper.toSupportInfo(resultInfo);
        return SupportMapper.toResultInfo(supportInfoRepository.save(supportInfo));
    }

    public List<ResultInfo> list() {
        return SupportMapper.toResultInfoList(supportInfoRepository.findAll());
    }

    public ResultInfo search(ResultInfo resultInfo) {
        Region region = regionRepository.findByRegionName(resultInfo.getRegion());
        SupportInfo supportInfo = supportInfoRepository.findByRegion(region);
        return SupportMapper.toResultInfo(supportInfo);
    }

    public ResultInfo edit(ResultInfo resultInfo) {
        Region region = regionRepository.findByRegionName(resultInfo.getRegion());
        SupportInfo supportInfo = supportInfoRepository.findByRegion(region);
        supportInfo.setTarget(resultInfo.getTarget());
        supportInfo.setUsage(resultInfo.getUsage());
        supportInfo.setLimit(resultInfo.getLimit());
        supportInfo.setRate(resultInfo.getRate());
        supportInfo.setInstitute(resultInfo.getInstitute());
        supportInfo.setMgmt(resultInfo.getMgmt());
        supportInfo.setReception(resultInfo.getReception());

        return SupportMapper.toResultInfo(supportInfoRepository.save(supportInfo));
    }

    public List<String> limits(Integer num) {
        Sort sort = Sort.by(Sort.Order.desc("limitNum"), Sort.Order.asc("avgRate"));
        Pageable pageable = PageRequest.of(0, num, sort);
        Page<SupportInfo> infoPage = supportInfoRepository.findAll(pageable);

//        for (SupportInfo info: infoPage.getContent()) {
//            log.info("limits list - {}, {}, {}", info.getRegion(), info.getLimit(), info.getAvgRate());
//        }
        return SupportMapper.toResultInfoList(infoPage.getContent()).stream()
            .map(ResultInfo::getRegion)
            .collect(Collectors.toList());
    }

    public ResultInfo minRateInstitute() {
        Sort sort = Sort.by(Sort.Order.asc("minRate"));
        Pageable pageable = PageRequest.of(0, 1, sort);
        Page<SupportInfo> infoPage = supportInfoRepository.findAll(pageable);

//        for (SupportInfo info: infoPage.getContent()) {
//            log.info("limits list - {}, {}, {}", info.getRegion(), info.getLimit(), info.getAvgRate());
//        }

        String institute = infoPage.getContent().stream()
            .map(SupportInfo::getInstitute).findFirst().orElse(StringUtils.EMPTY);
        return ResultInfo.builder()
            .institute(institute)
            .build();
    }
}
