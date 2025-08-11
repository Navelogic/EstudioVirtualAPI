package com.github.navelogic.estudiovirtualapi.Controller;

import com.github.navelogic.estudiovirtualapi.Service.Auth.AuthService;
import com.github.navelogic.estudiovirtualapi.Util.DTO.AuthDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.AuthResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthDTO authDTO) {
        AuthResponseDTO response = authService.authenticate(authDTO);
        return ResponseEntity.ok(response);
    }

}
