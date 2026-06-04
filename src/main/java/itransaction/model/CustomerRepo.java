package itransaction.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepo extends MongoRepository<Customer, Integer> {
    Customer findByNameIgnoreCase(String name);
}
