package io.bootify.trollys.repos;


import io.bootify.trollys.entity.TaskHistory;
import io.bootify.trollys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    Optional<TaskHistory> findByTaskId(Long taskId);
    List<TaskHistory> findByUser(User user);
}