package com.exam.demoApi.common;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.exam.demoApi.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.extern.slf4j.Slf4j;

import static com.exam.demoApi.exception.ExceptionCode.CANNOT_CONVERT_FILE;
import static com.exam.demoApi.exception.ExceptionCode.ONLY_SUPPORT_UTF8;

/**
 * @author yunsung Kim
 */
@Slf4j
public class Utils {

    private static CsvMapper mapper = new CsvMapper();

    public static <T> List<T> csvRead(Class<T> clazz, InputStream stream) {

        if (clazz == null) {
            return new ArrayList<>();
        }

        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);

        try {
            return reader.<T>readValues(stream).readAll();
        } catch (CharConversionException e) {
            throw new CustomException(ONLY_SUPPORT_UTF8);
        } catch (UnrecognizedPropertyException e) {
            throw new CustomException(CANNOT_CONVERT_FILE, e.getMessage());
        } catch (IOException e) {
            log.error("Utils.csvRead IOException - {}", e.getMessage());
        }

        return new ArrayList<>();
    }

    public static long limitToNumberConverter(String limitStr) {
        long number = 0;
        if (StringUtils.isEmpty(limitStr)) {
            return number;
        }

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(limitStr);

        if (m.find()) {
            try {
                number = Integer.parseInt(m.group());
            } catch (NumberFormatException e) {
                log.error("limitToNumberConverter NumberFormatException {} - {}", e.getMessage(), limitStr);
            }
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
        List<Double> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            return resultList;
        }

        Pattern p = Pattern.compile("\\d+(?:\\.\\d+)?");
        Matcher m = p.matcher(str);

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
        return doubleList.stream()
            .sorted()
            .findFirst()
            .orElse(Double.NaN);
    }
}
