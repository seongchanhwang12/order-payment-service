package dev.chan.orderpaymentservice.web.dto;

import dev.chan.orderpaymentservice.common.UnauthorizedException;
import dev.chan.orderpaymentservice.domain.member.MemberError;

public record LoginSession(long memberId, String email, String name) {
    public LoginSession {
        if(memberId <= 0) {
            throw new UnauthorizedException(MemberError.INVALID_SESSION, "memberId must be positive.");
        }
    }
    public static LoginSession of(long memberId, String email, String name) {
        return new LoginSession(memberId, email, name);
    }
}
