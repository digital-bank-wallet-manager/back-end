package com.prog4.digitalbank;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

@GetMapping("/id")
    public static String ping (){
    return "pong";
}



}
