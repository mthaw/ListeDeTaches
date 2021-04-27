package application;

import java.time.LocalDate;
import java.util.Date;

public class Tache {
	private String nom;
	private String description;
	private LocalDate dateLimite;//Check if it should be date type or String. What does DatePicker return??? str
	private int valeurSur10;

	public Tache() {
		this(null, null);
	}

	public Tache(String nom, String description) {
		this.nom = nom;
		this.description = description;
		this.dateLimite = null;
		this.valeurSur10 = 0;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateLimite() {
		return dateLimite;
	}

	public void setDateLimite(LocalDate dateLimite) {
		this.dateLimite = dateLimite;
	}

	public int getValeurSur10() {
		return valeurSur10;
	}

	public void setValeurSur10(int valeurSur10) {
		this.valeurSur10 = valeurSur10;
	}

}
