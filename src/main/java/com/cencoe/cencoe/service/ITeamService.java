package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Team;
import com.cencoe.cencoe.util.MensajeResponse;

public interface ITeamService {

    MensajeResponse listTeam();

    MensajeResponse findTeam(Long teamId);

    MensajeResponse saveTeam(Team team);

    MensajeResponse updateTeam(Team teamUpdate);

    MensajeResponse deleteTeam(Long teamId);
}
