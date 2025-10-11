package dev.chan.orderpaymentservice.application.auth.dto;

import dev.chan.orderpaymentservice.common.Ensure;

public record LoginCommand(String email, String password){

    public LoginCommand(String email, String password) {
        this.email = Ensure.nonBlank(email, "email");
        this.password = Ensure.nonBlank(password, "password");

    }
    public static LoginCommand of(String email, String password) {
        return new LoginCommand(email, password);
    }
}
