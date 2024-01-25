package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.repository.IUserRepository;
import com.cencoe.cencoe.service.IUserService;
import com.cencoe.cencoe.util.MensajeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository){

        this.userRepository = userRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listUsers() {
        List<User> getListUser;

        try {
            getListUser = userRepository.findAll();
        } catch (DataAccessException dtEx) {
            log.info("error");
            log.error("error", dtEx);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListUser.isEmpty()) {
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
                    getListUser);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findUser(Long userId) {
        Optional<User> searchUser;
        try {
            searchUser = userRepository.findById(userId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchUser.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchUser);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El usuario que busca no se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse saveUser(User user) {
        User userToSave;
        try {
            userToSave = userRepository.save(user);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.CREATED,
                "Usuario creado con éxito",
                true,
                userToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateUser(User userUpdate) {
        Optional<User> userToUpdate;
        try {
            userToUpdate = userRepository.findById(userUpdate.getUserId());

        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (userToUpdate.isPresent()) {

            User userExisting = userToUpdate.get();
            userExisting.setUserId(userUpdate.getUserId());
            userExisting.setUserNumDoc(userUpdate.getUserNumDoc());
            userExisting.setUserName(userUpdate.getUserName());
            userExisting.setUserLastName(userUpdate.getUserLastName());
            userExisting.setUserAddress(userUpdate.getUserAddress());
            userExisting.setUserPhone(userUpdate.getUserPhone());
            userExisting.setUserEmail(userUpdate.getUserEmail());
            userExisting.setUserPassword(userUpdate.getUserPassword());
            userExisting.setUserState(userUpdate.getUserState());
            userExisting.setUserDocType(userUpdate.getUserDocType());
//            userExisting.setRoles(userUpdate.getRoles());
//            userExisting.setTeams(userUpdate.getTeams());


            User userUpdated = userRepository.save(userExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Usuario actualizada con éxito",
                    true,
                    userUpdated);

        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El usuario que desea actualizar no existe",
                    true,
                    null);
        }
    }

    @Override
    public MensajeResponse deleteUser(Long userId) {
        Optional<User> userToDelete = userRepository.findById(userId);
        try {
            if (userToDelete.isPresent()) {
                userRepository.deleteById(userId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "El usuario que desea eliminar no existe",
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
                "Usuario eliminado con éxito",
                true,
                null);
    }

}
