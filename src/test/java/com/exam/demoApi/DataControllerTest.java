package com.exam.demoApi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.exam.demoApi.common.Utils;
import com.exam.demoApi.controller.DataController;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.interceptor.JwtInterceptor;
import com.exam.demoApi.interceptor.WebConfig;
import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.exam.demoApi.exception.ExceptionCode.ONLY_SUPPORT_UTF8;
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
@WebMvcTest(DataController.class)
@EnableSpringDataWebSupport
public class DataControllerTest {

    private static final String ROOT_URI = "/api/data";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataService service;

    @MockBean
    private JwtInterceptor jwtInterceptor;

    @MockBean
    private WebConfig webConfig;

    private List<ResultInfo> expectedList;

    private FileInputStream fis;

    private MockMultipartFile cvsFile;

    @Before
    public void setup() throws Exception {
        fis = new FileInputStream("src/main/resources/test.csv");
        expectedList = Utils.csvRead(ResultInfo.class, fis);
        cvsFile = new MockMultipartFile("test.csv", "", "text/csv", fis);
    }

    @Test
    public void CSV레코드를_데이터베이스에_저장__UTF8형식이_아닐때() throws Exception {
        given(service.addAll(any(InputStream.class))).willThrow(new CustomException(ONLY_SUPPORT_UTF8));

        defaultResultActions("/upload", cvsFile)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("@.code").value(ONLY_SUPPORT_UTF8.name()));
    }

    @Test
    public void CSV레코드를_데이터베이스에_저장__정상저장_및_출력() throws Exception {
        given(service.addAll(any(InputStream.class))).willReturn(expectedList);
        String resultJson = new ObjectMapper().writeValueAsString(expectedList);

        defaultResultActions("/upload", cvsFile)
            .andExpect(status().isOk())
            .andExpect(content().string(resultJson));
    }

    private ResultActions defaultResultActions(String url, MockMultipartFile multipartFile) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.multipart(ROOT_URI + url)
            .file("file", multipartFile.getBytes())
            .characterEncoding("UTF-8")
        ).andDo(print());
    }
}
