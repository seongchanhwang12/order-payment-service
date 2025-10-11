package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.application.member.SignUpUseCase;
import dev.chan.orderpaymentservice.application.member.dto.SignUpCommand;
import dev.chan.orderpaymentservice.web.dto.SignUpForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final SignUpUseCase signUpUseCase;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUpForm";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("signUpForm") @Valid SignUpForm form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes)
    {
        if (!Objects.equals(form.getPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "member.password.mismatch", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "signUpForm";
        }

        signUpUseCase.handle(SignUpCommand.of(
                form.getEmail(),
                form.getPassword(),
                form.getPhone(),
                form.getName()));

        redirectAttributes.addFlashAttribute("signupSuccess", true);
        return "redirect:/login";
    }


}
