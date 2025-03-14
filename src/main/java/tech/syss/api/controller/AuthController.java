package tech.syss.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import tech.syss.api.model.User;
import tech.syss.api.service.AuthService;
import tech.syss.api.service.UserService;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        if (userService.findByUsername(request.get("username")).orElse(null) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already in use.");
        }
        User user = userService.create(request.get("username"), request.get("password"), Set.of("USER"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> request) {
        if (userService.findByUsername(request.get("username")).orElse(null) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already in use.");
        }
        User user = userService.create(request.get("username"), request.get("password"), Set.of("ADMIN"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        User user = userService.findByUsername(request.get("username")).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        return ResponseEntity.ok(Map.of("token", authService.login(request.get("username"), request.get("password"))));
    }
}
