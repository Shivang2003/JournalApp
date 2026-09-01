package net.springproject.journalApp.service;

import net.springproject.journalApp.entity.User;
import net.springproject.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByUserName(){
        User user = userService.findByUserName("t3");
        assertNotNull(userService.findByUserName("t3"));
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "t1",
            "t2",
            "t3ASDA"
    })
    public void testFindAllUsers(String userName){
        User user = userService.findByUserName(userName);
        assertNotNull(userService.findByUserName(userName));
        assertTrue(!user.getJournalEntries().isEmpty());
    }

//    @BeforeAll
//    @BeforeEach
//    @AfterAll
//    @AfterEach
}
