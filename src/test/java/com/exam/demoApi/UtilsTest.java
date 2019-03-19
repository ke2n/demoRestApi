package com.exam.demoApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.CollectionUtils;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.nitorcreations.junit.runners.NestedRunner;

/**
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class UtilsTest {

    private final String CSV_PATH = "src/main/resources/test.csv";
    private InputStream fin;

    @Before
    public void setup() {
        try {
            fin = new FileInputStream(CSV_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CsvRead {

        @Test
        public void whenAllNullsValue() {
            List resultList = Utils.csvRead(null, null);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void whenClazzNullValue() {
            List resultList = Utils.csvRead(null, fin);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void whenStreamNullValue() {
            List resultList = Utils.csvRead(ResultInfo.class, null);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void whenStreamIOExceptionValue() {
            List resultList = Utils.csvRead(SupportInfo.class, null);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void whenSuccessValue() {
            List resultList = Utils.csvRead(ResultInfo.class, fin);
            Assert.assertFalse(CollectionUtils.isEmpty(resultList));
        }
    }

    public class convertRate {

        @Test
        public void whenNullValue() {
            double result = Utils.convertAvgRate(null);
            Assert.assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate(null);
            Assert.assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void whenEmptyStringValue() {
            double result = Utils.convertAvgRate("");
            Assert.assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate("");
            Assert.assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void whenNotIncludeNumberStringValue() {
            double result = Utils.convertAvgRate("ABCDEFG");
            Assert.assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate("ABCDEFG");
            Assert.assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void whenIncludeSingleNumberStringValue() {
            double result = Utils.convertAvgRate("0.5% 입니다.");
            Assert.assertEquals(result, 0.5, 0.0);

            double result2 = Utils.convertMinRate("0.5% 입니다.");
            Assert.assertEquals(result2, 0.5, 0.0);
        }

        @Test
        public void whenIncludeMultipleNumberStringValue() {
            double result = Utils.convertAvgRate("40.221% 부터 0.5% ~ 20.0");
            Assert.assertEquals(result, (40.221 + 0.5 + 20.0) / 3, 0.0);

            double result2 = Utils.convertMinRate("40.221% 부터 0.5% ~ 20.0");
            Assert.assertEquals(result2, 0.5, 0.0);
        }


    }

    public class limitToNumberConverter {

        @Test
        public void whenNullValue() {
            long result = Utils.limitToNumberConverter(null);
            Assert.assertEquals(result, 0);
        }

        @Test
        public void whenEmptyStringValue() {
            long result = Utils.limitToNumberConverter("");
            Assert.assertEquals(result, 0);
        }

        @Test
        public void whenNotIncludeNumberStringValue() {
            long result = Utils.limitToNumberConverter("ABCDEFG");
            Assert.assertEquals(result, 0);
        }

        @Test
        public void whenIncludeMultipleNumberStringValue() {
            long result = Utils.limitToNumberConverter("12ABCDEFG11");
            Assert.assertEquals(result, 12);
        }

        @Test
        public void whenIncludeNumberAndPostfixStringValue() {
            long result = Utils.limitToNumberConverter("12억원");
            Assert.assertEquals(result, 1200000000);
        }

        @Test
        public void whenIncludeMultipleNumberAndPostfixStringValue() {
            long result = Utils.limitToNumberConverter("12억원, 100백만원");
            Assert.assertEquals(result, 1200000000);
        }

    }

}
