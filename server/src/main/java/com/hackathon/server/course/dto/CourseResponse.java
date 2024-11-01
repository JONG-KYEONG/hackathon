package com.hackathon.server.course.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CourseResponse(
        Double centerX,
        Double centerY,
        List<CourseDto> courseDtos
) {
}
