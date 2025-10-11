package dev.chan.orderpaymentservice.domain.member;

public class MemberMother {

    private final static String EMAIL ="default@test.email";
    private final static String PASSWORD ="password";
    private final static String PHONE ="01022223333";
    private final static String NAME ="name";

    public static Member withEmail(String email) {
        return Member.of(email, PASSWORD, NAME, PHONE);
    }

    public static Member withEmailAndPassword(String email, String password) {
        return Member.of(email, password, NAME, PHONE);
    }
}
