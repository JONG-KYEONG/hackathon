package com.hackathon.server.tour.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
@Getter
public class TourApiResponseDto {
    private Response response;
    @Getter
    public static class Response {
        @JsonProperty("body")
        private Body body;
    }
    @Getter
    public static class Body {
        @JsonProperty("items")
        private Items items;
        private int totalCount;
    }
    @Getter
    public static class Items {
        @JsonProperty("item")
        private List<Item> item;
    }
    @Getter
    public static class Item {
        @JsonProperty("addr1")
        private String addr1;
        @JsonProperty("addr2")
        private String addr2;
        @JsonProperty("firstimage")
        private String firstimage;
        @JsonProperty("firstimage2")
        private String firstimage2;
        @JsonProperty("mapx")
        private String mapx;
        @JsonProperty("mapy")
        private String mapy;
        @JsonProperty("title")
        private String title;
        @JsonProperty("overview")
        private String overview;
    }
}
