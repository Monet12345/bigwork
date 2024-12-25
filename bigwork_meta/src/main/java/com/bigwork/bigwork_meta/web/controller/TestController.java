package com.bigwork.bigwork_meta.web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  @GetMapping("/")
  public String test() {
    System.out.println("testsssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    return "hello world";
  }

}
