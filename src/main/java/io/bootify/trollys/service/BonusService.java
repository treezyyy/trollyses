package io.bootify.trollys.service;

import io.bootify.trollys.entity.User;
import io.bootify.trollys.entity.UserBonus;
import io.bootify.trollys.repos.UserBonusRepository;
import io.bootify.trollys.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BonusService {

    private UserRepository userRepository;

    private UserBonusRepository userBonusRepository;

    private static final int DAILY_BONUS = 10;

    public boolean giveDailyBonus(User user) {
        LocalDate today = LocalDate.now();

        boolean hasReceivedBonusToday = userBonusRepository.existsByUserAndDate(user, today);

        if (!hasReceivedBonusToday) {
            UserBonus userBonus = new UserBonus();
            userBonus.setUser(user);
            userBonus.setPoints(DAILY_BONUS);
            userBonus.setDate(today);
            userBonus.setBonusType("Daily");
            userBonusRepository.save(userBonus);
            user.setBonusPoints(user.getBonusPoints() + DAILY_BONUS);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public int getUserBonusPoints(User user) {
        return user.getBonusPoints();
    }

}
