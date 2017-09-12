package ch.zbw.carrent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Klasse {

	@OneToMany(mappedBy = "klasse", cascade = CascadeType.REMOVE)
    private Set<Auto> autos = new HashSet<>();
	
	@Id	 @GeneratedValue
	private int id;
	private String klassenName;
	private int tagesgebuehr;
	public Klasse(String klassenName, int tagesgebuehr) {
		super();
		this.klassenName = klassenName;
		this.tagesgebuehr = tagesgebuehr;
	}
	public Klasse() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKlassenName() {
		return klassenName;
	}
	public void setKlassenName(String klassenName) {
		this.klassenName = klassenName;
	}
	public int getTagesgebuehr() {
		return tagesgebuehr;
	}
	public void setTagesgebuehr(int tagesgebuehr) {
		this.tagesgebuehr = tagesgebuehr;
	}
	public Set<Auto> getAutos() {
		return autos;
	}
}
