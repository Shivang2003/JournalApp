package net.springproject.journalApp.scheduler;

import net.springproject.journalApp.entity.JournalEntry;
import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.enums.Sentiment;
import net.springproject.journalApp.repository.UserRepositoryImpl;
import net.springproject.journalApp.service.EmailService;
import net.springproject.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "*/5 * * * * *")
    public void sendMialToUsers(){
        try{
            List<User> users = userRepositoryImpl.getUserForSA();
            for (User user : users) {
                List<JournalEntry> journalEntries = user.getJournalEntries();
                List<Sentiment> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
                if(filteredEntries.isEmpty()){
                    continue;
                }
                Map<String, Integer> sentimentCount = filteredEntries.stream().collect(Collectors.toMap(Sentiment::name, sentiment -> 1, Integer::sum));
                int max = 0;
                String maxSentiment = "";
                for(Map.Entry<String, Integer> entry : sentimentCount.entrySet()){
                    if (entry.getValue() > max){
                        max = entry.getValue();
                        maxSentiment = entry.getKey();
                    }
                }
                emailService.sendEmail("l@gmail.com", "SentimentAnalysis", "You were feeling " + maxSentiment + " in the last 7 days");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
