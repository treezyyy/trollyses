package io.bootify.trollys.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Автоматизация транспортной системы",
                description = "Автоматизационная система", version = "1.0.0",
                contact = @Contact(
                        name = "Баландин Кирилл",
                        email = "balandinkirill2005@nail.ru"
                )
        )
)
public class OpenApiConfig {
}
