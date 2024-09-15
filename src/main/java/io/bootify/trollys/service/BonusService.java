package io.bootify.trollys.service;

import io.bootify.trollys.entity.User;
import io.bootify.trollys.entity.UserBonus;
import io.bootify.trollys.repos.UserBonusRepository;
import io.bootify.trollys.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BonusService {

    private UserRepository userRepository;

    private UserBonusRepository userBonusRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BonusService.class);


    private static final int DAILY_BONUS = 10;

    public boolean giveDailyBonus(User user) {

        LOGGER.debug("Старт получения бонуса");

        LocalDate today = LocalDate.now();

        try {
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
        } catch (NullPointerException e) {
            LOGGER.error("NullPointer ошибка: ", e);
            throw new IllegalArgumentException("Ошибка при получении бонуса: недостаточные данные");
        } catch (OptimisticLockingFailureException e) {
            LOGGER.error("Конфликт версий при сохранении: ", e);
            throw new IllegalArgumentException("Ошибка при сохранении данных");
        }
    }

    public int getUserBonusPoints(User user) {
        return user.getBonusPoints();
    }

}
