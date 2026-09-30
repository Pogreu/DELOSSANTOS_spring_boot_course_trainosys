package com.trainosys.firstspring;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String magsabiNgKamusta() {
        return "Kamusta, Mundo! (Get)";
    }

    @PostMapping("/hello")
    public String kamustaPost(@RequestBody String name) {
        return "Kamusta, " + name + "! (Post)";
    }

    @PutMapping("/hello")
    public String kamustaPut(@RequestBody String name) {
        return "Nabagong data para kay: " + name + "! (Put - Update)";
    }

    @DeleteMapping("/hello")
    public String kamustaDelete() {
        return "Tinanggal na ang data! (Delete - Remove)";
    }
}
