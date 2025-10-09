package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.web.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUpForm";
    }
}
