package net.springproject.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.springproject.journalApp.cache.AppCache;
import net.springproject.journalApp.entity.CurrentWeather;
import net.springproject.journalApp.entity.JournalEntry;
import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.repository.JournalEntryRepository;
import net.springproject.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${api.key.weather}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            log.info("New user saved: {}", user.getUserName());
        }
        catch (Exception e){
            log.error("Error saving new user: {}", e.getMessage());
            throw new RuntimeException("Error saving new user: " + user.getUserName(), e);
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public CurrentWeather getCurrentWeather(String cityName) {
        try {
            String getCurrentWeatherUrl = "https://api.weatherstack.com/current?access_key=" + appCache.API_KEYS_CACHE.get("weather_key") + "&query=" + cityName;
            ResponseEntity<CurrentWeather> response = restTemplate.exchange(getCurrentWeatherUrl, HttpMethod.GET, null, CurrentWeather.class);
            CurrentWeather currentWeather = response.getBody();
            return currentWeather;
        } catch (Exception e) {
            log.error("Error fetching current weather for city {}: {}", cityName, e.getMessage());
        }
        return null;
    }

}
