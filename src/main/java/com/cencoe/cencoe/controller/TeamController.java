package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.Team;
import com.cencoe.cencoe.service.ITeamService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/cencoe")
public class TeamController {

    private final ITeamService teamService;

    @Autowired
    public TeamController(ITeamService teamService){

        this.teamService = teamService;
    }

    @GetMapping("/grupos")
    public ResponseEntity<Object> listTeam(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {

        MensajeResponse responseListTeam = teamService.listTeam(page, size);
        return new ResponseEntity<>(responseListTeam, HttpStatus.OK);
    }

    @GetMapping("grupo/{id}")
    public ResponseEntity<Object> findTeamById(@PathVariable Long id) {

        MensajeResponse responseFindTeam = teamService.findTeam(id);
        return new ResponseEntity<>(responseFindTeam, HttpStatus.OK);
    }

    @PostMapping("/grupo")
    public ResponseEntity<Object> saveTeam(@RequestBody Team team) {

        MensajeResponse responseSaveTeam = teamService.saveTeam(team);
        return new ResponseEntity<>(responseSaveTeam, HttpStatus.OK);
    }

    @PutMapping("/grupo/{teamId}")
    public ResponseEntity<Object> updateTeam(@RequestBody Team teamToUpdate, @PathVariable Long teamId) {

        MensajeResponse findTeamToUpdate = teamService.findTeam(teamId);
        if (findTeamToUpdate != null) {

            MensajeResponse responseTeamToUpdate = teamService.updateTeam(teamToUpdate);
            return new ResponseEntity<>(responseTeamToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/grupo/{teamId}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long teamId) {

        MensajeResponse responseTeamToDelete = teamService.deleteTeam(teamId);
        return new ResponseEntity<>(responseTeamToDelete, HttpStatus.OK);
    }



}
