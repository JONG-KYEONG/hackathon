package com.hackathon.server.course.dto;

import com.hackathon.server.course.service.CourseService;
import com.hackathon.server.tour.dto.TourApiResponseDto.Item;
import lombok.Builder;

@Builder
public record CourseDto(
        Double x,
        Double y,
        String name,
        String address,
        String overview,
        String imageUrl,
        double distance
        ) {
    public static CourseDto tourApiToCourseDto(Item item, double x, double y){
        return CourseDto.builder()
                .name(item.getTitle())
                .x(Double.parseDouble(item.getMapx()))
                .y(Double.parseDouble(item.getMapy()))
                .address(item.getAddr1() + item.getAddr2())
                .overview(item.getOverview())
                .imageUrl(item.getFirstimage())
                .distance(CourseService.calculateHaversineDistance(x, y, Double.parseDouble(item.getMapx()), Double.parseDouble(item.getMapy())))
                .build();
    }
}
