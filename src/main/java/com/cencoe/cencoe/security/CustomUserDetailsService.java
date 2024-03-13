package com.cencoe.cencoe.security;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String numDoc) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        User user= userRepository.findByUserNumDoc(numDoc)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        logger.info("User Authenticated Successfully..!!!");

        return new CustomUserDetails(user);
    }
}

/*Este servicio busca un usuario en la base de datos utilizando el número de documento proporcionado.
 Si el usuario no se encuentra, se lanza una excepción UsernameNotFoundException. Si el usuario se encuentra,
  se devuelve un objeto CustomUserDetails que contiene los detalles del usuario. Este objeto CustomUserDetails
   es una implementación personalizada de la interfaz UserDetails de Spring Security, que se utiliza para representar
    los detalles de un usuario autenticado.
 */