package dev.chan.orderpaymentservice.application.auth.dto;

import dev.chan.orderpaymentservice.domain.member.Member;

public record LoginResult(long memberId, String email, String name) {
    public static LoginResult of(Member loginMember) {
        return new LoginResult(loginMember.getId(), loginMember.getEmail(), loginMember.getName());
    }
}
