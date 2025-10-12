package dev.chan.orderpaymentservice.application.member;

import dev.chan.orderpaymentservice.application.member.dto.MemberDetail;
import dev.chan.orderpaymentservice.common.NotFoundException;
import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberUseCase {

    private final MemberRepository memberRepository;

    public MemberDetail handle(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberError.MEMBER_NOT_FOUND, "Member not found. id = [" + memberId + "]"));

        return MemberDetail.of(member.getEmail(),member.getName(),member.getPhone());

    }
}
