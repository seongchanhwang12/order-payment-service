package dev.chan.orderpaymentservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        // TODO 등록된 prduct 를 조회하는 로직을 추가 예정입니다.
        return "home";
    }
}
