package com.hackathon.server.course.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CourseResponse(
        List<CourseDto> courseDtos
) {
}
