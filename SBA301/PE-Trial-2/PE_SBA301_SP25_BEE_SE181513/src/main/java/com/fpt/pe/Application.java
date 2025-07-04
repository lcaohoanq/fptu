package com.fpt.pe;

import com.fpt.pe.models.AccountMember;
import com.fpt.pe.models.AccountMember.Role;
import com.fpt.pe.models.Car;
import com.fpt.pe.models.Country;
import com.fpt.pe.repositories.AccountRepository;
import com.fpt.pe.repositories.CarRepository;
import com.fpt.pe.repositories.CountryRepository;
import io.github.lcaohoanq.annotations.BrowserLauncher;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@BrowserLauncher(
    value = "http://localhost:8080/swagger-ui.html"
)
public class Application implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CarRepository carRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final String samplePassword = "1";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<AccountMember> AccountMemberList;
        if(accountRepository.findAll().isEmpty()){
            var acc1 = AccountMember.builder()
                .role(Role.ADMIN)
                .email("ad@gmail.com")
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc2 = AccountMember.builder()
                .role(Role.STAFF)
                .email("st@gmail.com")
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc3 = AccountMember.builder()
                .role(Role.MEMBER)
                .email("mem@gmail.com")
                .password(passwordEncoder.encode(samplePassword))
                .build();

            AccountMemberList = List.of(acc1, acc2, acc3);
            accountRepository.saveAll(AccountMemberList);
        }

        List<Country> countryList;
        if(countryRepository.findAll().isEmpty()){
            var cat1 = Country.builder()
                .name("Viet Nam")
                .build();

            var cat2 = Country.builder()
                .name("England")
                .build();

            var cat3 = Country.builder()
                .name("USA")
                .build();

            countryList = List.of(cat1, cat2, cat3);
            countryRepository.saveAll(countryList);
        } else{
            countryList = countryRepository.findAll();
        }

        List<Car> carList;
        if(carRepository.findAll().isEmpty()){
            var box1 = Car.builder()
                .name("Car 1")
                .price(29.99)
                .unitsInStock(10)
                .createdAt(LocalDateTime.now())
                .country(countryList.get(0))
                .build();

            var box2 = Car.builder()
                .name("Car 2")
                .price(199.99)
                .unitsInStock(10)
                .createdAt(LocalDateTime.now())
                .country(countryList.get(1))
                .build();

            var box3 = Car.builder()
                .name("Car 3")
                .price(499.99)
                .unitsInStock(10)
                .createdAt(LocalDateTime.now())
                .country(countryList.get(2))
                .build();

            carList = List.of(box1, box2, box3);
            carRepository.saveAll(carList);
        }


    }
}
