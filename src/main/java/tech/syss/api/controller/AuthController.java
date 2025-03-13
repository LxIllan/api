package tech.syss.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import tech.syss.api.model.User;
import tech.syss.api.service.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        if (authService.findByUsername(request.get("username")).orElse(null) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already in use.");
        }
        User user = authService.register(request.get("username"), request.get("password"), "user");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        User user = authService.findByUsername(request.get("username")).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        return ResponseEntity.ok(
            Map.of("token", authService.login(request.get("username"), request.get("password"))));
    }
}
