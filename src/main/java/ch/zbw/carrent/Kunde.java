package ch.zbw.carrent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Kunde
{
	@OneToMany(mappedBy = "kunde")
    private Set<Reservation> reservationen = new HashSet<>();
	
    @ManyToOne
    private Sachbearbeiter sachBearbeiter;
	
	@Id	 @GeneratedValue
	private int kundenId;
	private String name;
	private String vorname;
	private String strasse;
	private int plz;
	private String ort;
	
	public Kunde(String name, String vorname, String strasse, int plz, String ort, Sachbearbeiter sachBearbeiter) {
		super();
		this.name = name;
		this.vorname = vorname;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.sachBearbeiter = sachBearbeiter;
	}
	public Kunde() 
	{
		
	}

	public int getKundenId() {
		return kundenId;
	}
	public void setKundenId(int kundenId) {
		this.kundenId = kundenId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public Sachbearbeiter getSachBearbeiter() {
		return sachBearbeiter;
	}
	public void setSachBearbeiter(Sachbearbeiter sachBearbeiter) {
		this.sachBearbeiter = sachBearbeiter;
	}
	
}
