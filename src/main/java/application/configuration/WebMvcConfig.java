package application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * CORS Filter
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")					
		  .allowedOrigins("*")		
		  .allowedMethods("GET", "POST", "PUT", "DELETE");						
		
	}
	
}
