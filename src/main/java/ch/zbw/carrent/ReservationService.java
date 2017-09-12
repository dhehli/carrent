package ch.zbw.carrent;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	private ReservationRepository resRep;
	public ReservationService(ReservationRepository resRep) 
	{
		this.resRep = resRep;
	}
	public Iterable<Reservation> list()
	{
		return resRep.findAll();
	}
	public Reservation save(Reservation res) 
	{
		return resRep.save(res);
	}
	public Iterable<Reservation> save(List<Reservation> res)
	{
		return resRep.save(res);
	}
}
