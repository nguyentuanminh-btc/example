package com.example.service;

import java.util.HashSet;
import java.util.Set;

import com.example.authentication.CustomUserDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
		this.logger.debug("username = " + user.getUsername());
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new CustomUserDetail(
				user.getUsername(), user.getPassword(), grantedAuthorities, user.getId());
	}

}
