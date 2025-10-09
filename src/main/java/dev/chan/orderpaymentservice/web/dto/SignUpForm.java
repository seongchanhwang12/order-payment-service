package dev.chan.orderpaymentservice.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignUpForm {

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotBlank
    private String name;

    @NotBlank
    private String passwordConfirm;

    @AssertTrue
    private boolean agreeTerms;





}