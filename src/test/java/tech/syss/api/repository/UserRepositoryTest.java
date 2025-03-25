package tech.syss.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tech.syss.api.model.Role;
import tech.syss.api.model.User;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByUsername() {
        Role role = new Role("USER");
        roleRepository.save(role);
        User user = new User(1L, "Fernando", "password", Set.of(role));
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("Fernando");
        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals("Fernando", foundUser.get().getUsername());
        assertInstanceOf(Set.class, foundUser.get().getRoles());
    }

    @Test
    void testFailFindByUsername() {
        Role role = new Role("USER");
        roleRepository.save(role);
        User user = new User(1L, "Fernando", "password", Set.of(role));
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("Luis");
        assertFalse(foundUser.isPresent());
        assertThrows(NoSuchElementException.class, () -> foundUser.get());
    }
}
