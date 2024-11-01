package com.hackathon.server.tour.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.server.exception.BadRequestException;
import com.hackathon.server.tour.dto.Enum.ContentType;
import com.hackathon.server.tour.dto.TourApiResponseDto;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TourApi {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    TourApi(RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    private static final String TOUR_API_BASE_URL = "http://apis.data.go.kr/B551011/KorService1";

    @Value("${tour_api.secret.KorService1}")
    private String korService1_secret;

    private TourApiResponseDto getSpot(double x, double y, int radius, int contentTypeId, int pageSize) throws IOException { // 좌표 값 주변 리스트 가져오는 메소드
        try{
            String ENDPOINT = "/locationBasedList1";
            String url = TOUR_API_BASE_URL + ENDPOINT;

            int randomPageMax = getTotalCount(x,y,radius,contentTypeId) / pageSize;

            Random random = new Random();
            int randomPage = 0;
            if(randomPageMax > 1){
                randomPage = random.nextInt(randomPageMax-1);
            }

            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("serviceKey", URLEncoder.encode(korService1_secret, StandardCharsets.UTF_8))
                    .queryParam("numOfRows" , pageSize)
                    .queryParam("pageNo",randomPage)
                    .queryParam("MobileOS", URLEncoder.encode("ETC", StandardCharsets.UTF_8))
                    .queryParam("MobileApp", URLEncoder.encode("yaguhang", StandardCharsets.UTF_8))
                    .queryParam("mapX", URLEncoder.encode(String.valueOf(x), StandardCharsets.UTF_8))
                    .queryParam("mapY", URLEncoder.encode(String.valueOf(y), StandardCharsets.UTF_8))
                    .queryParam("radius", URLEncoder.encode(String.valueOf(radius), StandardCharsets.UTF_8))
                    .queryParam("contentTypeId", URLEncoder.encode(String.valueOf(contentTypeId), StandardCharsets.UTF_8))
                    .queryParam("_type", URLEncoder.encode("json", StandardCharsets.UTF_8))
                    .build(true)
                    .toUri();

            TourApiResponseDto tourApiResponseDto = restTemplate.getForObject(uri, TourApiResponseDto.class);

            return tourApiResponseDto;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    public Integer getTotalCount (double x, double y, int radius, int contentTypeId) throws IOException{ // total 개수 가져오는 메소드
        String ENDPOINT = "/locationBasedList1";
        String url = TOUR_API_BASE_URL + ENDPOINT;

        URI getPageuri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", URLEncoder.encode(korService1_secret, StandardCharsets.UTF_8))
                .queryParam("MobileOS", URLEncoder.encode("ETC", StandardCharsets.UTF_8))
                .queryParam("MobileApp", URLEncoder.encode("yaguhang", StandardCharsets.UTF_8))
                .queryParam("mapX", URLEncoder.encode(String.valueOf(x), StandardCharsets.UTF_8))
                .queryParam("mapY", URLEncoder.encode(String.valueOf(y), StandardCharsets.UTF_8))
                .queryParam("radius", URLEncoder.encode(String.valueOf(radius), StandardCharsets.UTF_8))
                .queryParam("contentTypeId", URLEncoder.encode(String.valueOf(contentTypeId), StandardCharsets.UTF_8))
                .queryParam("_type", URLEncoder.encode("json", StandardCharsets.UTF_8))
                .build(true)
                .toUri();
        //
        TourApiResponseDto tempTARD = restTemplate.getForObject(getPageuri, TourApiResponseDto.class);
        return tempTARD.getResponse().getBody().getTotalCount();
    }


    public URI getIntroUri(Long contentId, String contentTypeId){ // 카데고리에 맞는 api uri 생성 메소드
        String ENDPOINT = "/detailIntro1";
        String url = TOUR_API_BASE_URL + ENDPOINT;

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", URLEncoder.encode(korService1_secret, StandardCharsets.UTF_8))
                .queryParam("MobileOS", URLEncoder.encode("ETC", StandardCharsets.UTF_8))
                .queryParam("MobileApp", URLEncoder.encode("yaguhang", StandardCharsets.UTF_8))
                .queryParam("contentId", URLEncoder.encode(String.valueOf(contentId), StandardCharsets.UTF_8))
                .queryParam("_type", URLEncoder.encode("json", StandardCharsets.UTF_8))
                .queryParam("contentTypeId", URLEncoder.encode(contentTypeId, StandardCharsets.UTF_8))
                .build(true)
                .toUri();
        return uri;
    }

    public TourApiResponseDto getSpot(double x, double y, int radius, String category, int pageSize){
        try {
            return getSpot(x, y, radius, getContentTypeId(category), pageSize);
        } catch (IOException e) {
            return null;
        }
    }

    public int getContentTypeId(String category){ // 카테고리 값으로 contentTypeId 가져오기
        if(category.equals("관광지"))
            return ContentType.관광지.getValue();
        if(category.equals("문화시설"))
            return ContentType.문화시설.getValue();
        if(category.equals("레포츠"))
            return ContentType.레포츠.getValue();
        if(category.equals("숙박"))
            return ContentType.숙박.getValue();
        if(category.equals("음식점"))
            return ContentType.음식점.getValue();

        throw new BadRequestException("category 값을 확인해주세요");
    }
}