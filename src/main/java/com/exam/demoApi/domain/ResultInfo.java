package com.exam.demoApi.domain;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;

@Data
public class ResultInfo {

    private String region;

    private String target;

    private String usage;

    private String limit;

    private String rate;

    private String institute;

    private String mgmt;

    private String reception;

    @JsonSetter("구분")
    public void setCsvSupportId(int supportId) {
    }

    @JsonSetter("지자체명(기관명)")
    public void setCsvRegion(String region) {
        this.region = region;
    }

    @JsonSetter("지원대상")
    public void setCsvTarget(String target) {
        this.target = target;
    }

    @JsonSetter("용도")
    public void setCsvUsage(String usage) {
        this.usage = usage;
    }

    @JsonSetter("지원한도")
    public void setCsvLimit(String limit) {
        this.limit = limit;
    }

    @JsonSetter("이차보전")
    public void setCsvRate(String rate) {
        this.rate = rate;
    }

    @JsonSetter("추천기관")
    public void setCsvInstitute(String institute) {
        this.institute = institute;
    }

    @JsonSetter("관리점")
    public void setCsvMgmt(String mgmt) {
        this.mgmt = mgmt;
    }

    @JsonSetter("취급점")
    public void setCsvReception(String reception) {
        this.reception = reception;
    }
}
