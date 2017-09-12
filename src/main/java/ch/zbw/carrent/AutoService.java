package ch.zbw.carrent;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AutoService {
	private AutoRepository autoRep;
	public AutoService(AutoRepository autoRep) 
	{
		this.autoRep = autoRep;
	}
	public Iterable<Auto> list()
	{
		return autoRep.findAll();
	}
	public Auto save(Auto auto) 
	{
		return autoRep.save(auto);
	}
	public Iterable<Auto> save(List<Auto> auto)
	{
		return autoRep.save(auto);
	}
	public void delete(Auto auto) 
	{
		autoRep.delete(auto);
	}

}
