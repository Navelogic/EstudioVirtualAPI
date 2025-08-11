package com.github.navelogic.estudiovirtualapi.Service.Studio.Util;

import com.github.navelogic.estudiovirtualapi.Model.Studio;
import com.github.navelogic.estudiovirtualapi.Util.DTO.Studio.StudioResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class MapToStudioResponseDTO {

    /**
     * Maps a Studio entity to a StudioResponseDTO.
     *
     * @param studio the Studio entity to map
     * @return a StudioResponseDTO containing the mapped data
     */
    public StudioResponseDTO execute(Studio studio){
        return StudioResponseDTO.builder()
                .name(studio.getName())
                .description(studio.getDescription())
                .isActive(studio.getIsActive())
                .isAiControlled(studio.getIsAiControlled())
                .build();
    }
}
