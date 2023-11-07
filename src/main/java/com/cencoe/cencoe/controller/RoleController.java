package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.Role;
import com.cencoe.cencoe.service.IRoleService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/cencoe")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {

        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> listRoles() {

        MensajeResponse responseListRole = roleService.listRoles();
        return new ResponseEntity<>(responseListRole, HttpStatus.OK);

    }

    @GetMapping("/rol/{roleId}")
    public ResponseEntity<Object> showRoleById(@PathVariable Long roleId) {

        MensajeResponse responseFindRoleById = roleService.findRolById(roleId);
        return new ResponseEntity<>(responseFindRoleById, HttpStatus.OK);
    }

    @PostMapping("/rol")
    public ResponseEntity<Object> saveRole(@RequestBody Role role) {

        MensajeResponse responseSaveRole = roleService.saveRole(role);
        return new ResponseEntity<>(responseSaveRole, HttpStatus.OK);
    }

    @PutMapping("rol/{roleId}")
    public ResponseEntity<Object> updateRole(@PathVariable Long roleId, @RequestBody Role roleToUpdate) {

        MensajeResponse findRoleToUpdate = roleService.findRolById(roleId);
        if (findRoleToUpdate != null) {

            MensajeResponse responseRoleToUpdate = roleService.updateRole(roleToUpdate);
            return new ResponseEntity<>(responseRoleToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("rol/{roleId}")
    public ResponseEntity<Object> deleteRole(@PathVariable Long roleId) {

        MensajeResponse responseRoleToDelete = roleService.deleteRole(roleId);
        return new ResponseEntity<>(responseRoleToDelete, HttpStatus.OK);
    }

}
