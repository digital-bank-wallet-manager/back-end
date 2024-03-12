package com.prog4.digitalbank;

import com.prog4.digitalbank.idGenretor.IdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {


    @GetMapping("/ping")
    public static String ping (){
        return "pong";
    }
    @GetMapping("/id")
    public static String Id(){
       return IdGenerator.generateAccountNumber();
    }


}
