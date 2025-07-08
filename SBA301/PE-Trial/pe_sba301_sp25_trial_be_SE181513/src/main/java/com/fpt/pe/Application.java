package com.fpt.pe;

import com.fpt.pe.models.BlindBox;
import com.fpt.pe.models.Category;
import com.fpt.pe.models.SystemAccount;
import com.fpt.pe.models.SystemAccount.Role;
import com.fpt.pe.repositories.AccountRepository;
import com.fpt.pe.repositories.CategoryRepository;
import com.fpt.pe.repositories.ProductRepository;
import io.github.lcaohoanq.annotations.BrowserLauncher;
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
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final String samplePassword = "1";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<SystemAccount> systemAccountList;
        if(accountRepository.findAll().isEmpty()){
            var acc1 = SystemAccount.builder()
                .username("admin")
                .role(Role.ADMINISTRATOR)
                .email("admin@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc2 = SystemAccount.builder()
                .username("moderator")
                .role(Role.MODERATOR)
                .email("moderator@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc3 = SystemAccount.builder()
                .username("developer")
                .role(Role.DEVELOPER)
                .email("dev@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc4 = SystemAccount.builder()
                .username("member")
                .role(Role.MEMBER)
                .email("member@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode(samplePassword))
                .build();

            var acc5 = SystemAccount.builder()
                .username("member2")
                .role(Role.MEMBER)
                .email("member2@gmail.com")
                .isActive(false)
                .password(passwordEncoder.encode(samplePassword))
                .build();

            systemAccountList = List.of(acc1, acc2, acc3, acc4, acc5);
            accountRepository.saveAll(systemAccountList);
        }

        List<Category> categoryList;
        if(categoryRepository.findAll().isEmpty()){
            var cat1 = Category.builder()
                .name("Toys")
                .description("Toys for children")
                .rarityLevel("Common to Rare")
                .priceRange("$10 - $50")
                .build();

            var cat2 = Category.builder()
                .name("Electronics")
                .description("Electronic devices and gadgets")
                .rarityLevel("Rare to Epic")
                .priceRange("$100 - $500")
                .build();

            var cat3 = Category.builder()
                .name("Collectibles")
                .description("Rare and collectible items")
                .rarityLevel("Epic to Ultra Rare")
                .priceRange("$500 - $1000")
                .build();

            categoryList = List.of(cat1, cat2, cat3);
            categoryRepository.saveAll(categoryList);
        } else{
            categoryList = categoryRepository.findAll();
        }

        List<BlindBox> blindBoxList;
        if(productRepository.findAll().isEmpty()){
            var box1 = BlindBox.builder()
                .name("Mystery Box 1")
                .rarity("Contains a random toy")
                .price(29.99)
                .category(categoryList.get(0))
                .build();

            var box2 = BlindBox.builder()
                .name("Mystery Box 2")
                .rarity("Contains a random electronic gadget")
                .price(199.99)
                .category(categoryList.get(1))
                .build();

            var box3 = BlindBox.builder()
                .name("Mystery Box 3")
                .rarity("Contains a rare collectible item")
                .price(499.99)
                .category(categoryList.get(2))
                .build();

            blindBoxList = List.of(box1, box2, box3);
            productRepository.saveAll(blindBoxList);
        }


    }
}
