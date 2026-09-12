package net.springproject.journalApp.controller;

import net.springproject.journalApp.entity.CurrentWeather;
import net.springproject.journalApp.entity.JournalEntry;
import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.repository.UserRepository;
import net.springproject.journalApp.service.JournalEntryService;
import net.springproject.journalApp.service.RedisService;
import net.springproject.journalApp.service.UserService;
import net.springproject.journalApp.service.WeatherService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RedisService redisService;

    @Autowired
    WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        try{
            return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userInDb = userService.findByUserName(userName);
            if(userInDb!=null){
                userInDb.setUserName(updatedUser.getUserName());
                userInDb.setPassword(updatedUser.getPassword());
                userService.saveNewUser(userInDb);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/weather-info/{city}")
    public ResponseEntity<?> getWeatherInfo(@PathVariable("city") String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        CurrentWeather currentWeather = weatherService.getCurrentWeather(city);
        if(currentWeather==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentWeather, HttpStatus.OK);
    }

}
