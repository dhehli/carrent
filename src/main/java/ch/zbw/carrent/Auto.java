package ch.zbw.carrent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Auto {
	
	@OneToMany(mappedBy = "auto")
    private Set<Reservation> reservationen = new HashSet<>();
	
	@JsonIgnore
    @ManyToOne
    private Klasse klasse;
	

	@Id	 @GeneratedValue
	private int id;
	private String marke;
	private String typ;
	public Auto() {
		super();
	}
	public Auto(String marke, String typ, Klasse klasse) {
		super();
		this.marke = marke;
		this.typ = typ;
		this.klasse = klasse;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMarke() {
		return marke;
	}
	public void setMarke(String marke) {
		this.marke = marke;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public Klasse getKlasse() {
		return klasse;
	}
	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

}
