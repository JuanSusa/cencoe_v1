package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}