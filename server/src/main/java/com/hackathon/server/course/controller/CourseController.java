package com.hackathon.server.course.controller;

import com.hackathon.server.course.dto.CourseRequest;
import com.hackathon.server.course.dto.CourseResponse;
import com.hackathon.server.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("")
    @Operation(summary = "경로 추천 받기", description = "출발 x,y 좌표 / 도착 x,y 좌표 / 사용자 특성 넣어주세요. ")
    public ResponseEntity<CourseResponse> getCourse(@RequestParam(required = false) Double startX,
                                                    @RequestParam(required = false) Double startY,
                                                    @RequestParam Double endX,
                                                    @RequestParam Double endY,
                                                    @RequestParam String contents) {
        if(startX == null){
            startX = 129.10317348438963;
            startY = 35.134080249513474;
        }

        return new ResponseEntity<>(courseService.getCourse(CourseRequest.builder()
                        .startX(startX)
                        .startY(startY)
                        .endX(endX)
                        .endY(endY)
                        .contents(contents)
                .build()), HttpStatus.OK);
    }
}
