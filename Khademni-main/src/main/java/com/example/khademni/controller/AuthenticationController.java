package com.example.khademni.controller;

import com.example.khademni.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        //**
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        //
        return ResponseEntity.ok(service.authenticate(request));

    }
//    @GetMapping("/activate")
//    public String showActivateAccountPage() {
//        return "activateaccount";
//    }
@GetMapping("/activate-account")
public void confirm(
        @RequestParam String token
) throws MessagingException {
    service.activateAccount(token);
}

}

