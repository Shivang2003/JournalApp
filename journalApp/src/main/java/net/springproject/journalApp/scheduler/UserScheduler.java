package net.springproject.journalApp.scheduler;

import net.springproject.journalApp.entity.JournalEntry;
import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.repository.UserRepositoryImpl;
import net.springproject.journalApp.service.EmailService;
import net.springproject.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
                List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
                String entry = String.join(" ", filteredEntries);
                sentimentAnalysisService.getSentiment(entry);
                emailService.sendEmail("l@gmail.com", "SentimentAnalysis", "Body");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
