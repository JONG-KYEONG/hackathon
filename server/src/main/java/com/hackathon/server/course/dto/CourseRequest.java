package com.hackathon.server.course.dto;

import java.util.List;

public record CourseRequest (
        Integer startX,
        Integer startY,
        Integer endX,
        Integer endY,
        String contents
){
}
