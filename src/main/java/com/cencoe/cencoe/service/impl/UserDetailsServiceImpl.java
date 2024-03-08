package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Role;
import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String userNumDoc) throws UsernameNotFoundException {
        User user = userRepository.findByUserNumDoc(userNumDoc).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(user.getUserNumDoc(), user.getUserPassword(), mapToAuthorities(user.getRoles()));
    }
}

