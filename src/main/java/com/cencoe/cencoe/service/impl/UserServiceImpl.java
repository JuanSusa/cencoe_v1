package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.repository.IUserRepository;
import com.cencoe.cencoe.service.IUserService;
import com.cencoe.cencoe.util.MensajeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listUsers(int page, int size) {
        Page<User> userPage = null;

        try {
            Pageable pageable = PageRequest.of(page, size);
            userPage = userRepository.findAll(pageable);
        } catch (DataAccessException dtEx) {
            log.error("Error al obtener usuarios paginados", dtEx);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (userPage.isEmpty()) {
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
                    userPage);
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
        Optional<User> existingUser = userRepository.findByUserNumDoc(user.getUserNumDoc());
        Optional<User> existingUserByEmail = userRepository.findByUserEmail(user.getUserEmail());

        if (existingUser.isPresent()) {
            // El número de documento ya existe, devolver mensaje de error
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    "El número de documento ya está registrado en la base de datos",
                    false,
                    null);
        }

        if (existingUserByEmail.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    "Correo electronico ya existe",
                    false,
                    null);
        }

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

//    @Override
//    public MensajeResponse authenticate() {
//        try {
//            Optional<User> userAuth = userRepository.findByUserNumDoc(userLoginRequest.getNumDoc());
//            User user = userAuth.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
//            if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getUserPassword())) {
//
//                return MensajeResponse.buildMensajeGeneral(HttpStatus.OK, "Inicio de Sesión Exitoso", true, null);
//            } else {
//                return MensajeResponse.buildMensajeGeneral(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos", false, null);
//            }
//        } catch (UsernameNotFoundException e) {
//            return MensajeResponse.buildMensajeGeneral(HttpStatus.NOT_FOUND, "Usuario no encontrado", false, null);
//        } catch (Exception e) {
//            return MensajeResponse.buildMensajeGeneral(HttpStatus.INTERNAL_SERVER_ERROR, "Error al autenticar usuario", false, null);
//        }
//    }
//
}