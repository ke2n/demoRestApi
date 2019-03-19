package com.exam.demoApi.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    private static CsvMapper mapper = new CsvMapper();

    public static <T> List<T> csvRead(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

    public static long limitToNumberConverter(String limitStr) {
        long number = 0;
        try {
            number = Integer.parseInt(limitStr.replaceAll("\\D+", ""));
        } catch (NumberFormatException e) {
            log.error("limitToNumberConverter NumberFormatException {} - {}", e.getMessage(), limitStr);
        }

        if (StringUtils.contains(limitStr, "억원")) {
            number *= 100000000;
        } else if (StringUtils.contains(limitStr, "천만원")) {
            number *= 10000000;
        } else if (StringUtils.contains(limitStr, "백만원")) {
            number *= 1000000;
        }

        return number;
    }

    private static List<Double> getDoubleList(String str) {
        Pattern p = Pattern.compile("\\d+(?:\\.\\d+)?");
        Matcher m = p.matcher(str);
        List<Double> resultList = new ArrayList<>();
        while (m.find()) {
            double number = 0.0;
            try {
                number = Double.parseDouble(m.group());
            } catch (NumberFormatException e) {
                log.error("convertAvgRate NumberFormatException {} - {}", e.getMessage(), str);
            }
            resultList.add(number);
        }
        return resultList;
    }

    public static double convertAvgRate(String rate) {
        return getDoubleList(rate).stream()
            .mapToDouble(Double::doubleValue)
            .average().orElse(Double.NaN);
    }

    public static double convertMinRate(String rate) {
        List<Double> doubleList = getDoubleList(rate);
        if (!CollectionUtils.isEmpty(doubleList)) {
            return doubleList.get(0);
        }
        return Double.NaN;
    }
}
