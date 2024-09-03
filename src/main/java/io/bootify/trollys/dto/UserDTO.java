package io.bootify.trollys.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty(message = "Email should not be empty")

    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
