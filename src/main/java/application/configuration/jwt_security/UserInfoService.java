package application.configuration.jwt_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    public UserInfo getUserInfoById(Long userId) {
    	
    	Optional<UserInfo> user = this.userInfoRepository.findById(userId);
    	if(user.isPresent() == false) {
    		return null;
    	}
    	
    	return user.get();
    }
    
    /**
     * Add UserInfo in Database without hashing User password.<br>
     * This is used when User password is already read from Database in Hashed form, so there is no need to Hash password again.
     * @param userInfo - userinfo object with already Hashed User Password
     * @return saved UserInfo object
     */
    public UserInfo addUserNoHashPassword(UserInfo userInfo) {
    	
    	userInfo = this.userInfoRepository.save(userInfo);
    	return userInfo;
    }
    
    
    //--------------------------------------------------------------------------
    // Custom UserInfoService Methods
    //--------------------------------------------------------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = this.userInfoRepository.findByEmail(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public UserInfo addUser(UserInfo userInfo) throws SQLIntegrityConstraintViolationException {
    	
    	userInfo.setPassword(encoder.encode(userInfo.getPassword()));
    	UserInfo savedUser =  this.userInfoRepository.save(userInfo);
		return savedUser;
        
    }
    
    /**
     * Gets UserInfo Hashed Password from Database
     * @param userInfo
     * @return hashed User Password
     */
    public String getUserInfoPassword(UserInfo userInfo) {
    	
    	Optional<UserInfo> savedUserInfo = this.userInfoRepository.findById(userInfo.getId());
    	if(savedUserInfo.isPresent() == false) {
    		return null;
    	}
    	
    	return savedUserInfo.get().getPassword();
    }
    
    public Boolean exists(UserInfo userInfo) {
    	
    	if(this.userInfoRepository.existsById(userInfo.getId()) == true) {
    		return true;
    	}
    	
    	return false;
    }
    

}