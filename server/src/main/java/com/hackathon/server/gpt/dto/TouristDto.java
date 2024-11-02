package com.hackathon.server.gpt.dto;

import com.hackathon.server.tour.model.TouristAttraction;
import java.util.List;
import lombok.Builder;

@Builder
public record TouristDto(
        List<TouristAttraction> touristAttractions
) {
}
