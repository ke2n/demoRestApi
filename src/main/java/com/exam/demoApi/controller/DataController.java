package com.exam.demoApi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.demoApi.domain.SupportInfo;
import com.exam.demoApi.service.SupportInfoService;
import com.exam.demoApi.util.CsvUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final SupportInfoService supportInfoService;

    @PostMapping(value = "/upload", consumes = "text/csv")
    public void uploadSimple(@RequestBody InputStream body) throws IOException {
        List<SupportInfo> list = CsvUtils.read(SupportInfo.class, body);

        log.info("{}", list);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        List<SupportInfo> list = supportInfoService.addAll(file);

        log.info("{}", list);
    }

    @GetMapping(value = "/list")
    public List<SupportInfo> list() {
        List<SupportInfo> list = supportInfoService.list();

        log.info("{}", list);
        return list;
    }
}
