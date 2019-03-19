package com.exam.demoApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.repository.RegionRepository;
import com.exam.demoApi.repository.SupportInfoRepository;
import com.exam.demoApi.service.SupportInfoService;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.SupportInfoAssert.assertThatSupportInfoEntry;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class DemoApiServiceTest {

    private static final String REGION = "강릉시";
    private static final String TARGET = "강릉시 소재 중소기업으로서 강릉시장이 추천한 자";
    private static final String USAGE = "운전";
    private static final String LIMIT = "추천금액 이내";
    private static final String RATE = "3%";
    private static final String INSTITUTE = "강릉시";
    private static final String MGMT = "강릉지점";
    private static final String RECEPTION = "강릉시 소재 영업점";

    private SupportInfoRepository repository;

    private RegionRepository regionRepository;

    private SupportInfoService service;

    @Before
    public void setUp() {
        repository = mock(SupportInfoRepository.class);
        regionRepository = mock(RegionRepository.class);
        service = new SupportInfoService(repository, regionRepository);
    }

    public class Create {

        @Before
        public void returnNewSupportInfoEntry() {
            given(repository.save(any(SupportInfo.class))).willReturn(
                SupportInfo.builder()
                    .region(new Region(REGION))
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .institute(INSTITUTE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build()
            );
        }

        @Test
        public void shouldPersistNewTodoEntryWithCorrectInformation() {
            ResultInfo resultInfo = ResultInfo.builder()
                .region(REGION)
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();

            service.add(resultInfo);

            verify(repository, times(1)).save(
                assertArg(persisted -> assertThatSupportInfoEntry(persisted)
                    .hasInstitute(INSTITUTE)
                    .hasNoId()
                )
            );
            verifyNoMoreInteractions(repository);
        }

//        @Test
//        public void shouldReturnTheInformationOfPersistedTodoEntry() {
//            TodoDTO newTodoEntry = new TodoDTOBuilder()
//                .description(DESCRIPTION)
//                .title(TITLE)
//                .build();
//
//            TodoDTO created = service.create(newTodoEntry);
//            assertThatTodoDTO(created)
//                .hasDescription(DESCRIPTION)
//                .hasId(ID)
//                .hasTitle(TITLE)
//                .wasCreatedAt(CREATION_TIME)
//                .wasCreatedByUser(CREATED_BY_USER)
//                .wasModifiedAt(MODIFICATION_TIME)
//                .wasModifiedByUser(MODIFIED_BY_USER);
//        }
    }
}
