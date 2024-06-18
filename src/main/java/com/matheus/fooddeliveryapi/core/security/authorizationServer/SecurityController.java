package com.matheus.fooddeliveryapi.core.security.authorizationServer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "page/login";
    }

    @GetMapping("/oauth/confirm_access")
    public String approval() {
        return "page/approval";
    }
}
