package net.springproject.journalApp.controller;

import net.springproject.journalApp.entity.JournalEntry;
import net.springproject.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JoournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry newEntry){
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry);
        return true;
    }

    @GetMapping("id/{entryId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId entryId){
        return null;
    }

    @DeleteMapping("id/{entryId}")
    public JournalEntry deleteJournalEntryById(@PathVariable ObjectId entryId){
        return null;
    }

    @PutMapping("id/{entryId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId entryId, @RequestBody JournalEntry updatedEntry ){
        return null;
    }
}
