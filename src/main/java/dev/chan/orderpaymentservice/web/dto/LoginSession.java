package dev.chan.orderpaymentservice.web.dto;

public record LoginSession(long memberId, String email, String name) {

    public static Object of(long memberId, String email, String name) {
        return new LoginSession(memberId, email, name);
    }
}
