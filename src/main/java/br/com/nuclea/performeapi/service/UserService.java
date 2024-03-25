package br.com.nuclea.performeapi.service;

import br.com.nuclea.performeapi.entity.Role;
import br.com.nuclea.performeapi.entity.User;
import br.com.nuclea.performeapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	public static final String ACCREDITOR = "ACCREDITOR";
	@Autowired
	private UserRepository repository;
	
	public ResponseEntity<User> createUser(User user) {
		User existsUser = repository.findByEmailAndCpfCnpj(user.getEmail(), user.getCpfCnpj());
		if(existsUser != null) {
			throw new Error("User already exists!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	public User getByCpfCnpjAndEmail(String cpfCnpj, String email) {
		return repository.getByCpfCnpjAndEmail(cpfCnpj, email);
	}

	public List<User> getAll() {
		return repository.findAll();
	}

	public User createIfNotExists(User user) {
		User tkUser = getByCpfCnpjAndEmail(user.getCpfCnpj(), user.getEmail());
		if(tkUser == null) {
			return repository.save(user);
		}else {
			return tkUser;
		}
	}

	public User findByID(long id){
		return repository.getReferenceById(id);
	}

	public User makeAccreditor(Long id) {
		User user = findByID(id);
		Role role = new Role(ACCREDITOR);
		List<Role> roles = user.getRoles();
		roles.add(role);
		user.setRoles(roles);
		return repository.save(user);
	}

	public List<User> findAllAccreditors() {
		return repository.findByRolesName(ACCREDITOR);
	}

	public List<User> findAll() {
		List<User> users = repository.findAll();
		users.forEach(tkUser -> {
			tkUser.setIsAccreditor(false);
			tkUser.getRoles().forEach(role -> {
				if(role.getName().equals(ACCREDITOR)){
					tkUser.setIsAccreditor(true);
				}
			});
		});
		return users;
	}

	public User findByUsername(String username) {
		return repository.findByEmail(username);
	}

	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		User existsUser = repository.findByUsernameFetchRoles(username);

		if(existsUser == null) {
			throw new Error("usuário não existe");
		}
		return existsUser;
	}
}
