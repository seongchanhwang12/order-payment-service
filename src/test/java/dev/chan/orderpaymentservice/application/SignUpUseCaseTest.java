package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.member.SignUpUseCase;
import dev.chan.orderpaymentservice.common.SignUpCommandMother;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignUpUseCaseTest {

    @InjectMocks
    SignUpUseCase sut;

    @Mock
    MemberRepository memberRepository;

    @Captor
    ArgumentCaptor<Member> memberCaptor;



    /**
     * 회원 가입 성공 (해피 패스)
     *
     * 설명: 사용자가 회원가입을 하면
     * - 패스워드와 패스워드 확인 값이 일치하는지 검증하고,
     * - 회원 레파지토리에 (Member) 를 저장합니다.
     *
     * 테스트 목적:
     * - 회원 생성의 정상 처리 여부를 검증합니다.
     * - 비밀번호가 유효하게 입력되었는지 검증합니다.
     *
     * 검증 포인트:
     * 1. 사용자 입력데이터가 올바르면, 회원이 생성되어야합니다.
     * 2. 패스워드와 패스워드 확인 값이 일치하면 회원 생성에 성공합니다.
     *
     */
    @Test
    void 회원폼을받아서_회원저장에_성공한다() {
        //given
        String email = "email";
        String password = "password";
        String phone = "01011111111";
        String name = "name";

        doNothing().when(memberRepository).save(any(Member.class));

        //when
        sut.handle(SignUpCommandMother.of(email, password, phone, name));

        //then
        verify(memberRepository).save(memberCaptor.capture());
        Member saveMember = memberCaptor.getValue();
        assertThat(saveMember.getEmail()).isEqualTo(email);
        assertThat(saveMember.getPassword()).isEqualTo(password);
        assertThat(saveMember.getName()).isEqualTo(name);
    }




}