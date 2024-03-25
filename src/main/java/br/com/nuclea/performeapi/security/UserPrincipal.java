package br.com.nuclea.performeapi.security;

import br.com.nuclea.performeapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

	private Long id;
	private String name;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private String token;
	
	public UserPrincipal (User user) {
		this.id		  = user.getId();
		this.name	  = user.getName();
		this.username = user.getEmail();
		this.password = user.getPassword();
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		
		grantedAuthorities = user.getRoles().stream().map(role -> {
			return new SimpleGrantedAuthority("ROLE_".concat(role.getName()));
		}).collect(Collectors.toList());
		
		this.authorities = grantedAuthorities;
	}
	
	public static UserPrincipal createInstance(User user) {
		return new UserPrincipal(user);
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
