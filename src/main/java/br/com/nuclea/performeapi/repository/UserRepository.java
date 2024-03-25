package br.com.nuclea.performeapi.repository;

import br.com.nuclea.performeapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailAndCpfCnpj(String email, String cpfCnpj);

	User getByCpfCnpjAndEmail(String cpfCnpj, String email);

	User findByEmail(String email);
	
	@Query("SELECT u FROM USER u JOIN FETCH u.roles WHERE u.email = :email")
	User findByUsernameFetchRoles(@Param("email") String email);

	List<User> findByRolesName(String accreditor);
}
