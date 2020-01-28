package oss.pilot.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;

import oss.pilot.rest.model.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findByUid(String email);

    Optional<User> findByUidAndProvider(String uid, String provider);
}
