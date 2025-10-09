package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.SignUpCommand;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpUseCase {

    private final MemberRepository memberRepository;

    public void handle(SignUpCommand cmd) {
        Member mem = Member.signUp(
                cmd.email(),
                cmd.password(),
                cmd.passwordConfirm(),
                cmd.phone(),
                cmd.name()
                );

        memberRepository.save(mem);
    }
}
