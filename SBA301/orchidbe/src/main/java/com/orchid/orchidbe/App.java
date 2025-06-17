package com.orchid.orchidbe;

import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Category;
import com.orchid.orchidbe.pojos.Orchid;
import com.orchid.orchidbe.pojos.Order;
import com.orchid.orchidbe.pojos.OrderDetail;
import com.orchid.orchidbe.pojos.Role;
import com.orchid.orchidbe.repositories.AccountRepository;
import com.orchid.orchidbe.repositories.CategoryRepository;
import com.orchid.orchidbe.repositories.OrchidRepository;
import com.orchid.orchidbe.repositories.OrderDetailRepository;
import com.orchid.orchidbe.repositories.OrderRepository;
import com.orchid.orchidbe.repositories.RoleRepository;
import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final OrchidRepository orchidRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker")) {
            JavaBrowserLauncher.doHealthCheckThenOpenHomePage(
                "http://localhost:8080/actuator/health",
                "http://localhost:8080/swagger-ui.html");
        }

    }

    @Override
    public void run(String... args) throws Exception {
        // Create roles if empty
        List<Role> roles;
        if (roleRepository.findAll().isEmpty()) {
            var role1 = Role.builder().name("Admin").build();
            var role2 = Role.builder().name("User").build();
            var role3 = Role.builder().name("Manager").build();
            roles = roleRepository.saveAll(Arrays.asList(role1, role2, role3));
        } else {
            roles = roleRepository.findAll();
        }

        // Create categories if empty
        List<Category> categories;
        if (categoryRepository.findAll().isEmpty()) {
            var cat1 = Category.builder().name("Orchids").build();
            var cat2 = Category.builder().name("Exotic Flowers").build();
            var cat3 = Category.builder().name("Indoor Plants").build();
            categories = categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        } else {
            categories = categoryRepository.findAll();
        }

        // Create orchids if empty
        List<Orchid> orchids;
        if (orchidRepository.findAll().isEmpty()) {
            var o1 = Orchid.builder()
                .isNatural(true)
                .description("Natural orchid")
                .name("Phalaenopsis")
                .url("http://img1")
                .price(10.0)
                .categoryId(categories.isEmpty() ? null : categories.get(0).getId())
                .build();

            var o2 = Orchid.builder()
                .isNatural(false)
                .description("Hybrid orchid")
                .name("Cattleya")
                .url("http://img2")
                .price(15.0)
                .categoryId(categories.size() > 1 ? categories.get(1).getId() : null)
                .build();

            var o3 = Orchid.builder()
                .isNatural(true)
                .description("Random orchid")
                .name("Dendrobium")
                .url("http://img3")
                .price(20.0)
                .categoryId(categories.size() > 2 ? categories.get(2).getId() : null)
                .build();

            orchids = orchidRepository.saveAll(Arrays.asList(o1, o2, o3));
        } else {
            orchids = orchidRepository.findAll();
        }

        // Create accounts if empty
        List<Account> accounts;
        if (accountRepository.findAll().isEmpty()) {
            var acc1 = Account.builder()
                .name("Alice")
                .email("alice@example.com")
                .password("pass1")
                .role(roles.isEmpty() ? null : roles.get(0))
                .build();

            var acc2 = Account.builder()
                .name("Bob")
                .email("bob@example.com")
                .password("pass2")
                .role(roles.size() > 1 ? roles.get(1) : null)
                .build();

            var acc3 = Account.builder()
                .name("Charlie")
                .email("charlie@example.com")
                .password("pass3")
                .role(roles.size() > 2 ? roles.get(2) : null)
                .build();

            accounts = accountRepository.saveAll(Arrays.asList(acc1, acc2, acc3));
        } else {
            accounts = accountRepository.findAll();
        }

        // Create orders and order details if empty
        if (orderRepository.findAll().isEmpty()) {
            Order ord1 = Order.builder()
                .totalAmount(99.9)
                .orderDate(new Date())
                .orderStatus(Order.OrderStatus.PENDING)
                .accountId(accounts.isEmpty() ? null : accounts.get(0).getId())
                .build();

            Order ord2 = Order.builder()
                .totalAmount(149.5)
                .orderDate(new Date())
                .orderStatus(Order.OrderStatus.PROCESSING)
                .accountId(accounts.size() > 1 ? accounts.get(1).getId() : null)
                .build();

            Order ord3 = Order.builder()
                .totalAmount(199.0)
                .orderDate(new Date())
                .orderStatus(Order.OrderStatus.COMPLETED)
                .accountId(accounts.size() > 2 ? accounts.get(2).getId() : null)
                .build();

            List<Order> savedOrders = orderRepository.saveAll(Arrays.asList(ord1, ord2, ord3));

            // OrderDetails
            OrderDetail d1 = OrderDetail.builder()
                .price(10.0)
                .quantity(2)
                .orchidId(orchids.isEmpty() ? null : orchids.get(0).getId())
                .orderId(savedOrders.get(0).getId())
                .build();

            OrderDetail d2 = OrderDetail.builder()
                .price(15.0)
                .quantity(1)
                .orchidId(orchids.size() > 1 ? orchids.get(1).getId() : null)
                .orderId(savedOrders.get(1).getId())
                .build();

            OrderDetail d3 = OrderDetail.builder()
                .price(20.0)
                .quantity(3)
                .orchidId(orchids.size() > 2 ? orchids.get(2).getId() : null)
                .orderId(savedOrders.get(2).getId())
                .build();

            orderDetailRepository.saveAll(Arrays.asList(d1, d2, d3));
        }
    }

}
