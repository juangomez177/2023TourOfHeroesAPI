package co.udea.api.hero.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import co.udea.api.hero.exception.BusinessException;
import co.udea.api.hero.model.Hero;
import co.udea.api.hero.repository.HeroRepository;

@Service
public class HeroService {

	private final Logger log = LoggerFactory.getLogger(HeroService.class);

	private HeroRepository heroRepository;

	public HeroService(HeroRepository heroRepository) {
		this.heroRepository = heroRepository;
	}

	// Obtener todos los heroes
	public List<Hero> getHeroes() {
		log.info("Obteniendo todos los heroes");
		
		List<Hero> heroes = heroRepository.findAll();
		if (heroes.isEmpty()) {
			log.info("No se encuentran heroes");
			throw new BusinessException("No hay heroes en la Base de Datos");
		}			
		return heroes;
	}

	// Obtener un heroe mediante su id
	public Hero getHero(Integer id) {
		log.info("Obteniendo heroe con id: " + id);
		
		Optional<Hero> optionalHero = heroRepository.findById(id);
		if (!optionalHero.isPresent()) {
			log.info("No se encuentra un heroe con ID: " + id);
			throw new BusinessException("El heroe no existe");
		}
		return optionalHero.get();
	}

	// Consultar concurrencias de heroes
	public List<Hero> searchHeroes(String term) {
		log.info("Buscando heroes relacionados con: " + term);
		
		List<Hero> heroes = heroRepository.searchHeroesByTerm(term);
		if (heroes.isEmpty()) {
			log.info("No hay una coincidencias de heroes");
			throw new BusinessException("No hay una coincidencias de heroes");
		} 
		return heroes;
	}

	// Actualizar un heroe existente
	public Hero updateHero(Hero hero) {
		log.info("Actualizando heroe: " + hero.toString());
		
		int id = hero.getId();
		Optional<Hero> optionalHero = heroRepository.findById(id);
		if (!optionalHero.isPresent()) {
			log.info("No se encuentra un heroe con ID: " + id);
			throw new BusinessException("El heroe no existe");
		}
		Hero existingHero = optionalHero.get();
		existingHero.setName(hero.getName());
		log.info("Actualizando heroe: " + existingHero.toString());
		return heroRepository.save(existingHero);
	}

	// Agregar un nuevo heroe
	public Hero addHero(Hero hero) {
		log.info("Agregando heroe: " + hero.toString());

		// Asignación automática del id respecto a los ids existentes
		List<Integer> allIds = getAllIds();
		for (int i = 1; i <= allIds.size() + 1; i++) {
			if (!allIds.contains(i)) {
				hero.setId(i);
				break;
			}
		}
		return heroRepository.save(hero);
	}

	// Eliminar un heroe existente
	public void deleteHero(Integer id) {
		log.info("Eliminando heroe con id: " + id);
		
		Optional<Hero> optionalHero = heroRepository.findById(id);
		if (!optionalHero.isPresent()) {
			log.info("No se encuentra un héroe con ID: " + id);
			throw new BusinessException("El héroe no existe");
		}
		log.info("Eliminando heroe con ID: " + optionalHero.get().getId());
		heroRepository.delete(optionalHero.get());
	}

	// Obtener todos los IDs de los heroes
	public List<Integer> getAllIds() {
		List<Hero> allHeroes = heroRepository.findAll();
		List<Integer> allIDs = new ArrayList<>();
		for (int i = 0; i < allHeroes.size(); i++) {
			allIDs.add(allHeroes.get(i).getId());
		}
		return allIDs;
	}
}
