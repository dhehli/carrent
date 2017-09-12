package ch.zbw.carrent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KundeRepository extends JpaRepository<Kunde, Long>
{
	Kunde findByKundenId(int id);

	Kunde findByName(String string);
}
