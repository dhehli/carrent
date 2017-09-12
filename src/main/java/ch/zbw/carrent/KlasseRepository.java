package ch.zbw.carrent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KlasseRepository extends JpaRepository<Klasse, Long> {
	Klasse findById(int id);

	Klasse findByKlassenName(String string);
}
