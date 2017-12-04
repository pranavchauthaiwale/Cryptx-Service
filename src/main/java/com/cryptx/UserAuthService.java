package com.cryptx;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.CryptxUser;
import com.cryptx.services.IUserService;

@Service
public class UserAuthService implements UserDetailsService {

	private final static Logger logger = LoggerFactory.getLogger(UserAuthService.class);
	
	@Autowired
	IUserService userServie;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		CryptxUser user;
		try {
			user = userServie.findUserAuthDetailsByEmail(email);
		} catch (CryptxException e) {
			logger.info("Exception in loading user by email: " + email);
			logger.error("Error message: " + e.getMessage());
			e.printStackTrace();
			throw new UsernameNotFoundException("Invalid username/password");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
		//return null;
	}

}
