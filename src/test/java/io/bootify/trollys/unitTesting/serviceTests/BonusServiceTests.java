package io.bootify.trollys.unitTesting.serviceTests;

import io.bootify.trollys.entity.User;
import io.bootify.trollys.entity.UserBonus;
import io.bootify.trollys.repos.UserBonusRepository;
import io.bootify.trollys.repos.UserRepository;
import io.bootify.trollys.service.BonusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BonusServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBonusRepository userBonusRepository;

    @InjectMocks
    private BonusService bonusService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setBonusPoints(0);
    }

    @Test
    void giveDailyBonusTest() {
        when(userBonusRepository.existsByUserAndDate(user, LocalDate.now())).thenReturn(false);

        boolean result = bonusService.giveDailyBonus(user);

        assertTrue(result);
        assertEquals(10, (int) user.getBonusPoints());

        verify(userBonusRepository, times(1)).save(any(UserBonus.class));
        verify(userRepository, times(1)).save(user);
    }



}
