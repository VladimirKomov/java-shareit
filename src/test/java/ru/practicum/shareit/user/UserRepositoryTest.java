package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository repository;

//    @Test
//    void findByEmailContainingIgnoreCase() {
//        User user = new User(1, "newName", "newUser@user.com");
//        em.persist(user);
//        List<User> result = repository.findByEmailContainingIgnoreCase("newUser@user.com");
//        then(result).size().isEqualTo(1);
//        then(result).containsExactlyElementsOf(List.of(user));
//    }

}
