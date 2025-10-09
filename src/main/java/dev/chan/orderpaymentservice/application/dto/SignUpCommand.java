package dev.chan.orderpaymentservice.application.dto;


public record SignUpCommand(
        String email,
        String password,
        String phone,
        String name)
{
    public static SignUpCommand of(
            String email,
            String password,
            String phone,
            String name) {
        return new SignUpCommand(email, password, phone, name);
    }
}
