package application.configuration.jwt_security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    
//	public Optional<UserDTO> findByName(String username);
	public Optional<UserInfo> findByEmail(String email);
	
}

