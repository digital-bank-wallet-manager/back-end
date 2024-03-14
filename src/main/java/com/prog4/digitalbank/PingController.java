package com.prog4.digitalbank;

import com.prog4.digitalbank.idGenretor.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

@GetMapping("/id")
    public static String ping (){
    return "pong";
}



}
