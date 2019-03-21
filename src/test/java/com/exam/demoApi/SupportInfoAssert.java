package com.exam.demoApi;

import org.assertj.core.api.AbstractAssert;

import com.exam.demoApi.domain.Region;
import com.exam.demoApi.domain.SupportInfo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link SupportInfo}에 대한 assert 조건 정의
 * @author yunsung Kim
 */
final class SupportInfoAssert extends AbstractAssert<SupportInfoAssert, SupportInfo> {

    private SupportInfoAssert(SupportInfo actual) {
        super(actual, SupportInfoAssert.class);
    }

    static SupportInfoAssert assertThatSupportInfoEntry(SupportInfo actual) {
        return new SupportInfoAssert(actual);
    }

    SupportInfoAssert hasRegion(String strRegion) {
        isNotNull();
        Region expectedRegion = new Region(strRegion);
        Region actualRegion = actual.getRegion();
        assertThat(expectedRegion)
            .overridingErrorMessage("Expected region to be <%s> but was <%s>",
                expectedRegion, actualRegion
            ).isEqualTo(expectedRegion);

        return this;
    }

    SupportInfoAssert hasTarget(String expectedTarget) {
        isNotNull();

        String actualTarget = actual.getTarget();
        assertThat(actualTarget)
            .overridingErrorMessage("Expected target to be <%s> but was <%s>",
                expectedTarget, actualTarget
            ).isEqualTo(expectedTarget);

        return this;
    }

    SupportInfoAssert hasUsage(String expectedUsage) {
        isNotNull();

        String actualUsage = actual.getUsage();
        assertThat(actualUsage)
            .overridingErrorMessage("Expected usage to be <%s> but was <%s>",
                expectedUsage, actualUsage
            ).isEqualTo(expectedUsage);

        return this;
    }

    SupportInfoAssert hasLimit(String expectedLimit) {
        isNotNull();

        String actualLimit = actual.getLimit();
        assertThat(actualLimit)
            .overridingErrorMessage("Expected limit to be <%s> but was <%s>",
                expectedLimit, actualLimit
            ).isEqualTo(expectedLimit);

        return this;
    }

    SupportInfoAssert hasLimitNum(long expectedLimitNum) {
        isNotNull();

        Long actualLimitNum = actual.getLimitNum();
        assertThat(actualLimitNum)
            .overridingErrorMessage("Expected limitNum to be <%s> but was <%s>",
                expectedLimitNum, actualLimitNum
            ).isEqualTo(expectedLimitNum);

        return this;
    }

    SupportInfoAssert hasRate(String expectedRate) {
        isNotNull();

        String actualRate = actual.getRate();
        assertThat(actualRate)
            .overridingErrorMessage("Expected rate to be <%s> but was <%s>",
                expectedRate, actualRate
            ).isEqualTo(expectedRate);

        return this;
    }

    SupportInfoAssert hasMinRate(Double expectedMinRate) {
        isNotNull();

        Double actualMinRate = actual.getMinRate();
        assertThat(actualMinRate)
            .overridingErrorMessage("Expected minRate to be <%s> but was <%s>",
                expectedMinRate, actualMinRate
            ).isEqualTo(expectedMinRate);

        return this;
    }

    SupportInfoAssert hasAvgRate(Double expectedAvgRate) {
        isNotNull();

        Double actualAvgRate = actual.getAvgRate();
        assertThat(actualAvgRate)
            .overridingErrorMessage("Expected avgRate to be <%s> but was <%s>",
                expectedAvgRate, actualAvgRate
            ).isEqualTo(expectedAvgRate);

        return this;
    }

    SupportInfoAssert hasInstitute(String expectedInstitute) {
        isNotNull();

        String actualInstitute = actual.getInstitute();
        assertThat(actualInstitute)
            .overridingErrorMessage("Expected institute to be <%s> but was <%s>",
                expectedInstitute, actualInstitute
            ).isEqualTo(expectedInstitute);

        return this;
    }

    SupportInfoAssert hasMgmt(String expectedMgmt) {
        isNotNull();

        String actualMgmt = actual.getMgmt();
        assertThat(actualMgmt)
            .overridingErrorMessage("Expected mgmt to be <%s> but was <%s>",
                expectedMgmt, actualMgmt
            ).isEqualTo(expectedMgmt);

        return this;
    }

    SupportInfoAssert hasReception(String expectedReception) {
        isNotNull();

        String actualReception = actual.getReception();
        assertThat(actualReception)
            .overridingErrorMessage("Expected reception to be <%s> but was <%s>",
                expectedReception, actualReception
            ).isEqualTo(expectedReception);

        return this;
    }

    SupportInfoAssert hasNoId() {
        isNotNull();

        Integer actualSupportId = actual.getSupportId();
        assertThat(actualSupportId)
            .overridingErrorMessage("Expected region to be <null> but was <%s>", actualSupportId)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoRegion() {
        isNotNull();

        Region actualRegion = actual.getRegion();
        assertThat(actualRegion)
            .overridingErrorMessage("Expected region to be <null> but was <%s>", actualRegion)
            .isEqualTo(new Region());

        return this;
    }

    SupportInfoAssert hasNoTarget() {
        isNotNull();

        String actualTarget = actual.getTarget();
        assertThat(actualTarget)
            .overridingErrorMessage("Expected target to be <null> but was <%s>", actualTarget)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoUsage() {
        isNotNull();

        String actualUsage = actual.getUsage();
        assertThat(actualUsage)
            .overridingErrorMessage("Expected usage to be <null> but was <%s>", actualUsage)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoLimit() {
        isNotNull();

        String actualLimit = actual.getLimit();
        assertThat(actualLimit)
            .overridingErrorMessage("Expected limit to be <null> but was <%s>", actualLimit)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoRate() {
        isNotNull();

        String actualRate = actual.getRate();
        assertThat(actualRate)
            .overridingErrorMessage("Expected rate to be <null> but was <%s>", actualRate)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoInstitute() {
        isNotNull();

        String actualInstitute = actual.getInstitute();
        assertThat(actualInstitute)
            .overridingErrorMessage("Expected institute to be <null> but was <%s>", actualInstitute)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoMgmt() {
        isNotNull();

        String actualMgmt = actual.getMgmt();
        assertThat(actualMgmt)
            .overridingErrorMessage("Expected mgmt to be <null> but was <%s>", actualMgmt)
            .isNull();

        return this;
    }

    SupportInfoAssert hasNoReception() {
        isNotNull();

        String actualReception = actual.getReception();
        assertThat(actualReception)
            .overridingErrorMessage("Expected reception to be <null> but was <%s>", actualReception)
            .isNull();

        return this;
    }

    SupportInfoAssert isAllEmptyValue() {
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
