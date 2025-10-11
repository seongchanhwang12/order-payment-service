package dev.chan.orderpaymentservice.application.auth;

import dev.chan.orderpaymentservice.application.auth.dto.LoginCommand;
import dev.chan.orderpaymentservice.application.auth.dto.LoginResult;
import dev.chan.orderpaymentservice.common.PolicyViolationException;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LoginUseCase {

    private final MemberRepository memberRepository;

    public LoginResult handle(LoginCommand cmd){
        Member loginMember = memberRepository.findByEmail(cmd.email())
                .filter(m -> m.isMatch(cmd.password()))
                .orElseThrow(() -> new PolicyViolationException(MemberError.AUTHENTICATION_FAIL,"Invalid credentials"));

        return LoginResult.of(loginMember);
    }
}