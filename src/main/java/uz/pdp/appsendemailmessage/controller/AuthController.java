package uz.pdp.appsendemailmessage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsendemailmessage.entity.ApiResponse;
import uz.pdp.appsendemailmessage.model.LoginReceiveModel;
import uz.pdp.appsendemailmessage.model.RegisterReceiveModel;
import uz.pdp.appsendemailmessage.repository.RoleRepository;
import uz.pdp.appsendemailmessage.service.AuthService;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    RoleRepository roleRepository;


    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterReceiveModel registerReceiveModel) {

        ApiResponse apiResponse = authService.addUser(registerReceiveModel);
        return ResponseEntity.status(apiResponse.isState() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isState() ? 200 : 401).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginReceiveModel loginReceiveModel) {
        ApiResponse apiResponse = authService.login(loginReceiveModel);
        return ResponseEntity.status(apiResponse.isState() ? 200 : 401).body(apiResponse);
    }
}
