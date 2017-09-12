package ch.zbw.carrent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AutoRepository  extends JpaRepository<Auto, Long> {

	Auto findById(int autoId);

	Auto findByMarke(String string);
}
