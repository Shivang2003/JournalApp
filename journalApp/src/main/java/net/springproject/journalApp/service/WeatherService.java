package net.springproject.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.springproject.journalApp.cache.AppCache;
import net.springproject.journalApp.entity.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Autowired
    RedisService redisService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    public CurrentWeather getCurrentWeather(String cityName) {
        try {
            CurrentWeather currentWeather  = redisService.get("weather_of_" + cityName, CurrentWeather.class);
            if(currentWeather!=null){
                return currentWeather;
            }
            else{
                String getCurrentWeatherUrl = "https://api.weatherstack.com/current?access_key=" + appCache.API_KEYS_CACHE.get("weather_key") + "&query=" + cityName;
                ResponseEntity<CurrentWeather> response = restTemplate.exchange(getCurrentWeatherUrl, HttpMethod.GET, null, CurrentWeather.class);
                CurrentWeather weatherBody = response.getBody();
                if (weatherBody!=null){
                    redisService.set("weather_of_" + cityName, weatherBody, 300l);
                }
                return weatherBody;
            }
        } catch (Exception e) {
            log.error("Error fetching current weather for city {}: {}", cityName, e.getMessage());
        }
        return null;
    }


}
