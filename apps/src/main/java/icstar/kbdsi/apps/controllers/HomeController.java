package icstar.kbdsi.apps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "API For KBDSI Accounting System (ICStar Hackathon 2023)";
    }
}
