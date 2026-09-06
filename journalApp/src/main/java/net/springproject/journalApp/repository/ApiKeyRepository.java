package net.springproject.journalApp.repository;

import net.springproject.journalApp.entity.FetchKeys;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<FetchKeys, ObjectId> {
}
