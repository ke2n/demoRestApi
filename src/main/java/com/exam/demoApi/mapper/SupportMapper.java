package com.exam.demoApi.mapper;

import java.util.List;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;

import static java.util.stream.Collectors.toList;

public class SupportMapper {

    public static List<ResultInfo> toResultInfoList(List<SupportInfo> entities) {
        return entities.stream()
            .map(SupportMapper::toResultInfo)
            .collect(toList());
    }

    public static ResultInfo toResultInfo(SupportInfo info) {
        if (info == null) {
            return new ResultInfo();
        }

        return ResultInfo.builder()
            .institute(info.getInstitute())
            .limit(info.getLimit())
            .mgmt(info.getMgmt())
            .rate(info.getRate())
            .reception(info.getReception())
            .target(info.getTarget())
            .usage(info.getUsage())
            .region(info.getRegion().getRegionName())
            .build();
    }

    public static List<SupportInfo> toSupportInfoList(List<ResultInfo> entities) {
        return entities.stream()
            .map(SupportMapper::toSupportInfo)
            .collect(toList());
    }

    public static SupportInfo toSupportInfo(ResultInfo info) {
        // TODO: info가 null일때 처리

        return SupportInfo.builder()
            .institute(info.getInstitute())
            .limit(info.getLimit())
            .limitNum(Utils.limitToNumberConverter(info.getLimit()))
            .mgmt(info.getMgmt())
            .rate(info.getRate())
            .avgRate(Utils.convertAvgRate(info.getRate()))
            .minRate(Utils.convertMinRate(info.getRate()))
            .reception(info.getReception())
            .target(info.getTarget())
            .usage(info.getUsage())
            .region(new Region(info.getRegion()))
            .build();
    }

}
