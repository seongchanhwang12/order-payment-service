package dev.chan.orderpaymentservice.web.view;

import dev.chan.orderpaymentservice.application.auth.dto.LoginResult;
import dev.chan.orderpaymentservice.application.product.ProductQueryService;
import dev.chan.orderpaymentservice.application.product.dto.ProductView;
import dev.chan.orderpaymentservice.web.dto.LoginForm;
import dev.chan.orderpaymentservice.web.dto.LoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductQueryService productQueryService;

    @GetMapping("/")
    public String home(@SessionAttribute(value="LOGIN_MEMBER", required = false) LoginSession loginSession, Model model){
        List<ProductView> views = productQueryService.listAll().stream()
                .map(ProductView::from)
                .toList();

        model.addAttribute("products", views);
        if(Objects.nonNull(loginSession)) {
            model.addAttribute("LOGIN_MEMBER", loginSession.email());

        }

        return "home";
    }
}
