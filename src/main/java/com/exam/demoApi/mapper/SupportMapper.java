package com.exam.demoApi.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;

import static java.util.stream.Collectors.toList;

public class SupportMapper {

    public static List<ResultInfo> toResultInfoList(List<SupportInfo> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }

        return entities.stream()
            .map(SupportMapper::toResultInfo)
            .filter(Objects::nonNull)
            .collect(toList());
    }

    public static ResultInfo toResultInfo(SupportInfo info) {
        if (info == null) {
            return null;
        }

        Region infoRegion = info.getRegion();
        return ResultInfo.builder()
            .institute(info.getInstitute())
            .limit(info.getLimit())
            .mgmt(info.getMgmt())
            .rate(info.getRate())
            .reception(info.getReception())
            .target(info.getTarget())
            .usage(info.getUsage())
            .region((infoRegion == null) ? null : infoRegion.getRegionName())
            .build();
    }

    public static List<SupportInfo> toSupportInfoList(List<ResultInfo> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }

        return entities.stream()
            .map(SupportMapper::toSupportInfo)
            .filter(Objects::nonNull)
            .collect(toList());
    }

    public static SupportInfo toSupportInfo(ResultInfo info) {
        if (info == null) {
            return null;
        }

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
