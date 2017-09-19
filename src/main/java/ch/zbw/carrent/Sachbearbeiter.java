package ch.zbw.carrent;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sachbearbeiter {

	/*@OneToMany(mappedBy = "sachBearbeiter")
    private Set<Kunde> kunden = new HashSet<>();*/
	
	@Id @GeneratedValue
	private int id;
	private String name;
	private String vorName;
	public Sachbearbeiter(String name, String vorName) {
		super();
		this.name = name;
		this.vorName = vorName;
	}
	
	public Sachbearbeiter() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorName() {
		return vorName;
	}
	public void setVorName(String vorName) {
		this.vorName = vorName;
	}

	/*public Set<Kunde> getKunden() {
		return kunden;
	}

	public void setKunden(Set<Kunde> kunden) {
		this.kunden = kunden;
	}*/
	
}
