package com.fpt.mss.msbrand_se181513;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private String defaultPassword = "@2";

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {

        if(accountRepository.count() == 0){
            var account1 = Account.builder()
                .username("adminsys")
                .email("admin@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.ADMIN)
                .isActive(true)
                .build();

            var account2 = Account.builder()
                .username("johndoe")
                .email("john@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.MEMBER)
                .isActive(true)
                .build();

            var account3 = Account.builder()
                .username("modmichel")
                .email("michel@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.MODERATOR)
                .isActive(true)
                .build();

            var account4 = Account.builder()
                .username("vanvan")
                .email("vanavan@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.MEMBER)
                .isActive(true)
                .build();

            var account5 = Account.builder()
                .username("devops")
                .email("dev@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.DEVELOPER)
                .isActive(true)
                .build();

            accountRepository.saveAll(List.of(account1, account2, account3, account4, account5));

        }

        if(categoryRepository.count() == 0) {

            Category category1 = new Category();
            category1.setName("Electronics");
            category1.setDescription("Mystical creatures, wizards, and legendary beings.");
            category1.setRarityLevel("Common to Ultra Rare");
            category1.setPriceRange("$10 - $60");
            categoryRepository.save(category1);

            Category category2 = new Category();
            category2.setName("Gaming");
            category2.setDescription("Blind boxes featuring characters from popular video games.");
            category2.setRarityLevel("Common to Epic");
            category2.setPriceRange("$25 - $70");
            categoryRepository.save(category2);

            Category category3 = new Category();
            category3.setName("Sci-Fi");
            category3.setDescription("Space, futuristic themes, and robotic collectibles.");
            category3.setRarityLevel("Rare to Legendary");
            category3.setPriceRange("$30 - $80");
            categoryRepository.save(category3);

            Category category4 = new Category();
            category4.setName("Anime");
            category4.setDescription("Popular anime characters and themed mystery figures.");
            category4.setRarityLevel("Common to Legendary");
            category4.setPriceRange("$15 - $100");

            Category category5 = new Category();
            category5.setName("Steampunk");
            category5.setDescription("Victorian-era inspired mechanical and fantasy figures.");
            category5.setRarityLevel("Rare to Epic");
            category5.setPriceRange("$100 - $190");
            categoryRepository.save(category5);
        }

        if(brandRepository.count() == 0) {

            Brand brand1 = new Brand();
            brand1.setName("POP MART");
            brand1.setCountryOfOrigin("China");
            brandRepository.save(brand1);

            Brand brand2 = new Brand();
            brand2.setName("Funko");
            brand2.setCountryOfOrigin("USA");
            brandRepository.save(brand2);

            Brand brand3 = new Brand();
            brand3.setName("Kidrobot");
            brand3.setCountryOfOrigin("USA");
            brandRepository.save(brand3);

        }

    }
}
