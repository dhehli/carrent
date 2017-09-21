package ch.zbw.carrent;

import java.net.URI;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("sachbearbeiter")
public class SachbearbeiterRestController {
	
@Autowired 
private SachbearbeiterRepository sachRep;
@Autowired 
private KundeRepository kundeRep;
	
	public SachbearbeiterRestController(SachbearbeiterRepository sachRep, KundeRepository kundeRep) 
	{
		this.sachRep = sachRep;
		this.kundeRep = kundeRep;
	}
	@GetMapping("/sachbearbeiterList")
	public Iterable<Sachbearbeiter> list() {
		return sachRep.findAll();
	}
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Sachbearbeiter input) {
				Sachbearbeiter result = sachRep.save(new Sachbearbeiter(input.getName(), input.getVorName()));
					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{sachbearbeiterNr}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	ResponseEntity<?> edit(@RequestBody Sachbearbeiter input, @PathVariable("id") int id) {
					
					Sachbearbeiter result = sachRep.findById(input.getId());
					result.setName(input.getName());
					result.setVorName(input.getVorName());
					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, HttpServletResponse response) {
	    
	    Sachbearbeiter sach = sachRep.findById(id);
	    // true -> can delete
	    // false -> cannot delete, f.e. is FK reference somewhere
	    sachRep.delete(sach); 

	  /*  if (!wasOk) {
	        // will write to user which item couldn't be deleted
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        model.addAttribute("item", item);
	        return "items/error";   
	    }*/

	    return "redirect:/sachbearbeiterList";
	}


	


}
