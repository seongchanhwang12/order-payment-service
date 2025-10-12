package dev.chan.orderpaymentservice.application.member;

import dev.chan.orderpaymentservice.application.member.dto.MemberDetail;
import dev.chan.orderpaymentservice.common.NotFoundException;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetMemberUseCaseTest {

    @InjectMocks
    GetMemberUseCase sut;

    @Mock
    MemberRepository memberRepository;

    /**
     * 회원 조회 성공 (해피 패스)
     *
     * 설명: 회원 조회 성공 동작을 테스트
     *
     * 테스트 목적:
     * - 회원 조회 성공시 조회된 회원의 회원 정보가 올바른지 검증합니다.
     *
     * 검증 포인트:
     * 1. 회원 조회 성공시 조회 결과와 회원의 필드 데이터가 일치해야합니다.
     * 2. 회원 조회로직 실행시 memberRepository 에서 회원 정보를 1회만 읽어옵니다.
     */
    @Test
    void 회원조회시_DB에서_회원정보를_조회해서_반환한다() {
        //given
        long memberId = 1L;
        Member member = MemberMother.newMember();
        doReturn(Optional.of(member)).when(memberRepository).findById(memberId);

        //when
        MemberDetail result = sut.handle(memberId);

        //then
        verify(memberRepository).findById(memberId);
        assertThat(result.email()).isEqualTo(member.getEmail());
        assertThat(result.name()).isEqualTo(member.getName());
        assertThat(result.phone()).isEqualTo(member.getPhone());

    }
    /**
     * 회원 조회 실패 테스트 (언해피 패스)
     *
     * 설명: 회원 정보 조회 실패시 동작 테스트
     *
     * 테스트 목적:
     * - 회원정보 조회 실패시 예외 처리를 테스트합니다.
     *
     * 검증 포인트:
     * 1. 조회된 회원이 없을 경우 NotFoundException을 던집니다.
     * 2. NotFoundException의 에러코드는 필드가 MEMBER_NOT_FOUND 인지 검증합니다.
     *
     */
    @Test
    void 회원조회시_조회결과가없으면_NotFound예외를_던지고_회원조회에_실패한다() {
        //given
        long memberId = 1L;
        doReturn(Optional.empty()).when(memberRepository).findById(memberId);

        //when & then
        assertThatThrownBy(()-> sut.handle(memberId))
                .isInstanceOf(NotFoundException.class)
                .extracting("errorCode")
                .isEqualTo(MemberError.MEMBER_NOT_FOUND);

    }

}