package com.formlaez.application.api.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/ping")
public class PingAdminController {

    @GetMapping
    public String ping() {
        return "pong";
    }
}
