package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
