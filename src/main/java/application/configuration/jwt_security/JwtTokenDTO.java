package application.configuration.jwt_security;

import java.io.Serializable;

public class JwtTokenDTO implements Serializable {

	private static final long serialVersionUID = 1098316779781276440L;

	private String jwtToken;

	public JwtTokenDTO() {

	}

	public JwtTokenDTO(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	@Override
	public String toString() {
		return "JwtTokenDTO [jwtToken=" + jwtToken + "]";
	}

}
