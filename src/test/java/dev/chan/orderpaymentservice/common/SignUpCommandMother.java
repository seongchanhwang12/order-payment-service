package dev.chan.orderpaymentservice.common;

import dev.chan.orderpaymentservice.application.dto.SignUpCommand;

public class SignUpCommandMother {
    private final static String EMAIL ="default@test.email";
    private final static String PASSWORD ="password";
    private final static String PHONE ="01011112222";
    private final static String NAME = "name";


    public static SignUpCommand of(
            String email,
            String password,
            String phone,
            String name
    ){
        return new SignUpCommand(email, password, phone, name);
    };

    public static SignUpCommand withEmail(String email) {
        return new SignUpCommand(email, PASSWORD, PHONE, NAME);
    }
    public static SignUpCommand newCommand() {
        return new SignUpCommand(EMAIL, PASSWORD, PHONE, NAME);
    }
}
