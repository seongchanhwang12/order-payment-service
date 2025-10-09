package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.SignUpCommand;
import dev.chan.orderpaymentservice.domain.member.DuplicateMemberException;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpUseCase {

    private final MemberRepository memberRepository;

    @Transactional
    public void handle(SignUpCommand cmd) {
        Member member = Member.signUp(
                cmd.email(),
                cmd.password(),
                cmd.phone(),
                cmd.name()
                );

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMemberException(MemberError.DUPLICATE_EMAIL, "email already exists. email = [" + cmd.email() + "]");
        }
    }
}
