package com.exam.demoApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.repository.RegionRepository;
import com.exam.demoApi.repository.SupportInfoRepository;
import com.exam.demoApi.service.SupportInfoService;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.ResultInfoAssert.assertThatResultInfoEntry;
import static com.exam.demoApi.SupportInfoAssert.assertThatSupportInfoEntry;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_DATA;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_REGION;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_SUPPORT_ID;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class SupportInfoServiceTest {

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

    private RegionRepository regionRepository;

    private SupportInfoService service;

    @Before
    public void setUp() {
        repository = mock(SupportInfoRepository.class);
        regionRepository = mock(RegionRepository.class);
        service = new SupportInfoService(repository, regionRepository);
    }

    public class 지자체정보_목록출력 {

        private SupportInfo supportInfo;
        private List<SupportInfo> supportInfoList;

        @Before
        public void init() {
            supportInfo = SupportInfo.builder()
                .region(new Region(REGION))
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();

            supportInfoList = Arrays.asList(
                supportInfo,
                supportInfo,
                supportInfo
            );
        }

        @Test
        public void 결과값이_없을때() {
            given(repository.findAll()).willReturn(new ArrayList<>());

            Throwable thrown = catchThrowable(() -> service.list());

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_DATA);
        }

        @Test
        public void 결과값이_있을때() {
            given(repository.findAll()).willReturn(supportInfoList);

            List<ResultInfo> resultInfoList = service.list();

            assertTrue(!CollectionUtils.isEmpty(resultInfoList));
            assertEquals(3, resultInfoList.size());
        }
    }

    public class 지자체정보_생성 {

        @Before
        public void init() {
            given(repository.save(any(SupportInfo.class))).willReturn(
                SupportInfo.builder()
                    .supportId(ID)
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
        public void 저장시_입력값과_add메서드내의_SupportInfo정보_비교() {
            ResultInfo newInfo = ResultInfo.builder()
                .region(REGION)
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();

            service.add(newInfo);

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

    public class 지자체정보_수정 {

        private SupportInfo normalSupportInfo;

        @Before
        public void init() {
            normalSupportInfo = SupportInfo.builder()
                .region(new Region(REGION))
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();
        }

        @Test
        public void findByRegionName에서_결과값을_찾을_수_없을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.empty());
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.empty());

            ResultInfo resultInfo = ResultInfo.builder().region(REGION).build();

            Throwable thrown = catchThrowable(() -> service.edit(resultInfo));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_REGION);
        }


        @Test
        public void findByRegion에서_결과값을_찾을_수_없을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.of(new Region(REGION)));
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.empty());

            ResultInfo resultInfo = ResultInfo.builder().region(REGION).build();

            Throwable thrown = catchThrowable(() -> service.edit(resultInfo));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_SUPPORT_ID);
        }

        @Test
        public void 결과값이나오고_LIMIT와_RATE값이_포함되어_수정됐을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.of(new Region(REGION)));
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.of(normalSupportInfo));

            String EDITED_INSTITUTE = "기관 수정";
            String EDITED_TARGET = "지원 대상 수정";
            String EDITED_LIMIT = "5천만원 이내";
            String EDITED_RATE = "3.0%~6.0프로";

            ResultInfo requestInfo = ResultInfo.builder()
                .region(REGION)
                .target(EDITED_TARGET)
                .limit(EDITED_LIMIT)
                .rate(EDITED_RATE)
                .institute(EDITED_INSTITUTE).build();

            service.edit(requestInfo);

            verify(repository, times(1)).save(
                assertArg(persisted -> assertThatSupportInfoEntry(persisted)
                    .hasRegion(REGION)
                    .hasTarget(EDITED_TARGET)
                    .hasLimit(EDITED_LIMIT)
                    .hasLimitNum(50000000)
                    .hasRate(EDITED_RATE)
                    .hasMinRate(3.0)
                    .hasAvgRate(4.5)
                    .hasInstitute(EDITED_INSTITUTE)
                )
            );
        }
    }

    public class 지자체정보_검색 {

        private SupportInfo normalSupportInfo;

        @Before
        public void init() {
            normalSupportInfo = SupportInfo.builder()
                .region(new Region(REGION))
                .target(TARGET)
                .usage(USAGE).limit(LIMIT)
                .rate(RATE)
                .institute(INSTITUTE)
                .mgmt(MGMT)
                .reception(RECEPTION)
                .build();
        }

        @Test
        public void findByRegionName에서_결과값을_찾을_수_없을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.empty());
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.empty());

            ResultInfo resultInfo = ResultInfo.builder().region(REGION).build();

            Throwable thrown = catchThrowable(() -> service.search(resultInfo));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_REGION);
        }


        @Test
        public void findByRegion에서_결과값을_찾을_수_없을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.of(new Region(REGION)));
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.empty());

            ResultInfo resultInfo = ResultInfo.builder().region(REGION).build();

            Throwable thrown = catchThrowable(() -> service.search(resultInfo));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_SUPPORT_ID);
        }

        @Test
        public void 결과값이_모두_나왔을때() {
            given(regionRepository.findByRegionName(REGION)).willReturn(Optional.of(new Region(REGION)));
            given(repository.findByRegion(any(Region.class))).willReturn(Optional.of(normalSupportInfo));

            ResultInfo requestInfo = ResultInfo.builder()
                .region(REGION).build();

            ResultInfo resultInfo = service.search(requestInfo);

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

    public class 지원금액으로_내림차순_정렬_출력 {

        public class 결과값_없을때 {

            @Test
            public void EMPTY일때() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(new ArrayList<>()));

                Throwable thrown = catchThrowable(() -> service.limits(5));

                assertThat(thrown).isExactlyInstanceOf(CustomException.class);
                assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_DATA);
            }
        }

        public class 결과값_있을때 {

            private SupportInfo emptyRegionSupportInfo;
            private SupportInfo supportInfo;
            private List<SupportInfo> emptyRegionSupportInfoList;
            private List<SupportInfo> duplicateSupportInfoList;
            private List<SupportInfo> normalSupportInfoList;

            @Before
            public void init() {
                emptyRegionSupportInfo = SupportInfo.builder()
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .institute(INSTITUTE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build();

                supportInfo = SupportInfo.builder()
                    .region(new Region(REGION))
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .institute(INSTITUTE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build();

                emptyRegionSupportInfo = SupportInfo.builder()
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .institute(INSTITUTE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build();

                emptyRegionSupportInfoList = Arrays.asList(
                    emptyRegionSupportInfo
                );

                duplicateSupportInfoList = Arrays.asList(
                    supportInfo,
                    supportInfo,
                    supportInfo
                );

                normalSupportInfoList = Arrays.asList(
                    SupportInfo.builder().region(new Region("경기도")).build(),
                    SupportInfo.builder().region(new Region("강원도")).build(),
                    SupportInfo.builder().region(new Region("강릉시")).build(),
                    SupportInfo.builder().region(new Region("성남시")).build(),
                    SupportInfo.builder().region(new Region("화성시")).build()
                );
            }

            @Test
            public void Region값이_없는_결과값일때() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(emptyRegionSupportInfoList));

                List<String> resultList = service.limits(5);

                assertTrue(CollectionUtils.isEmpty(resultList));
            }

            @Test
            public void 중복된_Region값이_있을때() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(duplicateSupportInfoList));

                List<String> resultList = service.limits(5);

                assertTrue(!CollectionUtils.isEmpty(resultList));
                assertEquals(1, resultList.size());
            }

            @Test
            public void 정상의_경우() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(normalSupportInfoList));

                List<String> resultList = service.limits(5);

                assertTrue(!CollectionUtils.isEmpty(resultList));
                assertEquals(5, resultList.size());
            }
        }
    }

    public class 이차보전비율이_가장_작은_추천기관_출력 {

        public class 결과값_없을때 {

            @Test
            public void EMPTY일때() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(new ArrayList<>()));

                Throwable thrown = catchThrowable(() -> service.minRateInstitute());

                assertThat(thrown).isExactlyInstanceOf(CustomException.class);
                assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_DATA);
            }
        }

        public class 결과값_있을때 {

            private SupportInfo emptyInstituteSupportInfo;
            private SupportInfo supportInfo;
            private List<SupportInfo> emptyInstituteSupportInfoList;
            private List<SupportInfo> normalSupportInfoList;

            @Before
            public void init() {
                emptyInstituteSupportInfo = SupportInfo.builder()
                    .region(new Region(REGION))
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build();

                supportInfo = SupportInfo.builder()
                    .region(new Region(REGION))
                    .target(TARGET)
                    .usage(USAGE).limit(LIMIT)
                    .rate(RATE)
                    .institute(INSTITUTE)
                    .mgmt(MGMT)
                    .reception(RECEPTION)
                    .build();

                emptyInstituteSupportInfoList = Arrays.asList(
                    emptyInstituteSupportInfo
                );

                normalSupportInfoList = Arrays.asList(
                    supportInfo
                );
            }

            @Test
            public void Institute값이_없는_결과값일때() {
                given(repository.findAll(any(Pageable.class)))
                    .willReturn(new PageImpl<>(emptyInstituteSupportInfoList));

                Throwable thrown = catchThrowable(() -> service.minRateInstitute());

                assertThat(thrown).isExactlyInstanceOf(CustomException.class);
                assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_DATA);
            }

            @Test
            public void 정상의_경우() {
                given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(normalSupportInfoList));

                ResultInfo resultInfo = service.minRateInstitute();

                ResultInfo onlyInstituteResultInfo = ResultInfo.builder().institute(INSTITUTE).build();
                assertEquals(onlyInstituteResultInfo, resultInfo);
            }
        }
    }
}
