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
        return journalEntryService.findById(entryId).orElse(null);
    }

    @DeleteMapping("id/{entryId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId entryId){
         journalEntryService.deleteById(entryId);
         return true;
    }

    @PutMapping("id/{entryId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId entryId, @RequestBody JournalEntry updatedEntry ){
        JournalEntry old = journalEntryService.findById(entryId).orElse(null);
        if(old != null){
            old.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : old.getTitle());
            old.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
