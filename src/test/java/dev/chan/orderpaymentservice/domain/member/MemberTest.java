package dev.chan.orderpaymentservice.domain.member;

import dev.chan.orderpaymentservice.common.PolicyViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {


    @Test
    void signUp호출시_password와_passwordConfirm이_일치하지않으면_정책위반예외가발생한다() {
        //given
        String password = "password";
        String passwordConfirm = "password2";

        //when & then
        assertThatThrownBy(()->Member.signUp("email", password, passwordConfirm, "010", "kim"))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password");
    }
}