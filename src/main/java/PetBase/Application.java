package PetBase;

import PetBase.dao.Repository.OwnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner seedAdmin(OwnerRepository ownerRepo, PasswordEncoder encoder) {
        return args -> {
            if (ownerRepo.findByUsername("admin").isEmpty()) {
                PetBase.dao.model.Owner admin = new PetBase.dao.model.Owner();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin"));
                admin.setRoles(Set.of("ROLE_ADMIN"));
                admin.setName("Administrator");
                admin.setBirthDate(LocalDate.now().toString());
                ownerRepo.save(admin);
            }
        };
    }
}