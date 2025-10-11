package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.auth.LoginUseCase;
import dev.chan.orderpaymentservice.application.auth.dto.LoginCommand;
import dev.chan.orderpaymentservice.application.auth.dto.LoginResult;
import dev.chan.orderpaymentservice.common.LoginCommandMother;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberMother;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginIT {

    @Autowired
    LoginUseCase loginUseCase;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입된_ID_PWD를_받으면_로그인에_성공한다() {
        //given
        String email = "test@chan.dev";
        String password = "1234";
        LoginCommand cmd = LoginCommandMother.withEmailAndPassword(email,password);

        //when
        Member member = MemberMother.withEmailAndPassword(email, password);
        memberRepository.save(member);
        LoginResult result = loginUseCase.handle(cmd);

        //then
        Assertions.assertThat(result).isNotNull();

    }
}
