package com.exam.demoApi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvUtils {

    private static CsvMapper mapper = new CsvMapper();

    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

    public static List<SupportInfo> convertTo(List<ResultInfo> list) {
        List<SupportInfo> resultList = new ArrayList<>();
        for (ResultInfo info : list) {
            resultList.add(SupportInfo.builder()
                .institute(info.getInstitute())
                .limit(info.getLimit())
                .mgmt(info.getMgmt())
                .rate(info.getRate())
                .reception(info.getReception())
                .target(info.getTarget())
                .usage(info.getUsage())
                .region(new Region(info.getRegion()))
                .build()
            );
        }

        return resultList;
    }

    public static String getHashKey(String prefix, String str) {
        return String.format("%s_%s", prefix,
            DigestUtils.md5Hex(StringUtils.defaultString(str, "")).toUpperCase());
    }
}
