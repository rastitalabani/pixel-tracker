package se.rbg.pixeltracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.rbg.pixeltracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findUserByUserId(UUID userId);
  List<User> findAll();
}
