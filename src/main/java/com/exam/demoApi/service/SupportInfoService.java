package com.exam.demoApi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exam.demoApi.dao.RegionRepository;
import com.exam.demoApi.dao.SupportInfoRepository;
import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.util.CsvUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupportInfoService {

    private final SupportInfoRepository supportInfoRepository;

    private final RegionRepository regionRepository;

    public SupportInfo add(SupportInfo supportInfo) {
        return supportInfoRepository.save(supportInfo);
    }

    public List<SupportInfo> addAll(MultipartFile file) {
        List<ResultInfo> csvList = new ArrayList<>();
        List<SupportInfo> resultList = new ArrayList<>();
        try {
            csvList = CsvUtils.read(ResultInfo.class, file.getInputStream());
        } catch (IOException e) {
            log.error("SupportInfoService.addAll IOException - {}", e.getMessage());
        }

        List<SupportInfo> convertList = CsvUtils.convertTo(csvList);

        for (SupportInfo info : convertList) {
            String regionName = Optional.ofNullable(info)
                .map(SupportInfo::getRegion)
                .map(Region::getRegionName)
                .orElse(StringUtils.EMPTY);
            int count = regionRepository.countByRegionCode(CsvUtils.getHashKey("region", regionName));
            //if (count <= 0) {
            resultList.add(supportInfoRepository.save(info));
            //}
        }

        return resultList;
    }

    public List<SupportInfo> list() {
        return supportInfoRepository.findAll();
    }
}
