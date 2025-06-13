package com.github.navelogic.estudiovirtualapi.Util.NameAI.Controller;

import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.StudioNameAI;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Service.StudioNameAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/studio-name-ai")
@RequiredArgsConstructor
public class StudioNameAIController {

    private final StudioNameAIService studioNameAIService;

    @PostMapping
    public ResponseEntity<StudioNameAI> createStudioNameAI(@RequestBody StudioNameAI studioNameAI) {
        StudioNameAI createdStudioNameAI = studioNameAIService.createStudioNameAI(studioNameAI);
        return ResponseEntity.ok(createdStudioNameAI);
    }

}
