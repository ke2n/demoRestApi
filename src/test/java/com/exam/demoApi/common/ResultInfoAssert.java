package com.exam.demoApi.common;

import org.assertj.core.api.AbstractAssert;

import com.exam.demoApi.model.ResultInfo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ResultInfo}에 대한 assert 조건 정의
 * @author yunsung Kim
 */
public final class ResultInfoAssert extends AbstractAssert<ResultInfoAssert, ResultInfo> {

    private ResultInfoAssert(ResultInfo actual) {
        super(actual, ResultInfoAssert.class);
    }

    public static ResultInfoAssert assertThatResultInfoEntry(ResultInfo actual) {
        return new ResultInfoAssert(actual);
    }

    public ResultInfoAssert hasRegion(String expectedRegion) {
        isNotNull();

        String actualRegion = actual.getRegion();
        assertThat(actualRegion)
            .overridingErrorMessage("Expected region to be <%s> but was <%s>",
                expectedRegion, actualRegion
            ).isEqualTo(expectedRegion);

        return this;
    }

    public ResultInfoAssert hasTarget(String expectedTarget) {
        isNotNull();

        String actualTarget = actual.getTarget();
        assertThat(actualTarget)
            .overridingErrorMessage("Expected target to be <%s> but was <%s>",
                expectedTarget, actualTarget
            ).isEqualTo(expectedTarget);

        return this;
    }

    public ResultInfoAssert hasUsage(String expectedUsage) {
        isNotNull();

        String actualUsage = actual.getUsage();
        assertThat(actualUsage)
            .overridingErrorMessage("Expected usage to be <%s> but was <%s>",
                expectedUsage, actualUsage
            ).isEqualTo(expectedUsage);

        return this;
    }

    public ResultInfoAssert hasLimit(String expectedLimit) {
        isNotNull();

        String actualLimit = actual.getLimit();
        assertThat(actualLimit)
            .overridingErrorMessage("Expected limit to be <%s> but was <%s>",
                expectedLimit, actualLimit
            ).isEqualTo(expectedLimit);

        return this;
    }

    public ResultInfoAssert hasRate(String expectedRate) {
        isNotNull();

        String actualRate = actual.getRate();
        assertThat(actualRate)
            .overridingErrorMessage("Expected rate to be <%s> but was <%s>",
                expectedRate, actualRate
            ).isEqualTo(expectedRate);

        return this;
    }

    public ResultInfoAssert hasInstitute(String expectedInstitute) {
        isNotNull();

        String actualInstitute = actual.getInstitute();
        assertThat(actualInstitute)
            .overridingErrorMessage("Expected institute to be <%s> but was <%s>",
                expectedInstitute, actualInstitute
            ).isEqualTo(expectedInstitute);

        return this;
    }

    public ResultInfoAssert hasMgmt(String expectedMgmt) {
        isNotNull();

        String actualMgmt = actual.getMgmt();
        assertThat(actualMgmt)
            .overridingErrorMessage("Expected mgmt to be <%s> but was <%s>",
                expectedMgmt, actualMgmt
            ).isEqualTo(expectedMgmt);

        return this;
    }

    public ResultInfoAssert hasReception(String expectedReception) {
        isNotNull();

        String actualReception = actual.getReception();
        assertThat(actualReception)
            .overridingErrorMessage("Expected reception to be <%s> but was <%s>",
                expectedReception, actualReception
            ).isEqualTo(expectedReception);

        return this;
    }

    public ResultInfoAssert hasNoRegion() {
        isNotNull();

        String actualRegion = actual.getRegion();
        assertThat(actualRegion)
            .overridingErrorMessage("Expected region to be <null> but was <%s>", actualRegion)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoTarget() {
        isNotNull();

        String actualTarget = actual.getTarget();
        assertThat(actualTarget)
            .overridingErrorMessage("Expected target to be <null> but was <%s>", actualTarget)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoUsage() {
        isNotNull();

        String actualUsage = actual.getUsage();
        assertThat(actualUsage)
            .overridingErrorMessage("Expected usage to be <null> but was <%s>", actualUsage)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoLimit() {
        isNotNull();

        String actualLimit = actual.getLimit();
        assertThat(actualLimit)
            .overridingErrorMessage("Expected limit to be <null> but was <%s>", actualLimit)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoRate() {
        isNotNull();

        String actualRate = actual.getRate();
        assertThat(actualRate)
            .overridingErrorMessage("Expected rate to be <null> but was <%s>", actualRate)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoInstitute() {
        isNotNull();

        String actualInstitute = actual.getInstitute();
        assertThat(actualInstitute)
            .overridingErrorMessage("Expected institute to be <null> but was <%s>", actualInstitute)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoMgmt() {
        isNotNull();

        String actualMgmt = actual.getMgmt();
        assertThat(actualMgmt)
            .overridingErrorMessage("Expected mgmt to be <null> but was <%s>", actualMgmt)
            .isNull();

        return this;
    }

    ResultInfoAssert hasNoReception() {
        isNotNull();

        String actualReception = actual.getReception();
        assertThat(actualReception)
            .overridingErrorMessage("Expected reception to be <null> but was <%s>", actualReception)
            .isNull();

        return this;
    }

    public ResultInfoAssert isAllEmptyValue() {
        hasNoRegion();
        hasNoTarget();
        hasNoUsage();
        hasNoLimit();
        hasNoRate();
        hasNoInstitute();
        hasNoMgmt();
        hasNoReception();

        return this;
    }
}
