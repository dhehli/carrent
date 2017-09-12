package ch.zbw.carrent;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SachbearbeiterService {
	private SachbearbeiterRepository sachbRep;
	public SachbearbeiterService(SachbearbeiterRepository kundeRep) 
	{
		this.sachbRep = sachbRep;
	}
	public Iterable<Sachbearbeiter> list()
	{
		return sachbRep.findAll();
	}
	public Sachbearbeiter save(Sachbearbeiter sachB) 
	{
		return sachbRep.save(sachB);
	}
	public Iterable<Sachbearbeiter> save(List<Sachbearbeiter> sachB)
	{
		return sachbRep.save(sachB);
	}
}
