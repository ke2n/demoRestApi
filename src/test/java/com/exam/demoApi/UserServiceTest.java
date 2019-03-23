package com.exam.demoApi;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.exam.demoApi.domain.User;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.repository.UserRepository;
import com.exam.demoApi.service.UserService;
import com.nitorcreations.junit.runners.NestedRunner;

import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_DATA;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_USER;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_EXIST_USERNAME;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_REQUIRED_PASSWORD;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_REQUIRED_USERNAME;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * {@link UserServiceTest}에 대한 단위 테스트
 *
 * @author yunsung Kim
 */
@RunWith(NestedRunner.class)
public class UserServiceTest {

    private UserRepository repository;

    private UserService service;

    @Before
    public void setUp() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    public class signin메서드_테스트 {

        @Test
        public void 파라메터가_Null일때() {
            Throwable thrown = catchThrowable(() -> service.signin(null));
            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void UserName또는_password가_없을때() {
            User emptyPasswordUser = User.builder()
                .username("user")
                .password("").build();
            Throwable thrown = catchThrowable(() -> service.signin(emptyPasswordUser));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);

            User emptyUserNameUser = User.builder()
                .username("")
                .password("password").build();
            Throwable thrown2 = catchThrowable(() -> service.signin(emptyUserNameUser));

            assertThat(thrown2).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown2).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void DB에_없는_User일때() {
            given(repository.findByUsername(any(String.class))).willReturn(Optional.empty());

            User user = User.builder()
                .username("user")
                .password("password").build();
            Throwable thrown = catchThrowable(() -> service.signin(user));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void 요청정보가_DB값과_다를때() {
            User dbUser = User.builder()
                .username("user")
                .password("$2a$10$jZ7qnMGdWvVRW.XP/VK21udfW5NVzfLVslaOfI5sE2VZtRksStX1y").build();
            given(repository.findByUsername(any(String.class))).willReturn(Optional.of(dbUser));

            User requestedUser = User.builder()
                .username("user")
                .password("1234").build();
            Throwable thrown = catchThrowable(() -> service.signin(requestedUser));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void 요청정보가_DB값과_일치할때() {
            User dbUser = User.builder()
                .username("user")
                .password("$2a$10$jZ7qnMGdWvVRW.XP/VK21udfW5NVzfLVslaOfI5sE2VZtRksStX1y").build();
            given(repository.findByUsername(any(String.class))).willReturn(Optional.of(dbUser));

            User requestedUser = User.builder()
                .username("user")
                .password("1212").build();
            User resultUser = service.signin(requestedUser);

            assertNotNull(resultUser);
            assertEquals(requestedUser.getUsername(), resultUser.getUsername());
        }
    }

    public class signup메서드_테스트 {

        @Test
        public void 파라메터가_Null일때() {
            Throwable thrown = catchThrowable(() -> service.signup(null));
            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_DATA);
        }

        @Test
        public void UserName또는_password가_없을때() {
            User emptyPasswordUser = User.builder()
                .username("user")
                .password("").build();
            Throwable thrown = catchThrowable(() -> service.signup(emptyPasswordUser));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(SIGNUP_REQUIRED_PASSWORD);

            User emptyUserNameUser = User.builder()
                .username("")
                .password("password").build();
            Throwable thrown2 = catchThrowable(() -> service.signup(emptyUserNameUser));

            assertThat(thrown2).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown2).getResultCode()).isEqualTo(SIGNUP_REQUIRED_USERNAME);
        }

        @Test
        public void DB에_있는_User일때() {
            User dbUser = User.builder()
                .username("user")
                .password("$2a$10$jZ7qnMGdWvVRW.XP/VK21udfW5NVzfLVslaOfI5sE2VZtRksStX1y").build();
            given(repository.findByUsername(any(String.class))).willReturn(Optional.of(dbUser));

            User user = User.builder()
                .username("user")
                .password("password").build();
            Throwable thrown = catchThrowable(() -> service.signup(user));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(SIGNUP_EXIST_USERNAME);
        }

        @Test
        public void 요청정보를_DB에_저장하였을때() {
            User dbUser = User.builder()
                .username("user")
                .password("$2a$10$jZ7qnMGdWvVRW.XP/VK21udfW5NVzfLVslaOfI5sE2VZtRksStX1y").build();
            given(repository.save(any(User.class))).willReturn(dbUser);

            User requestedUser = User.builder()
                .username("user")
                .password("1234").build();

            User resultUser = service.signup(requestedUser);

            assertNotNull(resultUser);
            assertEquals(requestedUser.getUsername(), resultUser.getUsername());
        }
    }

    public class findByUsername메서드_테스트 {

        @Test
        public void 파라메터가_Null일때() {
            Throwable thrown = catchThrowable(() -> service.findByUsername(null));
            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void DB에_없는_User일때() {
            given(repository.findByUsername(any(String.class))).willReturn(Optional.empty());

            Throwable thrown = catchThrowable(() -> service.findByUsername("user"));

            assertThat(thrown).isExactlyInstanceOf(CustomException.class);
            assertThat(((CustomException) thrown).getResultCode()).isEqualTo(NOT_FOUND_USER);
        }

        @Test
        public void DB에_있는_User일때() {
            User dbUser = User.builder()
                .username("user")
                .password("$2a$10$jZ7qnMGdWvVRW.XP/VK21udfW5NVzfLVslaOfI5sE2VZtRksStX1y").build();
            given(repository.findByUsername(any(String.class))).willReturn(Optional.of(dbUser));

            User resultUser = service.findByUsername("user");

            assertNotNull(resultUser);
            assertFalse(StringUtils.isEmpty(resultUser.getUsername()));
        }
    }
}
