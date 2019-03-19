package com.exam.demoApi;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.exam.demoApi.domain.SupportInfo;
import com.nitorcreations.junit.runners.NestedRunner;

/**
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class SupportInfoTest {

    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private static final int MAX_LENGTH_TITLE = 100;

    private static final String TARGET = "강릉시 소재 중소기업으로서 강릉시장이 추천한 자";
    private static final String TITLE = "title";

    private static final String UPDATED_DESCRIPTION = "updatedDescription";
    private static final String UPDATED_TITLE = "updatedTitle";

    public class Build {

        public class WhenRegionIsInvalid {

            public class WhenRegionIsNull {

                @Test(expected = NullPointerException.class)
                public void shouldThrowException() {
                    SupportInfo.builder()
                        .region(null)
                        .target(TARGET)
                        .build();
                }
            }
        }
    }

}
