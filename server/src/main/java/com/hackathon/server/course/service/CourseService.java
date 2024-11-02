package com.hackathon.server.course.service;

import com.hackathon.server.course.dto.CourseDto;
import com.hackathon.server.course.dto.CourseRequest;
import com.hackathon.server.course.dto.CourseResponse;
import com.hackathon.server.tour.dto.TourApiResponseDto;
import com.hackathon.server.tour.service.TourApi;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    private final TourApi tourApi;

    CourseService(TourApi tourApi){
        this.tourApi = tourApi;
    }

    private static final int PAGE_SIZE = 5;
    private static final int RADIUS = 1500;

    public CourseResponse getCourse (CourseRequest courseRequest){
        double startX = courseRequest.startX();
        double startY = courseRequest.startY();

        if(courseRequest.startX() == null){
            startX = 129.1031734;
            startY = 35.13408024;
        }


        double distance = calculateHaversineDistance(startX, startY, courseRequest.endX(), courseRequest.endY());
        int courseCount = getCourseCount(distance);

        double[] oneThird = divideCoordinates(startX, startY, courseRequest.endX(), courseRequest.endY(), 1.0 / 5.0);
        double[] twoThird = divideCoordinates(startX, startY, courseRequest.endX(), courseRequest.endY(), 2.0 / 5.0);
        double[] threeThird = divideCoordinates(startX, startY, courseRequest.endX(), courseRequest.endY(), 3.0 / 5.0);
        double[] fourThird = divideCoordinates(startX, startY, courseRequest.endX(), courseRequest.endY(), 4.0 / 5.0);

        if(courseCount == 0){
            return CourseResponse.builder()
                    .centerX(twoThird[0])
                    .centerY(twoThird[1])
                    .courseDtos(new ArrayList<>())
                    .build();
        }

        List<CourseDto> courseDtos = new ArrayList<>();
        List<String> categorys = new ArrayList<>();

        categorys.add("관광지");
        if(courseRequest.contents().contains("레포츠")){
            categorys.add("레포츠");
        }
        else{
            categorys.add("관광지");
        }

        if(courseRequest.contents().contains("음식점")){
            categorys.add("음식점");
        }
        else{
            categorys.add("관광지");
        }

        if(courseRequest.contents().contains("문화시설")){
            categorys.add("문화시설");
        }
        else if(courseRequest.contents().contains("음식점")){
            categorys.add("음식점");
        }
        else{
            categorys.add("관광지");
        }

        if(courseRequest.contents().contains("숙박")){
            categorys.add("숙박");
        }
        else if(courseRequest.contents().contains("음식점")){
            categorys.add("음식점");
        }
        else{
            categorys.add("관광지");
        }

        int idx = 0;

        TourApiResponseDto tourApiResponseDto = tourApi.getSpot(oneThird[0], oneThird[1], RADIUS, categorys.get(idx), PAGE_SIZE);

        Random random = new Random();
        int randomValue = random.nextInt(tourApiResponseDto.getResponse().getBody().getItems().getItem().size());

        courseDtos.add(CourseDto.tourApiToCourseDto(tourApiResponseDto.getResponse().getBody().getItems().getItem().get(randomValue) , courseRequest.startX(), courseRequest.startY()));
        idx++;

        tourApiResponseDto = tourApi.getSpot(twoThird[0], twoThird[1], RADIUS, categorys.get(idx), PAGE_SIZE);

        random = new Random();
        randomValue = random.nextInt(tourApiResponseDto.getResponse().getBody().getItems().getItem().size());

        courseDtos.add(CourseDto.tourApiToCourseDto(tourApiResponseDto.getResponse().getBody().getItems().getItem().get(randomValue) , courseRequest.startX(), courseRequest.startY()));
        idx++;

        tourApiResponseDto = tourApi.getSpot(threeThird[0], threeThird[1], RADIUS, categorys.get(idx), PAGE_SIZE);

        random = new Random();
        randomValue = random.nextInt(tourApiResponseDto.getResponse().getBody().getItems().getItem().size());

        courseDtos.add(CourseDto.tourApiToCourseDto(tourApiResponseDto.getResponse().getBody().getItems().getItem().get(randomValue) , courseRequest.startX(), courseRequest.startY()));
        idx++;

        tourApiResponseDto = tourApi.getSpot(fourThird[0], fourThird[1], RADIUS, categorys.get(idx), PAGE_SIZE);

        random = new Random();
        randomValue = random.nextInt(tourApiResponseDto.getResponse().getBody().getItems().getItem().size());

        courseDtos.add(CourseDto.tourApiToCourseDto(tourApiResponseDto.getResponse().getBody().getItems().getItem().get(randomValue) , courseRequest.startX(), courseRequest.startY()));
        idx++;

        tourApiResponseDto = tourApi.getSpot(fourThird[0], fourThird[1], RADIUS, categorys.get(idx), PAGE_SIZE);

        random = new Random();
        randomValue = random.nextInt(tourApiResponseDto.getResponse().getBody().getItems().getItem().size());

        courseDtos.add(CourseDto.tourApiToCourseDto(tourApiResponseDto.getResponse().getBody().getItems().getItem().get(randomValue) , courseRequest.startX(), courseRequest.startY()));


        courseDtos.sort(Comparator.comparingDouble(CourseDto::distance));


        return CourseResponse.builder()
                .centerX(twoThird[0])
                .centerY(twoThird[1])
                .courseDtos(courseDtos)
                .build();
    }

    private int getCourseCount(double distance){
        if(distance < 5){
            return 0;
        }
        else {
            return 4;
        }
//        else {
//            return 5;
//        }
    }

    public double[] divideCoordinates(double x1, double y1, double x2, double y2, double ratio) {
        double x = x1 + (x2 - x1) * ratio;
        double y = y1 + (y2 - y1) * ratio;
        return new double[]{x, y};
    }

    public static double calculateHaversineDistance(double x1, double y1, double x2, double y2) {
        double R = 6371; // 지구 반지름 (km)
        double dY = deg2rad(y2 - y1);
        double dX = deg2rad(x2 - x1);

        double a = Math.sin(dY / 2) * Math.sin(dY / 2) +
                Math.cos(deg2rad(y1)) * Math.cos(deg2rad(y2)) *
                        Math.sin(dX / 2) * Math.sin(dX / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // 거리 (km)

        return distance;
    }

    public static double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

}
