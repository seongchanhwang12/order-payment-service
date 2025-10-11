package dev.chan.orderpaymentservice.common;

import dev.chan.orderpaymentservice.application.auth.dto.LoginCommand;

public class LoginCommandMother {

    private final static String EMAIL = "test@test.com" ;
    private final static String PASSWORD = "password";
    public static LoginCommand login() {
        return new LoginCommand(EMAIL,PASSWORD);
    }

    public static LoginCommand withEmailAndPassword(String email, String password) {
        return new LoginCommand(email,password);
    }
}
