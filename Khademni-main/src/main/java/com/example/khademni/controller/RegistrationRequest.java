package com.example.khademni.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "firstname is mandetory")
    private String firstname;
    private String lastname;
//    @Email(message = "Email is not formatted")
    private String email;
//    @Size(min = 8 , message = "password should be 8 characters long minimum")
    private String password;

}
