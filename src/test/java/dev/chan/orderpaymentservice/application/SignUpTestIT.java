package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.SignUpCommand;
import dev.chan.orderpaymentservice.common.SignUpCommandMother;
import dev.chan.orderpaymentservice.domain.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SignUpTestIT {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SignUpUseCase signUpUseCase;


    /**
     * 회원 가입 성공 통합테스트 (해피 패스)
     * 설명: 사용자 회원 가입시 DB에 데이터가 정상적으로 저장되었는지 확인합니다.
     *
     * 테스트 목적:
     * - 컨트롤러에게 전달받은 데이터가 올바를 때 회원 유스케이스에서 DB에 정상적으로 저장 요청이 실행되는지 검증
     *
     * 검증 포인트:
     * 1. use case 실행시 예외가 발생하지 않은 경우 회원 가입에 성공합니다.
     * 2. 입력한 데이터와 동일한 회원 가입 데이터가 DB에 저장됩니다.
     *
     */
    @Test
    @Transactional
    void 올바른_회원가입_커맨드가_있으면_회원가입_성공한다() {
        //given
        SignUpCommand cmd = SignUpCommandMother.newCommand();

        //when
        signUpUseCase.handle(cmd);
        Optional<Member> foundMember = memberRepository.findByEmail(cmd.email());

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo(cmd.email());

    }

    /**
     * 회원 가입 실패시 예외처리 통합 테스트 (언해피 패스)
     * 설명: 이미 저장된 ID를 회원가입 요청시 트랜잭션 롤백 여부 및 지정된 정책 예외가 발생하는지 테스트합니다.
     *
     * 테스트 목적:
     * - 비밀번호가 유효하게 입력되었는지 검증합니다.
     *
     * 검증 포인트:
     * 1. 이미 가입된 ID를 중복 가입 요청 할 경우 DuplicateMemberException 이 던져집니다.
     * 2. 예외 발생시 데이터가 롤백됩니다.
     *
     */
    @Test
    @Transactional
    void 회원가입시_존재하는_이메일이면_중복예외를_던진다() {
        //given
        String email = "email@test.com";
        SignUpCommand cmd = SignUpCommandMother.withEmail(email);
        Member member = MemberMother.withEmail(email);

        //when & then
        memberRepository.save(member);
        assertThatThrownBy(()-> signUpUseCase.handle(cmd))
                .isInstanceOf(DuplicateMemberException.class)
                .extracting("errorCode").isEqualTo(MemberError.DUPLICATE_EMAIL);
    }



}
