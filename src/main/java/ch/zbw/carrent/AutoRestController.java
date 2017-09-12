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
@RequestMapping("auto")
public class AutoRestController {

	private AutoService autoService;

	@Autowired 
	private AutoRepository autoRep;
	private KlasseRepository klasseRep;
	
	public AutoRestController(AutoRepository autoRep, KlasseRepository klasseRep) 
	{
		this.autoRep = autoRep;
		this.klasseRep = klasseRep;
	}
	@GetMapping("/autos")
	public Iterable<Auto> list() {
		return autoRep.findAll();
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Auto getAuto(@PathVariable int id) {
		return this.autoRep.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	ResponseEntity<?> add(@RequestBody Auto input, @PathVariable("id") int id) {			
		Klasse klasse = klasseRep.findById(id);
		Auto result = autoRep.save(new Auto(input.getMarke(), input.getTyp(), klasse));
	
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(result.getId()).toUri();
	
		return ResponseEntity.created(location).build();
	

	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, HttpServletResponse response) {
	    
	    Auto auto = autoRep.findById(id);
	    // true -> can delete
	    // false -> cannot delete, f.e. is FK reference somewhere
	    autoRep.delete(auto); 

	  /*  if (!wasOk) {
	        // will write to user which item couldn't be deleted
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        model.addAttribute("item", item);
	        return "items/error";   
	    }*/

	    return "redirect:/autos";
	}



	
}
