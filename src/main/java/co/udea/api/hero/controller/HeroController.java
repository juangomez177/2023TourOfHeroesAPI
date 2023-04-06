package co.udea.api.hero.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.udea.api.hero.model.Hero;
import co.udea.api.hero.service.HeroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/heroes") // La URI donde serán inyectados los siguientes servicios
public class HeroController {

	private final Logger log = LoggerFactory.getLogger(HeroController.class);

	private HeroService heroService;

	public HeroController(HeroService heroService) {
		this.heroService = heroService;
	}

	// Obtener todos los heroes (http://localhost:8080/toh-api/heroes)
	@GetMapping
	// Documentación Swagger, disponible en http://localhost:8080/toh-api/swagger-ui.html
	@ApiOperation(value = "Obtiene todos los heroes", response = Hero.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Heroes encontrados exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<List<Hero>> getHeroes() {
		log.info("Rest request obtener todos los heroes");
		List<Hero> heroes = heroService.getHeroes();
		return ResponseEntity.ok(heroes);
	}

	// Obtener un heroe mediante su id (http://localhost:8080/toh-api/heroes/1) Se envía un Integer mediante la solicitud GET
	@GetMapping("{id}")
	@ApiOperation(value = "Busca un heroe por su id", response = Hero.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Heroe encontrado exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<Hero> getHero(@PathVariable Integer id) {
		log.info("Rest request buscar heroe por id: " + id);
		return ResponseEntity.ok(heroService.getHero(id));
	}

	// Consultar concurrencias de heroes (http://localhost:8080/toh-api/heroes/?name=Man) Se envía un String mediante la solicitud GET
	@GetMapping("/")
	@ApiOperation(value = "Busca concurrencias de heroes", response = Hero.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Heroes encontrados exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<List<Hero>> searchHeroes(@RequestParam("name") String term) {
		log.info("Rest request buscar heroes por query: " + term);
		List<Hero> heroes = heroService.searchHeroes(term);
		return ResponseEntity.ok(heroes);
	}

	// Actualizar un heroe existente (http://localhost:8080/toh-api/heroes) Se envía un json heroe mediante la solicitud PUT
	@PutMapping
	@ApiOperation(value = "Actualiza un heroe existente", response = Hero.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Heroe actualizado exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<Hero> updateHero(@RequestBody Hero hero) {
		log.info("Rest request actualizar heroe con ID: " + hero.getId());
		return ResponseEntity.ok(heroService.updateHero(hero));
	}

	// Agregar un nuevo heroe (http://localhost:8080/toh-api/heroes) Se envía un json heroe mediante la solicitud POST
	@PostMapping
	@ApiOperation(value = "Agrega un nuevo heroe", response = Hero.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Heroe agregado exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<Hero> addHero(@RequestBody Hero hero) {
		log.info("Rest request agregar heroe: " + hero.toString());
		return ResponseEntity.ok(heroService.addHero(hero));
	}

	// Eliminar un heroe existente (http://localhost:8080/toh-api/heroe/1) Se envía un Integer mediante la solicitud DELETE
	@DeleteMapping("{id}")
	@ApiOperation(value = "Elimina un heroe por su id", response = Hero.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Heroe eliminado exitosamente"),
			@ApiResponse(code = 400, message = "La petición es invalida"),
			@ApiResponse(code = 500, message = "Error interno al procesar la respuesta") })
	public ResponseEntity<Void> deleteHero(@PathVariable Integer id) {
		log.info("Rest request eliminar heroe por id: " + id);
		heroService.deleteHero(id);
		return ResponseEntity.noContent().build();
	}
}
