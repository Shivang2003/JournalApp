package net.springproject.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class CurrentWeather {

    private Request request;
    private Location location;
    private Current current;

    @Getter
    @Setter
    public static class AirQuality{
        private String co;
        private String no2;
        private String o3;
        private String so2;
        @JsonProperty("pm2_5")
        private String pm2dot5;
        private String pm10;
    }

    @Getter
    @Setter
    public static class Astro{
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
    }

    @Getter
    @Setter
    public static class Current{
        private int temperature;
        @JsonProperty("weather_code")
        private int weatherCode;
        @JsonProperty("weather_icons")
        private ArrayList<String> weatherIcons;
        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;
        private Astro astro;
        @JsonProperty("air_quality")
        private AirQuality airQuality;
        private int pressure;
        private int precip;
        private int humidity;
        private int visibility;
    }

    @Getter
    @Setter
    public static class Location{
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
    }

    @Getter
    @Setter
    public static class Request{
        private String type;
        private String query;
        private String language;
        private String unit;
    }


}
