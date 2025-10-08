package dev.chan.orderpaymentservice.web;

import dev.chan.orderpaymentservice.application.ProductQueryService;
import dev.chan.orderpaymentservice.application.dto.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductQueryService productQueryService;

    @GetMapping("/")
    public String home(Model model){
        List<ProductView> views = productQueryService.listAll().stream().map(ProductView::from).toList();
        model.addAttribute("products", views);
        return "home";
    }
}
