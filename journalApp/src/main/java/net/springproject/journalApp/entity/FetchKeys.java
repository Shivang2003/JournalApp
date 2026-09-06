package net.springproject.journalApp.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apiKeys")
@Data
@NoArgsConstructor
public class FetchKeys {
    private String key;
    private String value;
}
