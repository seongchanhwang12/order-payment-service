package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.auth.LoginUseCase;
import dev.chan.orderpaymentservice.application.auth.dto.LoginCommand;
import dev.chan.orderpaymentservice.application.auth.dto.LoginResult;
import dev.chan.orderpaymentservice.common.LoginCommandMother;
import dev.chan.orderpaymentservice.common.PolicyViolationException;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.domain.member.MemberMother;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @InjectMocks
    LoginUseCase sut;

    @Mock
    MemberRepository memberRepository;

    /**
     * 로그인 성공 (해피 패스)
     *
     * 설명: 사용자 로그인 성공시 회원 정보를 반환하는 테스트
     *
     * 테스트 목적:
     * - 로그인 시 정상적으로 협력 객체를 호출하는지 검증합니다. (MemberRepository)
     * - 로그인 요청 데이터가 반환되는 회원 정보와 일치하는지 검증합니다.
     *
     * 검증 포인트:
     * 1. 로그인 검증 성공시 저장소가 조회되고, 회원정보가 존재해야합니다.
     * 2. 로그인 요청한 회원의 ID와 회원 저장소에서 조회된 회원의 ID가 같아야합니다. ( 같은 회원 )
     * 3. 반환되는 회원 정보는 로그인 요청한 회원의 email 과 일치해야합니다.
     * 4. 회원 조회 후엔 더 이상 회원 저장소와의 추가 상호작용이 발생하지 않아야 합니다. (불필요한 쿼리나 더티체킹으로 인한 부작용 방지)
     */
    @Test
    void 로그인_요청시_이메일과_패스워드가_일치하면_회원정보를_반환한다() {
        //given
        String email = "memberId@test.com";
        String password = "password";
        Member member = MemberMother.withEmailAndPassword(email, password);

        LoginCommand cmd = LoginCommandMother.withEmailAndPassword(email, password);
        doReturn(Optional.of(member)).when(memberRepository).findByEmail(any());

        //when
        LoginResult result = sut.handle(cmd);

        //then
        assertThat(result).isNotNull();
        assertThat(result.memberId()).isEqualTo(member.getId());
        assertThat(result.email()).isEqualTo(member.getEmail());
        verify(memberRepository).findByEmail(email);

        verify(memberRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(memberRepository);

    }

    /**
     * 로그인 실패 - 유효하지 않은 email (언해피 패스)
     *
     * 설명: 회원 로그인시 email이 유효하지 않을 경우 테스트
     *
     * 테스트 목적:
     * - 유효하지 않은 email로 로그인 요청했을 때 메서드가 지정한 예외를 던지는지 검증합니다.
     *
     * 검증 포인트:
     * 1. 저장소에 저장되지 않은 이메일일 경우, 인증 실패 에러코드를 정책 위반 예외에 담아 던집니다.(로그인이 실패합니다)
     */
    @Test
    void 로그인_이메일_미존재시_정책위반예외를_던지고_로그인_실패한다() {
        //given
        String email = "memberId@test.com";
        String password = "password";
        LoginCommand cmd = LoginCommandMother.withEmailAndPassword(email, password);

        doReturn(Optional.empty()).when(memberRepository).findByEmail(any());

        //when & then
        assertThatThrownBy(() -> sut.handle(cmd))
                .isInstanceOf(PolicyViolationException.class)
                .extracting("errorCode").isEqualTo(MemberError.AUTHENTICATION_FAIL);

        verify(memberRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(memberRepository);

    }

    /**
     * 로그인 실패 - 패스워드 불일치 (언해피 패스)
     *
     * 설명: 사용자가 요청한 패스워드가 저장된 회원의 패스워드와 일치하는지 검증합니다.
     *
     * 테스트 목적:
     * - 회원 로그인 시 요청된 패스워드와 실제 패스워드 같지 않은경우 지정된 예외를 던집니다.
     *
     * 검증 포인트:
     * 1. 로그인 요청자의 패스워드가 실제 해당 id를 가진 회원의 패스워드가 일치하는지 검증합니다.
     * 2. 패스워드 불일치시 정책위반 예외가 발생합니다.
     * 3. 회원 정보 조회가 실제로 발생했는지 확인합니다
     * 4. 회원 정보 조회 후 레파지토리와의 상호작용이 없어야합니다.
     */
    @Test
    void 로그인_패스워드_불일치시_정책위반예외를_던지고_로그인_실패한다() {
        //given
        String email = "memberId@test.com";
        String password = "not correct";
        LoginCommand cmd = LoginCommandMother.withEmailAndPassword(email, password);

        Member member = MemberMother.withEmailAndPassword(email, "correct");
        doReturn(Optional.of(member)).when(memberRepository).findByEmail(any());

        //when & then
        assertThatThrownBy(()-> sut.handle(cmd)).isInstanceOf(PolicyViolationException.class)
                .extracting("errorCode")
                .isEqualTo(MemberError.AUTHENTICATION_FAIL);

        verify(memberRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(memberRepository);
    }

}