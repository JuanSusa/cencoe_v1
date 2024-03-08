package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNumDoc(String userNumDoc);

    Optional<User> findByUserEmail(String userEmail);

}
