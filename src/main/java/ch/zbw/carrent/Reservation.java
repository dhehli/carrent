package ch.zbw.carrent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reservation {

	
		@JsonIgnore
	    @ManyToOne
	    private Kunde kunde;
		
		@JsonIgnore
		@ManyToOne
		private Auto auto;
		
		
		@Id	 @GeneratedValue
		private int resId;
		private int tage;
		public Reservation(int tage, Kunde kunde, Auto auto) {
			super();
			this.tage = tage;
			this.kunde = kunde;
			this.auto = auto;
		}
		public Reservation() {
			super();
		}
		public int getResId() {
			return resId;
		}
		public void setResId(int resId) {
			this.resId = resId;
		}
		public int getTage() {
			return tage;
		}
		public void setTage(int tage) {
			this.tage = tage;
		}
		public Kunde getKunde() {
			return kunde;
		}
		public void setKunde(Kunde kunde) {
			this.kunde = kunde;
		}
		public Auto getAuto() {
			return auto;
		}
		public void setAuto(Auto auto) {
			this.auto = auto;
		}
		
		
}
