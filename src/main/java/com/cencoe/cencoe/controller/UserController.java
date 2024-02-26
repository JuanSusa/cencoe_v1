package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.service.IUserService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v2/cencoe")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {

        this.userService = userService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object>  listUsers(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size) {

        MensajeResponse responseListUsers = userService.listUsers(page, size);
        return new ResponseEntity<>(responseListUsers, HttpStatus.OK);
    }

    @GetMapping("usuario/{id}")
    public ResponseEntity<Object> findUsersById(@PathVariable Long id) {

        MensajeResponse responseFindUser = userService.findUser(id);
        return new ResponseEntity<>(responseFindUser, HttpStatus.OK);
    }

    @PostMapping("/usuario")
    public ResponseEntity<Object> saveCampaign(@RequestBody User user) {

        MensajeResponse responseSaveUser = userService.saveUser(user);
        return new ResponseEntity<>(responseSaveUser, HttpStatus.OK);
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User usersToUpdate, @PathVariable Long id) {

        MensajeResponse findUserToUpdate = userService.findUser(id);
        if (findUserToUpdate != null) {

            MensajeResponse responseUserToUpdate = userService.updateUser(usersToUpdate);
            return new ResponseEntity<>(responseUserToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {

        MensajeResponse responseUserToDelete = userService.deleteUser(id);
        return new ResponseEntity<>(responseUserToDelete, HttpStatus.OK);
    }
}
