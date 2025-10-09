package dev.chan.orderpaymentservice.domain.member;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.common.PolicyViolationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String name;
    private String phone;

    @Builder(access = AccessLevel.PROTECTED)
    private Member(String email, String password, String phone, String name){
        this.email = Ensure.nonBlank(email, "Member.email");
        this.password = Ensure.nonBlank(password, "Member.password");
        this.name = Ensure.nonBlank(name, "Member.name");
        this.phone = Ensure.nonBlank(phone, "Member.phone");
    }

    public static Member signUp(String email, String password, String passwordConfirm, String phone, String name) {
        if(!Objects.equals(password, passwordConfirm)) {
            throw new PolicyViolationException(MemberError.INVALID_PASSWORD
                    , "invalid password. password is [" + password +  "] but, passwordConfirm = [" + passwordConfirm + "]");
        }

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .build();
    }


}
