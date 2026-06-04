package itransaction;

import itransaction.controller.TransactionController;
import itransaction.model.data.AccountService;
import itransaction.model.data.CustomerService;
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
            CustomerService customerService = new CustomerService();
            AccountService accountService = new AccountService();
            TransactionController controller = new TransactionController(customerService, accountService);
        };
    }
}