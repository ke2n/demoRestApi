package com.exam.demoApi;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.controller.InfoController;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.interceptor.JwtInterceptor;
import com.exam.demoApi.interceptor.WebConfig;
import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.service.SupportInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.exam.demoApi.exception.ExceptionCode.FAIL;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_DATA;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_REGION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yunsung Kim
 */
@RunWith(SpringRunner.class)
@WebMvcTest(InfoController.class)
@EnableSpringDataWebSupport
public class InfoControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String ROOT_URI = "/api/info";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SupportInfoService service;

    @MockBean
    private JwtInterceptor jwtInterceptor;

    @MockBean
    private WebConfig webConfig;

    private List<ResultInfo> expectedList;

    @Before
    public void setup() throws Exception {
        expectedList = Utils.csvRead(ResultInfo.class, new FileInputStream("src/main/resources/test.csv"));
    }

    @Test
    public void 지자체정보_목록출력__결과값이_없을때() throws Exception {
        given(service.list()).willThrow(new CustomException(NOT_FOUND_DATA));

        defaultResultActions("/list", null)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(NOT_FOUND_DATA.name()));
    }

    @Test
    public void 지자체정보_목록출력__결과값이_있을때() throws Exception {
        given(service.list()).willReturn(expectedList);
        String resultJson = new ObjectMapper().writeValueAsString(expectedList);

        defaultResultActions("/list", null)
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));

    }

    @Test
    public void 지자체정보_검색__GET으로_호출했을때() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(ROOT_URI + "/search")
            .contentType(APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(FAIL.name()))
            .andExpect(jsonPath("@.message").value("Request method 'GET' not supported"));
    }

    @Test
    public void 지자체정보_검색__요청값이_없을때() throws Exception {
        given(service.search(any(ResultInfo.class))).willThrow(new CustomException(NOT_FOUND_REGION));

        defaultResultActions("/search", ResultInfo.builder().region("없음").build())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(NOT_FOUND_REGION.name()));
    }

    @Test
    public void 지자체정보_검색__요청값이_있고_정상출력() throws Exception {
        given(service.search(any(ResultInfo.class))).willReturn(expectedList.get(0));
        String resultJson = new ObjectMapper().writeValueAsString(expectedList.get(0));

        defaultResultActions("/search", expectedList.get(0))
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));
    }

    @Test
    public void 지자체정보_수정__GET으로_호출했을때() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(ROOT_URI + "/edit")
            .contentType(APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(FAIL.name()))
            .andExpect(jsonPath("@.message").value("Request method 'GET' not supported"));
    }

    @Test
    public void 지자체정보_수정__요청값이_없을때() throws Exception {
        given(service.edit(any(ResultInfo.class))).willThrow(new CustomException(NOT_FOUND_REGION));

        defaultResultActions("/edit", ResultInfo.builder().region("없음").build())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(NOT_FOUND_REGION.name()));
    }

    @Test
    public void 지자체정보_수정__요청값이_있고_정상_수정결과_출력() throws Exception {
        given(service.edit(any(ResultInfo.class))).willReturn(expectedList.get(0));
        String resultJson = new ObjectMapper().writeValueAsString(expectedList.get(0));

        defaultResultActions("/edit", expectedList.get(0))
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));
    }

    @Test
    public void 지원금액으로_내림차순_정렬_출력__요청값이_없을때() throws Exception {
        given(service.limits(any(Integer.class))).willThrow(new CustomException(NOT_FOUND_DATA));

        defaultResultActions("/limits/3", null)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(NOT_FOUND_DATA.name()));
    }

    @Test
    public void 지원금액으로_내림차순_정렬_출력__요청값이_있고_정상출력() throws Exception {
        List<String> resultList = expectedList.stream()
            .map(ResultInfo::getRegion)
            .limit(3).collect(Collectors.toList());
        given(service.limits(any(Integer.class))).willReturn(resultList);
        String resultJson = new ObjectMapper().writeValueAsString(resultList);

        defaultResultActions("/limits/3", null)
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));
    }

    @Test
    public void 이차보전비율이_가장_작은_추천기관_출력__요청값이_없을때() throws Exception {
        given(service.minRateInstitute()).willThrow(new CustomException(NOT_FOUND_DATA));

        defaultResultActions("/min-rate-institute", null)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(NOT_FOUND_DATA.name()));
    }

    @Test
    public void 이차보전비율이_가장_작은_추천기관_출력__요청값이_있고_정상출력() throws Exception {
        ResultInfo resultinfo = ResultInfo.builder().institute("경기신용보증재단").build();
        given(service.minRateInstitute()).willReturn(resultinfo);
        String resultJson = new ObjectMapper().writeValueAsString(resultinfo);

        defaultResultActions("/min-rate-institute", null)
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));
    }

    private ResultActions defaultResultActions(String url, ResultInfo resultInfo) throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.get(ROOT_URI + url)
            .contentType(APPLICATION_JSON_UTF8);

        if (resultInfo != null) {
            String resultJson = new ObjectMapper().writeValueAsString(resultInfo);
            rb = MockMvcRequestBuilders.post(ROOT_URI + url)
                .contentType(APPLICATION_JSON_UTF8).content(resultJson);
        }

        return mvc.perform(rb)
            .andDo(print());
    }
}
