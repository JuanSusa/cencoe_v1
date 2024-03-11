package com.cencoe.cencoe.service.impl;


import com.cencoe.cencoe.models.entity.UserLoginRequest;
import com.cencoe.cencoe.models.repository.IUserRepository;
import com.cencoe.cencoe.service.IAuthService;
import com.cencoe.cencoe.util.MensajeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.cencoe.cencoe.security.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    //    @Override
//    public MensajeResponse login(UserLoginRequest loginRequest) {
//        logger.info("Autenticando usuario {}", loginRequest.getNumDoc());
//      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                        loginRequest.getNumDoc(),
//                        loginRequest.getPassword()
//                )
//        );
//        Optional<User> userOptional = userRepository.findByUserNumDoc(loginRequest.getNumDoc());
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            String token = jwtService.generateToken(authentication);
//            return MensajeResponse.buildMensajeGeneral(HttpStatus.OK, "Login exitoso", true, token);
//        }else{
//            return MensajeResponse.buildMensajeGeneral(HttpStatus.NOT_FOUND, "Usuario no encontrado", false, null);
//        }
//    }
    public MensajeResponse login(UserLoginRequest loginRequest) {
        try {
            logger.info("Autenticando usuario {}", loginRequest.getUserNumDoc());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserNumDoc(),
                            loginRequest.getUserPassword()
                    )
            );

            String token = jwtService.generateToken(authentication);
            return MensajeResponse.buildMensajeGeneral(HttpStatus.OK, "Login exitoso", true, token);
        } catch (AuthenticationException e) {
            logger.error("Error al autenticar usuario", e);
            return MensajeResponse.buildMensajeGeneral(HttpStatus.UNAUTHORIZED, "Error al autenticar usuario", false, null);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return MensajeResponse.buildMensajeGeneral(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado", false, null);
        }
    }
}
