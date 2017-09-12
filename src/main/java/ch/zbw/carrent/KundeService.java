package ch.zbw.carrent;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class KundeService {

	private KundeRepository kundeRep;
	public KundeService(KundeRepository kundeRep) 
	{
		this.kundeRep = kundeRep;
	}
	public Iterable<Kunde> list()
	{
		return kundeRep.findAll();
	}
	public Kunde save(Kunde kunde) 
	{
		return kundeRep.save(kunde);
	}
	public Iterable<Kunde> save(List<Kunde> kunden)
	{
		return kundeRep.save(kunden);
	}
}
