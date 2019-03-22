package com.exam.demoApi.controller;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.service.DataService;

import lombok.RequiredArgsConstructor;

/**
 * @author yunsung Kim
 */
@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public List<ResultInfo> uploadMultipart(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        return dataService.addAll(is);
    }
}
