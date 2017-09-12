package ch.zbw.carrent;

import java.net.URI;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("klasse")
public class KlasseRestController {
	
	private KlasseService klasseService;
	
	@Autowired 
	private KlasseRepository klasseRep;
	
	public KlasseRestController(KlasseRepository klasseRep) 
	{
		this.klasseRep = klasseRep;
	}
	@GetMapping("/klassen")
	public Iterable<Klasse> list() {
		return klasseRep.findAll();
	}
	
/*	@RequestMapping(method = RequestMethod.GET, value = "/{klassenId}")
	Collection<Auto> getByKlasse(@PathVariable int klassenId) {
		return this.klasseRep.findById(klassenId).getAutos();
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Klasse getKlasse(@PathVariable int id) {
		return this.klasseRep.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Klasse input) {
					
					Klasse result = klasseRep.save(new Klasse(input.getKlassenName(), input.getTagesgebuehr()));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, HttpServletResponse response) {
	    
	    Klasse klasse = klasseRep.findById(id);
	    // true -> can delete
	    // false -> cannot delete, f.e. is FK reference somewhere
	    klasseRep.delete(klasse); 

	  /*  if (!wasOk) {
	        // will write to user which item couldn't be deleted
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        model.addAttribute("item", item);
	        return "items/error";   
	    }*/

	    return "redirect:/klassen";
	}


	

}