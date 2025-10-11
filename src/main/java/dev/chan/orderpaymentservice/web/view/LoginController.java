package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.application.auth.LoginUseCase;
import dev.chan.orderpaymentservice.application.auth.dto.LoginCommand;
import dev.chan.orderpaymentservice.application.auth.dto.LoginResult;
import dev.chan.orderpaymentservice.web.dto.LoginForm;
import dev.chan.orderpaymentservice.web.dto.LoginSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "loginForm";
    }

    /**
     * TODO 임시 로그인 컨트롤러 - 추후 spring security 도입 예정입니다
     * @param loginForm - 로그인 폼 객체
     * @param session -
     * @return
     */
    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session){
        LoginResult result = loginUseCase.handle(LoginCommand.of(loginForm.getEmail(), loginForm.getPassword()));
        session.setAttribute("LOGIN_MEMBER", LoginSession.of(result.memberId(),result.email(),result.name()));
        return "redirect:/";
    }



}
