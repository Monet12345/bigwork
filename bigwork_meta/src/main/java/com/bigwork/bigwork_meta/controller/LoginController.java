package com.bigwork.bigwork_meta.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {
  @GetMapping("/login")
  @PreAuthorize("hasAnyAuthority('sys:add')")
  String login() {
    return "login";
  }
}


