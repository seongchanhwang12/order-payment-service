package dev.chan.orderpaymentservice.web.api;

import dev.chan.orderpaymentservice.application.member.GetMemberUseCase;
import dev.chan.orderpaymentservice.application.member.dto.MemberDetail;
import dev.chan.orderpaymentservice.common.ApiResponse;
import dev.chan.orderpaymentservice.common.UnauthorizedException;
import dev.chan.orderpaymentservice.domain.member.MemberError;
import dev.chan.orderpaymentservice.web.dto.LoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final GetMemberUseCase getMemberUseCase;

    /**
     * 회원 조회 API
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberDetail>> getMember(
            @SessionAttribute(value = "LOGIN_MEMBER", required = false) LoginSession loginSession)
    {
        if (loginSession == null) {
            throw new UnauthorizedException(MemberError.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        MemberDetail memberDetail = getMemberUseCase.handle(loginSession.memberId());
        return ResponseEntity.ok()
                .body(ApiResponse.success(memberDetail));
    }

}
