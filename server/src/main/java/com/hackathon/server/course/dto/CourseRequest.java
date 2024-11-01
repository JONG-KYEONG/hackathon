package com.hackathon.server.course.dto;

import java.util.List;

public record CourseRequest (
        Double startX,
        Double startY,
        Double endX,
        Double endY,
        String contents
){
}
