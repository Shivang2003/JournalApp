package net.springproject.journalApp.repository;

import lombok.extern.slf4j.Slf4j;
import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Slf4j
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testLoadUserByUsername() {
        List<User> users = userRepositoryImpl.getUserForSA();
        log.info(users.toString());
    }
}
