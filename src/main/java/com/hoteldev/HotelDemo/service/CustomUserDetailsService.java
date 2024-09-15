package com.hoteldev.HotelDemo.service;

import com.hoteldev.HotelDemo.exception.ProjectException;
import com.hoteldev.HotelDemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ClassName: CustomUserDetailSerivce
 * Package: com.hoteldev.HotelDemo.service
 * Description:
 *
 * @Author MegaSwampert
 * @Create 5/09/2024 2:55 pm
 * @Version 1.0
 */

/*service: a service component in spring, allowing it to be detected
 * during component scanning and instantiated as a bean*/
@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*this class implements the UserDetailsService from spring security.
    * this interface requires the implementation of the loadUserByUsername().
    * UsernameNotFoundException is from spring security.*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /*orElseThrow: if the user is not found, throws an exception with error message
       * it is a method of Optional class*/
        return userRepository.findByEmail(username).orElseThrow(() -> new ProjectException("Error: Username/Email not found."));
    }
}
