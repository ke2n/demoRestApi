package com.exam.demoApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.CollectionUtils;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.nitorcreations.junit.runners.NestedRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class UtilsTest {

    private final String CSV_PATH = "src/main/resources/test.csv";
    private final String CSV_PATH_NO_UTF8 = "src/main/resources/test_no_utf8.csv";
    private InputStream fin;
    private InputStream finNoUtf8;

    @Before
    public void setup() {
        try {
            fin = new FileInputStream(CSV_PATH);
            finNoUtf8 = new FileInputStream(CSV_PATH_NO_UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CsvRead_메서드_테스트 {

        @Test
        public void 모든_파라메터가_Null일때() {
            List resultList = Utils.csvRead(null, null);
            assertEquals(resultList.size(), 0);
        }

        @Test
        public void Clazz파라메터가_Null일때() {
            List resultList = Utils.csvRead(null, fin);
            assertEquals(resultList.size(), 0);
        }

        @Test
        public void Stream이_Null일때() {
            List resultList = Utils.csvRead(ResultInfo.class, null);
            assertEquals(resultList.size(), 0);
        }

        @Test
        public void whenStreamIOExceptionValue() {
            List resultList = Utils.csvRead(SupportInfo.class, null);
            assertEquals(resultList.size(), 0);
        }

        @Test
        public void UTF8형식의_파일이_아닌경우() {
            // TODO: 해당 case 작성 필요
            List resultList = Utils.csvRead(ResultInfo.class, finNoUtf8);
            assertTrue(CollectionUtils.isEmpty(resultList));
        }

        @Test
        public void 정상케이스일때() {
            List resultList = Utils.csvRead(ResultInfo.class, fin);
            assertFalse(CollectionUtils.isEmpty(resultList));
        }
    }

    public class convertRate_메서드_테스트 {

        @Test
        public void 파라메터가_Null일때() {
            double result = Utils.convertAvgRate(null);
            assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate(null);
            assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void 빈스트링값_일때() {
            double result = Utils.convertAvgRate("");
            assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate("");
            assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void 스트링에_숫자값이_포함되지_않았을때() {
            double result = Utils.convertAvgRate("ABCDEFG");
            assertEquals(result, Double.NaN, 0.0);

            double result2 = Utils.convertMinRate("ABCDEFG");
            assertEquals(result2, Double.NaN, 0.0);
        }

        @Test
        public void 스트링에_숫자값이_한번만_출현하였을때() {
            double result = Utils.convertAvgRate("0.5% 입니다.");
            assertEquals(result, 0.5, 0.0);

            double result2 = Utils.convertMinRate("0.5% 입니다.");
            assertEquals(result2, 0.5, 0.0);
        }

        @Test
        public void 스트링에_숫자값이_두번이상_출현하였을때() {
            double result = Utils.convertAvgRate("40.221% 부터 0.5% ~ 20.0");
            assertEquals(result, (40.221 + 0.5 + 20.0) / 3, 0.0);

            double result2 = Utils.convertMinRate("40.221% 부터 0.5% ~ 20.0");
            assertEquals(result2, 0.5, 0.0);
        }


    }

    public class limitToNumberConverter_메서드_테스트 {

        @Test
        public void 파라메터가_Null일때() {
            long result = Utils.limitToNumberConverter(null);
            assertEquals(result, 0);
        }

        @Test
        public void 빈스트링값_일때() {
            long result = Utils.limitToNumberConverter("");
            assertEquals(result, 0);
        }

        @Test
        public void 스트링에_숫자값이_포함되지_않았을때() {
            long result = Utils.limitToNumberConverter("ABCDEFG");
            assertEquals(result, 0);
        }

        @Test
        public void 스트링에_숫자값이_두번이상_출현하였을때() {
            long result = Utils.limitToNumberConverter("12ABCDEFG11");
            assertEquals(result, 12);
        }

        @Test
        public void 스트링에_숫자값과_금액이_문자로_출현하였을때() {
            long result = Utils.limitToNumberConverter("12억원");
            assertEquals(result, 1200000000);
        }

        @Test
        public void 스트링에_숫자값과_금액이_문자로_출현하였을때_2() {
            long result = Utils.limitToNumberConverter("100백만원");
            assertEquals(result, 100000000);
        }

    }

}
