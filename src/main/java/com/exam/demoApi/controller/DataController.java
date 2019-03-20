package com.exam.demoApi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.demoApi.domain.ResultInfo;
import com.exam.demoApi.service.DataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public List<ResultInfo> uploadMultipart(@RequestParam("file") MultipartFile file) {
        List<ResultInfo> list = new ArrayList<>();
        try {
            InputStream is = file.getInputStream();
            list = dataService.addAll(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("{}", list);

        return list;
    }
}
