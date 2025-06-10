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
            JavaBrowserLauncher.openHomePage("http://localhost:8080/swagger-ui.html");
        }

    }

    @Override
    public void run(String... args) throws Exception {
//        // Roles
//        Role role1 = new Role(0, "Admin");
//        Role role2 = new Role(0, "User");
//        Role role3 = new Role(0, "Manager");
//        roleRepository.saveAll(Arrays.asList(role1, role2, role3));
//
//        // Categories
//        Category cat1 = new Category(0, "Orchids");
//        Category cat2 = new Category(0, "Exotic Flowers");
//        Category cat3 = new Category(0, "Indoor Plants");
//        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
//
//        // Accounts - save and get managed entities
//        Account acc1 = new Account(0, "Alice", "alice@example.com", "pass1", role1);
//        Account acc2 = new Account(0, "Bob", "bob@example.com", "pass2", role2);
//        Account acc3 = new Account(0, "Charlie", "charlie@example.com", "pass3", role3);
//        List<Account> savedAccounts = accountRepository.saveAll(Arrays.asList(acc1, acc2, acc3));
//        acc1 = savedAccounts.get(0);
//        acc2 = savedAccounts.get(1);
//        acc3 = savedAccounts.get(2);
//
//        // Orchids
//        Orchid o1 = new Orchid(0, true, "Natural orchid", "Phalaenopsis", "http://img1", 10.0,
//                               cat1);
//        Orchid o2 = new Orchid(0, false, "Hybrid orchid", "Cattleya", "http://img2", 15.0, cat2);
//        Orchid o3 = new Orchid(0, true, "Random orchid", "Dendrobium", "http://img3", 20.0, cat3);
//        List<Orchid> savedOrchids = orchidRepository.saveAll(Arrays.asList(o1, o2, o3));
//        o1 = savedOrchids.get(0);
//        o2 = savedOrchids.get(1);
//        o3 = savedOrchids.get(2);
//
//        // Orders - don't set ID explicitly if using auto-generation
//        Order ord1 = new Order();
//        ord1.setTotalAmount(99.9);
//        ord1.setOrderDate(new Date());
//        ord1.setOrderStatus(Order.OrderStatus.PENDING);
//        ord1.setAccount(acc1);
//
//        Order ord2 = new Order();
//        ord2.setTotalAmount(149.5);
//        ord2.setOrderDate(new Date());
//        ord2.setOrderStatus(Order.OrderStatus.PROCESSING);
//        ord2.setAccount(acc2);
//
//        Order ord3 = new Order();
//        ord3.setTotalAmount(199.0);
//        ord3.setOrderDate(new Date());
//        ord3.setOrderStatus(Order.OrderStatus.COMPLETED);
//        ord3.setAccount(acc3);
//
//        List<Order> savedOrders = orderRepository.saveAll(Arrays.asList(ord1, ord2, ord3));
//        ord1 = savedOrders.get(0);
//        ord2 = savedOrders.get(1);
//        ord3 = savedOrders.get(2);
//
//        // OrderDetails
//        OrderDetail d1 = new OrderDetail(0, 10.0, 2, o1, ord1);
//        OrderDetail d2 = new OrderDetail(0, 15.0, 1, o2, ord2);
//        OrderDetail d3 = new OrderDetail(0, 20.0, 3, o3, ord3);
//        orderDetailRepository.saveAll(Arrays.asList(d1, d2, d3));
    }

}
