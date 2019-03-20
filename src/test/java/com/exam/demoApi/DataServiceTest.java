package com.exam.demoApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.repository.SupportInfoRepository;
import com.exam.demoApi.service.DataService;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.ResultInfoAssert.assertThatResultInfoEntry;
import static com.exam.demoApi.SupportInfoAssert.assertThatSupportInfoEntry;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class DataServiceTest {

    private static final Integer ID = 20;
    private static final String REGION = "강릉시";
    private static final String TARGET = "강릉시 소재 중소기업으로서 강릉시장이 추천한 자";
    private static final String USAGE = "운전";
    private static final String LIMIT = "추천금액 이내";
    private static final String RATE = "3%";
    private static final String INSTITUTE = "강릉시";
    private static final String MGMT = "강릉지점";
    private static final String RECEPTION = "강릉시 소재 영업점";

    private SupportInfoRepository repository;

    private DataService service;

    @Before
    public void setUp() {
        repository = mock(SupportInfoRepository.class);
        service = new DataService(repository);
    }

    public class CSV레코드를_데이터베이스에_저장 {

        private InputStream fin;
        List<ResultInfo> csvList;

        @Before
        public void init() {
            String csvFile = "src/main/resources/test.csv";
            try {
                fin = new FileInputStream(csvFile);
                csvList = Utils.csvRead(ResultInfo.class, fin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void 저장시_입력값과_add메서드내의_SupportInfo정보_비교() {

            service.addAll(fin);

            verify(repository, times(1)).save(
                assertArg(persisted -> assertThatSupportInfoEntry(persisted)
                    .hasNoId()
                    .hasRegion(REGION)
                    .hasTarget(TARGET)
                    .hasUsage(USAGE).hasLimit(LIMIT)
                    .hasRate(RATE)
                    .hasInstitute(INSTITUTE)
                    .hasMgmt(MGMT)
                    .hasReception(RECEPTION)
                )
            );
            verifyNoMoreInteractions(repository);
        }

        @Test
        public void 저장시_입력값과_결과값_비교() {
            ResultInfo newInfo = ResultInfo.builder()
                .region(REGION)
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();

            ResultInfo resultInfo = service.add(newInfo);
            assertThatResultInfoEntry(resultInfo)
                .hasRegion(REGION)
                .hasTarget(TARGET)
                .hasUsage(USAGE).hasLimit(LIMIT)
                .hasRate(RATE)
                .hasInstitute(INSTITUTE)
                .hasMgmt(MGMT)
                .hasReception(RECEPTION);
        }
    }
}
