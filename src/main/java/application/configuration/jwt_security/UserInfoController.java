package application.configuration.jwt_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        
    	try {
    		UserInfo savedUser = this.userInfoService.addUser(userInfo);
//        	savedUser.setPassword(null);
    		return new ResponseEntity<UserInfo>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("User e-mail is already in use: " + userInfo.getEmail(), HttpStatus.NOT_ACCEPTABLE);	
		}

    }

    
    @RequestMapping(value = "/user/userProfile/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    // @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> userProfile(@PathVariable("userId") Long userId) {
    	
    	UserInfo user = this.userInfoService.getUserInfoById(userId);
    	if(user == null) {
    		return new ResponseEntity<String>("Not found User with id: " + userId, HttpStatus.NOT_FOUND);
    	}
    	
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/adminProfile", method = RequestMethod.GET)
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

//    @PostMapping("/generateToken")
    @RequestMapping(value = "/generateToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	
        	JwtTokenDTO jwtToken = new JwtTokenDTO(jwtService.generateToken(authRequest.getEmail())); 
        	
            return new ResponseEntity<JwtTokenDTO>(jwtToken, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request ");
        }
        
    }

}