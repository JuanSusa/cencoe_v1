package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Role;
import com.cencoe.cencoe.util.MensajeResponse;

import java.util.List;

public interface IRoleService {

    MensajeResponse listRoles();

    MensajeResponse findRolById(Long roleId);

    MensajeResponse saveRole(Role role);

    MensajeResponse updateRole(Role roleToUpdate);

    MensajeResponse deleteRole(Long roleId);


}
