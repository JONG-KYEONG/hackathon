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

@Transactional
@Service
public class CourseService {
    private final TourApi tourApi;

    CourseService(TourApi tourApi){
        this.tourApi = tourApi;
    }

    private static final int PAGE_SIZE = 5;
    private static final int RADIUS = 1500;

    public CourseResponse getCourse (CourseRequest courseRequest){
        double distance = calculateHaversineDistance(courseRequest.startX(), courseRequest.startY(), courseRequest.endX(), courseRequest.endY());
        int courseCount = getCourseCount(distance);

        if(courseCount == 0){
            return new CourseResponse(new ArrayList<>());
        }

        double[] oneThird = divideCoordinates(courseRequest.startX(), courseRequest.startY(), courseRequest.endX(), courseRequest.endY(), 1.0 / 5.0);
        double[] twoThird = divideCoordinates(courseRequest.startX(), courseRequest.startY(), courseRequest.endX(), courseRequest.endY(), 2.0 / 5.0);
        double[] threeThird = divideCoordinates(courseRequest.startX(), courseRequest.startY(), courseRequest.endX(), courseRequest.endY(), 3.0 / 5.0);
        double[] fourThird = divideCoordinates(courseRequest.startX(), courseRequest.startY(), courseRequest.endX(), courseRequest.endY(), 4.0 / 5.0);

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
        else{
            categorys.add("관광지");
        }
        if(courseRequest.contents().contains("숙박")){
            categorys.add("숙박");
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
        double dx = x2 - x1;
        double dy = y2 - y1;
        double a = Math.pow(Math.sin(dy / 2), 2)
                + Math.cos(y1) * Math.cos(y2) * Math.pow(Math.sin(dx / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6371 * c; // 지구 반경(km)
    }

}
