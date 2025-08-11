package com.github.navelogic.estudiovirtualapi.Controller.Studio;

import com.github.navelogic.estudiovirtualapi.Service.Studio.CreateStudioUseCase;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Studio.StudioRegistrationDTO;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Studio.StudioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/studios")
@RequiredArgsConstructor
public class CreateStudioController {

    private final CreateStudioUseCase createStudioUseCase;

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StudioResponseDTO> create(
            @Valid @RequestBody StudioRegistrationDTO studioRegistrationDTO,
            @AuthenticationPrincipal String userIdString){

        var ownerId = UUID.fromString(userIdString);
        var createdStudio = createStudioUseCase.createForPlayer(studioRegistrationDTO, ownerId);

        var location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/studios/my-studio")
                .build().toUri();

        return ResponseEntity.created(location).body(createdStudio);
    }
}
