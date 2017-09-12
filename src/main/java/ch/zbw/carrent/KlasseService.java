package ch.zbw.carrent;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class KlasseService {
	private KlasseRepository klasseRep;
	public KlasseService(KlasseRepository klasseRep) 
	{
		this.klasseRep = klasseRep;
	}
	public Iterable<Klasse> list()
	{
		return klasseRep.findAll();
	}
	public Klasse save(Klasse klasse) 
	{
		return klasseRep.save(klasse);
	}
	public Iterable<Klasse> save(List<Klasse> klasse)
	{
		return klasseRep.save(klasse);
	}
}
