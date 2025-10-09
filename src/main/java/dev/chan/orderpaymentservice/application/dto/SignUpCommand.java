package dev.chan.orderpaymentservice.application.dto;


public record SignUpCommand(
        String email,
        String password,
        String passwordConfirm,
        String phone,
        String name)
{}
