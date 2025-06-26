package com.github.navelogic.estudiovirtualapi.Util.NameAI.Controller;

import com.github.navelogic.estudiovirtualapi.Util.NameAI.Model.CrewMemberNameAI;
import com.github.navelogic.estudiovirtualapi.Util.NameAI.Service.CrewMemberNameAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crew-member-name-ai")
@RequiredArgsConstructor
public class CrewMemberNameAIController {

    private final CrewMemberNameAIService crewMemberNameAIService;

    @PostMapping
    public ResponseEntity<CrewMemberNameAI> createCrewMemberNameAI(@RequestBody CrewMemberNameAI crewMemberNameAI) {
        CrewMemberNameAI createdCrewMemberNameAI = crewMemberNameAIService.createCrewMemberNameAI(crewMemberNameAI);
        return ResponseEntity.ok(createdCrewMemberNameAI);
    }

}
