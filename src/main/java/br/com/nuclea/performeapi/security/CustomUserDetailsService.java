package br.com.nuclea.performeapi.security;

import br.com.nuclea.performeapi.entity.User;
import br.com.nuclea.performeapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User existsUser = userRepository.findByUsernameFetchRoles(username);
		
		if(existsUser == null) {
			throw new Error("usuário não existe");
		}
		return UserPrincipal.createInstance(existsUser);
	}

}
