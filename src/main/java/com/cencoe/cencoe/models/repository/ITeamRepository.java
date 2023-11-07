package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, Long> {
}
