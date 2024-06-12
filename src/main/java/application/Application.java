package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//---------------------------------------------------------------------------
// Spring Security is currently - disabled
//---------------------------------------------------------------------------
// To Enable Spring Security go to application.propertirs and set property:
// custom.variables.SpringSecurity.enable=false
//---------------------------------------------------------------------------
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
	}
	
}
