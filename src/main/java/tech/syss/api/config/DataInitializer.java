package tech.syss.api.config;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.syss.api.model.Membership;
import tech.syss.api.model.Role;
import tech.syss.api.repository.MembershipRepository;
import tech.syss.api.repository.RoleRepository;
import tech.syss.api.repository.UserRepository;
import tech.syss.api.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final UserService userService;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, MembershipRepository membershipRepository,
        UserService userService) {
            this.roleRepository = roleRepository;
            this.userRepository = userRepository;
            this.membershipRepository = membershipRepository;
            this.userService = userService;
        }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
        }

        if (userRepository.count() == 0) {
            userService.create("admin", "admin123", Set.of("ADMIN", "USER"));
            userService.create("user", "user123", Set.of("USER"));
        }

        if (membershipRepository.count() == 0) {
            membershipRepository.save(new Membership("Monthly", 1, 0, 350.0));
            membershipRepository.save(new Membership("Quarter", 3, 0, 900.0));
            membershipRepository.save(new Membership("Yearly", 12, 0, 3500.0));
            membershipRepository.save(new Membership("Weekly", 0, 1, 100.0));
        }
    }
}
