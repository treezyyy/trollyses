package io.bootify.trollys.repos;

import io.bootify.trollys.entity.DocumentFile;
import io.bootify.trollys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {

    List<DocumentFile> getAllFilesByUser(User user);

}
