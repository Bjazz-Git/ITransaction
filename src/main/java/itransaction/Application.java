package itransaction;

import itransaction.controller.TransactionController;
import itransaction.model.AccountRepository;
import itransaction.model.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(){
        return args -> {
            // Repositories
            CustomerRepository customerRepository = new CustomerRepository();
            AccountRepository accountRepository = new AccountRepository(customerRepository);

            TransactionController controller = new TransactionController(customerRepository, accountRepository);
        };
    }
}