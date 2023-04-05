package co.udea.api.hero.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.udea.api.hero.model.Hero;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {

	// Los métodos de acceso a la BD son generados por Spring-Boot, los cuales construyen los
	// recursos necesarios para operar sobre la BD, como la consulta sql, etc

	// Sin embargo el método para buscar por termino, se necesita un query personalizado:
	@Query(value = "SELECT * FROM heroes WHERE id LIKE %:query% or name LIKE %:query%", nativeQuery = true)
	List<Hero> searchHeroesByTerm(@Param("query") String term);
}
