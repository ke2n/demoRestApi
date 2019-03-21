package com.exam.demoApi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.mapper.SupportMapper;
import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.repository.RegionRepository;
import com.exam.demoApi.repository.SupportInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_DATA;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_REGION;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_SUPPORT_ID;

/**
 * @author yunsung Kim
 */
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
        List<SupportInfo> SupportInfoList = supportInfoRepository.findAll();
        if (CollectionUtils.isEmpty(SupportInfoList)) {
            throw new CustomException(NOT_FOUND_DATA);
        }

        return SupportMapper.toResultInfoList(SupportInfoList);
    }

    private SupportInfo findSupportInfoByRegionName(String regionName) {
        Optional<Region> result = regionRepository.findByRegionName(regionName);
        Region region = result.orElseThrow(() -> new CustomException(NOT_FOUND_REGION));
        return supportInfoRepository.findByRegion(region)
            .orElseThrow(() -> new CustomException(NOT_FOUND_SUPPORT_ID));
    }

    public ResultInfo search(ResultInfo resultInfo) {
        SupportInfo supportInfo = findSupportInfoByRegionName(resultInfo.getRegion());
        return SupportMapper.toResultInfo(supportInfo);
    }

    public ResultInfo edit(ResultInfo resultInfo) {
        SupportInfo supportInfo = findSupportInfoByRegionName(resultInfo.getRegion());
        supportInfo.setTarget(resultInfo.getTarget());
        supportInfo.setUsage(resultInfo.getUsage());
        supportInfo.setLimit(resultInfo.getLimit());
        supportInfo.setLimitNum(Utils.limitToNumberConverter(resultInfo.getLimit()));
        supportInfo.setRate(resultInfo.getRate());
        supportInfo.setAvgRate(Utils.convertAvgRate(resultInfo.getRate()));
        supportInfo.setMinRate(Utils.convertMinRate(resultInfo.getRate()));
        supportInfo.setInstitute(resultInfo.getInstitute());
        supportInfo.setMgmt(resultInfo.getMgmt());
        supportInfo.setReception(resultInfo.getReception());

        return SupportMapper.toResultInfo(supportInfoRepository.save(supportInfo));
    }

    public List<String> limits(Integer num) {
        Sort sort = Sort.by(Sort.Order.desc("limitNum"), Sort.Order.asc("avgRate"));
        Pageable pageable = PageRequest.of(0, num, sort);
        Page<SupportInfo> infoPage = supportInfoRepository.findAll(pageable);
        List<SupportInfo> SupportInfoList = infoPage.getContent();

        if (CollectionUtils.isEmpty(SupportInfoList)) {
            throw new CustomException(NOT_FOUND_DATA);
        }

        return SupportMapper.toResultInfoList(SupportInfoList).stream()
            .map(ResultInfo::getRegion)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }

    public ResultInfo minRateInstitute() {
        Sort sort = Sort.by(Sort.Order.asc("minRate"));
        Pageable pageable = PageRequest.of(0, 1, sort);
        Page<SupportInfo> infoPage = supportInfoRepository.findAll(pageable);
        List<SupportInfo> SupportInfoList = infoPage.getContent();

        if (CollectionUtils.isEmpty(SupportInfoList)) {
            throw new CustomException(NOT_FOUND_DATA);
        }

        String institute = SupportInfoList.stream()
            .findFirst()
            .map(SupportInfo::getInstitute).orElseThrow(() -> new CustomException(NOT_FOUND_DATA));
        return ResultInfo.builder()
            .institute(institute)
            .build();
    }
}
