package com.exam.demoApi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.mapper.SupportMapper;
import com.exam.demoApi.repository.SupportInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataService {

    private final SupportInfoRepository supportInfoRepository;

    public List<ResultInfo> addAll(InputStream is) {
        List<ResultInfo> csvList = new ArrayList<>();
        List<SupportInfo> resultList = new ArrayList<>();
        try {
            csvList = Utils.csvRead(ResultInfo.class, is);
        } catch (IOException e) {
            log.error("SupportInfoService.addAll IOException - {}", e.getMessage());
        }

        List<SupportInfo> convertList = SupportMapper.toSupportInfoList(csvList);

        for (SupportInfo info : convertList) {
            resultList.add(supportInfoRepository.save(info));
        }

        return SupportMapper.toResultInfoList(resultList);
    }
}
