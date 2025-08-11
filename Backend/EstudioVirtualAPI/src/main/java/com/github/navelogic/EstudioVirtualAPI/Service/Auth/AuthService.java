package com.github.navelogic.estudiovirtualapi.Service.Auth;

import com.github.navelogic.estudiovirtualapi.Repository.PlayerRepository;
import com.github.navelogic.estudiovirtualapi.Util.DTO.AuthDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.AuthResponseDTO;
import com.github.navelogic.estudiovirtualapi.Util.Provider.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Duration TOKEN_EXPIRATION = Duration.ofMinutes(120);

    @Value("${user.issuer}")
    private String jwtIssuer;

    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        var user = playerRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou senha inválidos.");
        }

        if (!user.isActive()) {
            throw new BadCredentialsException("A conta do usuário está desativada.");
        }

        String token = jwtProvider.generateToken(user.getId().toString(), TOKEN_EXPIRATION, user.getUserRole());

        return AuthResponseDTO.builder()
                .authToken(token)
                .role(user.getUserRole().name())
                .build();
    }
}
