package io.bootify.trollys.repos;

import io.bootify.trollys.entity.User;
import io.bootify.trollys.entity.UserBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserBonusRepository extends JpaRepository<UserBonus, Long> {

    boolean existsByUserAndDate(User user, LocalDate date);

    List<UserBonus> findByUser(User user);
}
