package ch.zbw.carrent;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*")
@RequestMapping("restAPI/kunde")
@RestController
public class KundeRestController {
	
	@Autowired 
	private KundeRepository kundeRep;
	@Autowired 
	private SachbearbeiterRepository sachRep;
	
	public KundeRestController(KundeRepository kundeRep, SachbearbeiterRepository sachRep) 
	{
		this.kundeRep = kundeRep;
		this.sachRep = sachRep;
	}
	@GetMapping("")
	public Iterable<Kunde> list() {
		return kundeRep.findAll();
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Kunde getReservation(@PathVariable int id) {
		return this.kundeRep.findByKundenId(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Kunde input) {
					Sachbearbeiter sachB = sachRep.findById(input.getSachBearbeiter().getId());
					Kunde result = kundeRep.save(new Kunde(input.getName(), input.getStrasse(), input.getStrasse(),input.getPlz(), input.getOrt(),sachB));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getKundenId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	ResponseEntity<?> edit(@RequestBody Kunde input, @PathVariable("id") int id) {
					
					Kunde result = kundeRep.findByKundenId(id);
					Sachbearbeiter sachResult = sachRep.findById(input.getSachBearbeiter().getId());
					result.setName(input.getName());
					result.setOrt(input.getOrt());
					result.setPlz(input.getPlz());
					result.setStrasse(input.getStrasse());
					result.setVorname(input.getVorname());
					result.setSachBearbeiter(sachResult);
					
					kundeRep.save(result);
					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getKundenId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, HttpServletResponse response) {
	    
	    Kunde kunde = kundeRep.findByKundenId(id);
	    // true -> can delete
	    // false -> cannot delete, f.e. is FK reference somewhere
	    kundeRep.delete(kunde); 

	  /*  if (!wasOk) {
	        // will write to user which item couldn't be deleted
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        model.addAttribute("item", item);
	        return "items/error";   
	    }*/

	    return "redirect:/kunden";
	}



}