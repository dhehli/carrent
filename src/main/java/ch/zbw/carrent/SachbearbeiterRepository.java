package ch.zbw.carrent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface SachbearbeiterRepository extends JpaRepository<Sachbearbeiter, Long>
{
	Sachbearbeiter findById(int sachbearbeiterNr);
	Sachbearbeiter findByName(String name);
}
