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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("restAPI/reservation")
public class ReservationRestController {
	
	@Autowired 
	private ReservationRepository resRep;
	@Autowired 
	private KundeRepository kundeRep;
	@Autowired 
	private AutoRepository autoRep;
	
	
	public ReservationRestController(ReservationRepository resRep,KundeRepository kundeRep,AutoRepository autoRep) 
	{
		this.resRep = resRep;
		this.autoRep = autoRep;
		this.kundeRep = kundeRep;
	}
	@GetMapping("")
	public Iterable<Reservation> list() {
		return resRep.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{reservationNr}")
	public Reservation getReservation(@PathVariable int resId) {
		return this.resRep.findByResId(resId);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/{autoId}/{kundeId}")
	ResponseEntity<?> add(@RequestBody Reservation input, @PathVariable int autoId, @PathVariable int kundeId) {
					Kunde kunde = kundeRep.findByKundenId(kundeId);
					Auto auto = autoRep.findById(autoId);
					Reservation result = resRep.save(new Reservation(input.getTage(), kunde, auto));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{reservationNr}")
						.buildAndExpand(result.getResId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	
	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<?> edit(@RequestBody Reservation input) {
					
					Reservation result = resRep.findByResId(input.getResId());
					Auto autoResult = autoRep.findById(input.getAuto().getId());
					Kunde kundeResult = kundeRep.findByKundenId(input.getKunde().getKundenId());
					result.setTage(input.getTage());
					result.setAuto(autoResult);
					result.setKunde(kundeResult);
					resRep.save(result);
					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getResId()).toUri();

					return ResponseEntity.created(location).build();
	

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, HttpServletResponse response) {
	    
	    Reservation res = resRep.findByResId(id);
	    // true -> can delete
	    // false -> cannot delete, f.e. is FK reference somewhere
	    resRep.delete(res); 

	  /*  if (!wasOk) {
	        // will write to user which item couldn't be deleted
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        model.addAttribute("item", item);
	        return "items/error";   
	    }*/

	    return "redirect:/reservationen";
	}

}
