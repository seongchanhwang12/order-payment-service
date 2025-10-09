package dev.chan.orderpaymentservice.common;

import dev.chan.orderpaymentservice.application.dto.SignUpCommand;

public class SignUpCommandMother {
    public static SignUpCommand of(
            String email,
            String password,
            String passwordConfirm,
            String phone,
            String name
    ){
        return new SignUpCommand(email, password, passwordConfirm, phone, name);
    };
}
