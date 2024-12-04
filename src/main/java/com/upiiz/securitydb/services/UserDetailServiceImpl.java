package com.upiiz.securitydb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.upiiz.securitydb.entities.UserEntity;
import com.upiiz.securitydb.repositories.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Requerimos consultar un usuario por username
        // Cont odos sus detalles
        //Quien lo realiza - Repositorio
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(()->new UsernameNotFoundException(username+"not found"));
        // En userEntity = Sus datos, roles y permisos
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //Roles
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleEnum().name()));
        });
        //Permisos
        userEntity.getRoles().stream()
        .flatMap(role -> role.getPermissions().stream())
        .forEach(permission-> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(), 
        userEntity.getPassword(), 
        userEntity.isEnabled(), 
        userEntity.isAccountNonExpired(), 
        userEntity.isCredentialNoExpired(), 
        userEntity.isAccountNonLocked(), 
        authorities);
    }
}
