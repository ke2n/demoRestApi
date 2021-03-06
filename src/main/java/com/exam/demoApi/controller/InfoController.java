package com.exam.demoApi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.demoApi.model.ResultInfo;
import com.exam.demoApi.service.SupportInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yunsung Kim
 */
@RestController
@Slf4j
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final SupportInfoService supportInfoService;

    @GetMapping(value = "/list")
    public List<ResultInfo> list() {
        return supportInfoService.list();
    }

    @PostMapping(value = "/search")
    public ResultInfo search(@RequestBody ResultInfo resultInfo) {
        return supportInfoService.search(resultInfo);
    }

    @PostMapping(value = "/edit")
    public ResultInfo edit(@RequestBody ResultInfo resultInfo) {
        return supportInfoService.edit(resultInfo);
    }

    @GetMapping(value = "/limits/{num}")
    public List<String> limits(@PathVariable Integer num) {
        return supportInfoService.limits(num);
    }

    @GetMapping(value = "/min-rate-institute")
    public ResultInfo minRateInstitute() {
        return supportInfoService.minRateInstitute();
    }
}
