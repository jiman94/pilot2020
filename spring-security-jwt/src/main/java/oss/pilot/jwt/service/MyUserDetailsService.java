package oss.pilot.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	
	@Autowired
	PasswordEncoder encoder;
	
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	 
    	String password = encoder.encode("foo");
    	 
    	
        return new User("foo", password,
                new ArrayList<>());
    }
}