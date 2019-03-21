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
import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.repository.SupportInfoRepository;
import com.exam.demoApi.service.DataService;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.SupportInfoAssert.assertThatSupportInfoEntry;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * {@link DataService}에 대한 단위 테스트
 *
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class DataServiceTest {

    private SupportInfoRepository repository;

    private DataService service;

    @Before
    public void setUp() {
        repository = mock(SupportInfoRepository.class);
        service = new DataService(repository);
    }

    public class CSV레코드를_데이터베이스에_저장 {

        private InputStream fin;
        private InputStream expectedFin;
        private List<ResultInfo> expectedList;

        @Before
        public void init() throws IOException {
            String csvFile = "src/main/resources/test.csv";
            fin = new FileInputStream(csvFile);

            expectedFin = new FileInputStream(csvFile);
            expectedList = Utils.csvRead(ResultInfo.class, expectedFin);
        }

        @Test
        public void 저장시_입력값이_null일_경우() {
            List<ResultInfo> resultInfoList = service.addAll(null);
            assertTrue(CollectionUtils.isEmpty(resultInfoList));
        }

        @Test
        public void 저장시_입력값이_잘못된_format인_경우() {
            // TODO: 해당 case 작성 필요
            //List<ResultInfo> resultInfoList = service.addAll(null);
            //assertTrue(CollectionUtils.isEmpty(resultInfoList));
        }

        @Test
        public void 저장시_입력값과_add메서드내의_SupportInfo정보_비교() {
            service.addAll(fin);

            for (ResultInfo expected : expectedList) {
                verify(repository, times(1)).save(
                    assertArg(persisted -> assertThatSupportInfoEntry(persisted)
                        .hasRegion(expected.getRegion())
                        .hasTarget(expected.getTarget())
                        .hasUsage(expected.getUsage())
                        .hasLimit(expected.getLimit())
                        .hasRate(expected.getRate())
                        .hasInstitute(expected.getInstitute())
                        .hasMgmt(expected.getMgmt())
                        .hasReception(expected.getReception())
                    )
                );
            }
            verifyNoMoreInteractions(repository);
        }
    }
}
