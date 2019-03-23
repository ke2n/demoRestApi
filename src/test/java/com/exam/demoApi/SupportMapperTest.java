package com.exam.demoApi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.mapper.SupportMapper;
import com.exam.demoApi.model.ResultInfo;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.common.ResultInfoAssert.assertThatResultInfoEntry;
import static com.exam.demoApi.common.SupportInfoAssert.assertThatSupportInfoEntry;

/**
 * {@link SupportMapper}에 대한 단위 테스트
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class SupportMapperTest {

    private static final String REGION = "강릉시";
    private static final String TARGET = "강릉시 소재 중소기업으로서 강릉시장이 추천한 자";
    private static final String USAGE = "운전";
    private static final String LIMIT = "추천금액 이내";
    private static final String RATE = "3%에서 5.00%";
    private static final String INSTITUTE = "강릉시";
    private static final String MGMT = "강릉지점";
    private static final String RECEPTION = "강릉시 소재 영업점";

    private SupportInfo emptySupportInfo;
    private SupportInfo normalSupportInfo;
    private SupportInfo regionIsNullSupportInfo;

    private ResultInfo emptyResultInfo;
    private ResultInfo normalResultInfo;
    private ResultInfo regionIsNullResultInfo;

    @Before
    public void setup() {
        emptySupportInfo = SupportInfo.builder().build();

        normalSupportInfo = SupportInfo.builder()
            .region(new Region(REGION))
            .target(TARGET)
            .usage(USAGE).limit(LIMIT)
            .rate(RATE)
            .institute(INSTITUTE)
            .mgmt(MGMT)
            .reception(RECEPTION)
            .build();

        regionIsNullSupportInfo = SupportInfo.builder()
            .target(TARGET)
            .usage(USAGE).limit(LIMIT)
            .rate(RATE)
            .institute(INSTITUTE)
            .mgmt(MGMT)
            .reception(RECEPTION)
            .build();

        emptyResultInfo = ResultInfo.builder().build();

        normalResultInfo = ResultInfo.builder()
            .region(REGION)
            .target(TARGET)
            .usage(USAGE).limit(LIMIT)
            .rate(RATE)
            .institute(INSTITUTE)
            .mgmt(MGMT)
            .reception(RECEPTION)
            .build();

        regionIsNullResultInfo = ResultInfo.builder()
            .target(TARGET)
            .usage(USAGE).limit(LIMIT)
            .rate(RATE)
            .institute(INSTITUTE)
            .mgmt(MGMT)
            .reception(RECEPTION)
            .build();
    }

    public class toResultInfo메서드_테스트 {

        @Test
        public void Null일_경우() {
            ResultInfo result = SupportMapper.toResultInfo(null);
            Assert.assertNull(result);
        }

        @Test
        public void EmptyValue일_경우() {
            ResultInfo result = SupportMapper.toResultInfo(emptySupportInfo);
            assertThatResultInfoEntry(result)
                .isAllEmptyValue();
        }

        @Test
        public void Region값이_Null일_경우() {
            ResultInfo result = SupportMapper.toResultInfo(regionIsNullSupportInfo);
            assertThatResultInfoEntry(result)
                .hasNoRegion()
                .hasTarget(TARGET)
                .hasUsage(USAGE)
                .hasLimit(LIMIT)
                .hasRate(RATE)
                .hasInstitute(INSTITUTE)
                .hasMgmt(MGMT)
                .hasReception(RECEPTION);
        }

        @Test
        public void 정상의_경우() {
            ResultInfo result = SupportMapper.toResultInfo(normalSupportInfo);
            assertThatResultInfoEntry(result)
                .hasRegion(REGION)
                .hasTarget(TARGET)
                .hasUsage(USAGE)
                .hasLimit(LIMIT)
                .hasRate(RATE)
                .hasInstitute(INSTITUTE)
                .hasMgmt(MGMT)
                .hasReception(RECEPTION);
        }
    }

    public class toResultInfoList메서드_테스트 {

        @Test
        public void Null일_경우() {
            List<ResultInfo> resultList = SupportMapper.toResultInfoList(null);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void EmptyList일_경우() {
            List<ResultInfo> resultList = SupportMapper.toResultInfoList(new ArrayList<>());
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void List중_Null인_Value가_있을_경우() {
            List<SupportInfo> requestList = new ArrayList<>();
            requestList.add(null);
            requestList.add(emptySupportInfo);

            List<ResultInfo> resultList = SupportMapper.toResultInfoList(requestList);
            Assert.assertEquals(resultList.size(), 1);
        }

        @Test
        public void 정상의_경우() {
            List<SupportInfo> requestList = new ArrayList<>();
            requestList.add(normalSupportInfo);
            requestList.add(normalSupportInfo);

            List<ResultInfo> resultList = SupportMapper.toResultInfoList(requestList);
            Assert.assertEquals(resultList.size(), requestList.size());
        }
    }

    public class toSupportInfo메서드_테스트 {

        @Test
        public void Null일_경우() {
            SupportInfo result = SupportMapper.toSupportInfo(null);
            Assert.assertNull(result);
        }

        @Test
        public void EmptyValue일_경우() {
            SupportInfo result = SupportMapper.toSupportInfo(emptyResultInfo);
            assertThatSupportInfoEntry(result)
                .isAllEmptyValue();
        }

        @Test
        public void Region값이_Null일_경우() {
            SupportInfo result = SupportMapper.toSupportInfo(regionIsNullResultInfo);
            assertThatSupportInfoEntry(result)
                .hasNoRegion()
                .hasTarget(TARGET)
                .hasUsage(USAGE)
                .hasLimit(LIMIT)
                .hasLimitNum(0)
                .hasRate(RATE)
                .hasMinRate(3.0)
                .hasAvgRate(4.0)
                .hasInstitute(INSTITUTE)
                .hasMgmt(MGMT)
                .hasReception(RECEPTION);
        }

        @Test
        public void 정상의_경우() {
            SupportInfo result = SupportMapper.toSupportInfo(normalResultInfo);
            assertThatSupportInfoEntry(result)
                .hasRegion(REGION)
                .hasTarget(TARGET)
                .hasUsage(USAGE)
                .hasLimit(LIMIT)
                .hasLimitNum(0)
                .hasRate(RATE)
                .hasMinRate(3.0)
                .hasAvgRate(4.0)
                .hasInstitute(INSTITUTE)
                .hasMgmt(MGMT)
                .hasReception(RECEPTION);
        }
    }

    public class toSupportInfoList메서드_테스트 {

        @Test
        public void Null일_경우() {
            List<SupportInfo> resultList = SupportMapper.toSupportInfoList(null);
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void EmptyList일_경우() {
            List<SupportInfo> resultList = SupportMapper.toSupportInfoList(new ArrayList<>());
            Assert.assertEquals(resultList.size(), 0);
        }

        @Test
        public void List중_Null인_Value가_있을_경우() {
            List<ResultInfo> requestList = new ArrayList<>();
            requestList.add(null);
            requestList.add(emptyResultInfo);

            List<SupportInfo> resultList = SupportMapper.toSupportInfoList(requestList);
            Assert.assertEquals(resultList.size(), 1);
        }

        @Test
        public void 정상의_경우() {
            List<ResultInfo> requestList = new ArrayList<>();
            requestList.add(normalResultInfo);
            requestList.add(normalResultInfo);

            List<SupportInfo> resultList = SupportMapper.toSupportInfoList(requestList);
            Assert.assertEquals(resultList.size(), requestList.size());
        }
    }

}
