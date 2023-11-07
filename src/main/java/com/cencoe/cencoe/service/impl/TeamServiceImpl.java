package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Team;
import com.cencoe.cencoe.models.repository.ITeamRepository;
import com.cencoe.cencoe.service.ITeamService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements ITeamService {

    private final ITeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(ITeamRepository teamRepository) {

        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listTeam() {
        List<Team> getListTeams;

        try {
            getListTeams = teamRepository.findAll();
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListTeams.isEmpty()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "No Hay registros en la base de datos",
                    true,
                    null);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta exitosa",
                    true,
                    getListTeams);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findTeam(Long teamId) {
        Optional<Team> searchTeam;
        try {
            searchTeam = teamRepository.findById(teamId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchTeam.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchTeam);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El grupo que busca no se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse saveTeam(Team team) {
        Team teamToSave;
        try {
            teamToSave = teamRepository.save(team);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.CREATED,
                "Registro creado con éxito",
                true,
                teamToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateTeam(Team teamUpdate) {

        Optional<Team> teamToUpdate;
        try {
            teamToUpdate = teamRepository.findById(teamUpdate.getTeamId());

        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (teamToUpdate.isPresent()) {

            Team teamExisting = teamToUpdate.get();
            teamExisting.setTeamId(teamUpdate.getTeamId());
            teamExisting.setTeamName(teamUpdate.getTeamName());
            teamExisting.setTeamCapacity(teamUpdate.getTeamCapacity());
            teamExisting.setTeamState(teamUpdate.getTeamState());

            Team teamUpdated = teamRepository.save(teamExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Grupo actualizado con éxito",
                    true,
                    teamUpdated);

        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El grupo que desea actualizar no existe",
                    true,
                    null);
        }
    }

    @Override
    public MensajeResponse deleteTeam(Long teamId) {
        Optional<Team> teamToDelete = teamRepository.findById(teamId);
        try {
            if (teamToDelete.isPresent()) {
                teamRepository.deleteById(teamId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "El grupo que desea eliminar no existe",
                        true,
                        null);
            }
        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.NO_CONTENT,
                "Grupo   eliminado con éxito",
                true,
                null);
    }

}
