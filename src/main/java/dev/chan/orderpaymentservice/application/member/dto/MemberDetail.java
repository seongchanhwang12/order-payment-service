package dev.chan.orderpaymentservice.application.member.dto;

public record MemberDetail(String email, String name, String phone) {
    public static MemberDetail of(String email, String name, String phone) {
        return new MemberDetail(email, name, phone);
    }
}
