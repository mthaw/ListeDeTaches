package application;

import java.time.LocalDate;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Tache {
	private int num;
	private String nom;
	private String description;
	private int tempsRequise;
	private LocalDate dateLimite;//Check if it should be date type or String. What does DatePicker return??? str
	private int valeurSur10;

	public Tache() {
		this(null, null);
	}

	public Tache(String nom, String description) {
		this.num = 0;
		this.nom = nom;
		this.description = description;
		this.tempsRequise = 0;
		this.dateLimite = null;
		this.valeurSur10 = 0;
	}

	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
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

	public void setTempsRequise(int tempsRequise) {
		this.tempsRequise = tempsRequise;
	}
	
	public int getTempsRequise() {
		return tempsRequise;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
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
