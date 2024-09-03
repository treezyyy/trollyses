package io.bootify.trollys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
public class TrollysApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TrollysApplication.class, args);
    }

}
