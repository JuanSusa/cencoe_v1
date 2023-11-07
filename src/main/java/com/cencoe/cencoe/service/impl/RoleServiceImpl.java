package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Role;
import com.cencoe.cencoe.models.repository.IRoleRepository;
import com.cencoe.cencoe.service.IRoleService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(IRoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listRoles() {

        List<Role> getListRoles;

        try {
            getListRoles = roleRepository.findAll();
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListRoles.isEmpty()) {
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
                    getListRoles);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findRolById(Long roleId) {
        Optional<Role> searchRole;
        try {
            searchRole = roleRepository.findById(roleId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchRole.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchRole);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "No se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse saveRole(Role role) {

        Role roleToSave;
        try {
            roleToSave = roleRepository.save(role);
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
                roleToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateRole(Role roleToUpdate) {
        Optional<Role> searchRoleToUpdate;
        try {
            searchRoleToUpdate = roleRepository.findById(roleToUpdate.getRoleId());
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchRoleToUpdate.isPresent()) {

            Role roleExisting = searchRoleToUpdate.get();
            roleExisting.setRoleId(roleToUpdate.getRoleId());
            roleExisting.setRoleName(roleToUpdate.getRoleName());
            roleExisting.setRoleState(roleToUpdate.getRoleState());

            Role roleUpdated = roleRepository.save(roleExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.CREATED,
                    "Registro actualizado con éxito",
                    true,
                    roleUpdated);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    ("El rol que desea actualizar con el id: " + roleToUpdate.getRoleId() + " no se encuentra en la base de datos"),
                    true,
                    null);
        }

    }

    @Override
    @Transactional
    public MensajeResponse deleteRole(Long roleId) {

        Optional<Role> roleToDelete = roleRepository.findById(roleId);
        try {
            if (roleToDelete.isPresent()) {
                roleRepository.deleteById(roleId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "El rol que desea eliminar no existe",
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
                "Rol eliminado con éxito",
                true,
                null);
    }
}
