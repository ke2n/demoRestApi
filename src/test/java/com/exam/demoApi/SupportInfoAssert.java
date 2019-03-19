package com.exam.demoApi;

import org.assertj.core.api.AbstractAssert;

import com.exam.demoApi.domain.SupportInfo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class provides a fluent API that can be used for writing assertions
 * to {@link com.exam.demoApi.domain.SupportInfo} objects.
 *
 * @author yunsung Kim
 */
final class SupportInfoAssert extends AbstractAssert<SupportInfoAssert, SupportInfo> {

    private SupportInfoAssert(SupportInfo actual) {
        super(actual, SupportInfoAssert.class);
    }

    static SupportInfoAssert assertThatSupportInfoEntry(SupportInfo actual) {
        return new SupportInfoAssert(actual);
    }

    SupportInfoAssert hasInstitute(String expectedInstitute) {
        isNotNull();

        String actualInstitute = actual.getInstitute();
        assertThat(actualInstitute)
            .overridingErrorMessage(String.format(
                "Expected institute to be <%s> but was <%s>.",
                expectedInstitute,
                actualInstitute
            ))
            .isEqualTo(expectedInstitute);

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

    SupportInfoAssert hasId(Long expectedId) {
        isNotNull();

        int actualId = actual.getSupportId();
        assertThat(actualId)
            .overridingErrorMessage("Expected id to be <%d> but was <%d>",
                expectedId,
                actualId
            )
            .isEqualTo(expectedId);

        return this;
    }

    SupportInfoAssert hasNoId() {
        isNotNull();

        int actualId = actual.getSupportId();
        assertThat(actualId)
            .overridingErrorMessage("Expected id to be <null> but was <%d>.", actualId)
            .isNull();

        return this;
    }
}
