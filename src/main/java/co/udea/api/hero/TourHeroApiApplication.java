package co.udea.api.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourHeroApiApplication {

	/*
	 * Documentación de la api-rest en: http://localhost:8080/toh-api/swagger-ui.html
	 */

	public static void main(String[] args) {
		SpringApplication.run(TourHeroApiApplication.class, args);
	}

}
