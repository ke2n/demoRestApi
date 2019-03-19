/*
 * Copyright (c) 2019. 3. 5.
 * Yunsung Kim
 */

package com.exam.demoApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.repository.RegionRepository;
import com.exam.demoApi.repository.SupportInfoRepository;
import com.exam.demoApi.service.DataService;
import com.exam.demoApi.service.SupportInfoService;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApiServiceTests {

    @Autowired
    private DataService dataService;

    @Autowired
    private SupportInfoService supportInfoService;

    @Autowired
    private SupportInfoRepository supportInfoRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Before
    public void setup() {
        String csvFile = "src/main/resources/test.csv";
        try {
            InputStream fin = new FileInputStream(csvFile);
            dataService.addAll(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanAll() {
        supportInfoRepository.deleteAll();
        regionRepository.deleteAll();
    }

    @Test
    public void listTest() {

        List<ResultInfo> list = supportInfoService.list();

        assertFalse(CollectionUtils.isEmpty(list));
    }

    @Test
    public void searchTest() {
        ResultInfo resultInfo = supportInfoService.search(null);

        log.info("{}", resultInfo);
    }
}
