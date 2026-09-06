package net.springproject.journalApp.cache;

import jakarta.annotation.PostConstruct;
import net.springproject.journalApp.entity.FetchKeys;
import net.springproject.journalApp.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, String> API_KEYS_CACHE;

    @PostConstruct
    public void cacheApiKeys() {
        API_KEYS_CACHE = new HashMap<>();
        List<FetchKeys> apiKeys = apiKeyRepository.findAll();
        for(FetchKeys val: apiKeys){
            API_KEYS_CACHE.put(val.getKey(), val.getValue());
        }
    }

}
