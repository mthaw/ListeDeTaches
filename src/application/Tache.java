//Cette classe permet de creer des objets de type Tache. Les objets de type Tache representent des taches a completer.
package application;

//Imports necessaires

import java.time.LocalDate;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Tache {
	//Variables d'instance privees - proprietes des taches
	private int num;//La numero de tache dans le TableView dans l'onglet priorisateur
	private String nom;//Le nom de la tache
	private String description;//La description de la tache
	private int tempsRequise;//Le temps requise en minutes pour completer la tache
	private LocalDate dateLimite;//Date Limite ou la tache est due
	private int valeurSur10;//La valeur / importance de la tache sur 10

	//Constructeur sans arguments
	public Tache() {
		this(null, null);
	}

	//Constructeur avec nom et description
	public Tache(String nom, String description) {
		//Assigner le nom et description dans les arguments aux variables d'instance de nom et description
		//Assigner des valeurs par defaut aux autres variables d'instance
		this.num = 0;
		this.nom = nom;
		this.description = description;
		this.tempsRequise = 0;
		this.dateLimite = null;
		this.valeurSur10 = 0;
	}

	//Getters et Setters (public) pour tous les variables d'instance privees
	
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
	
	/*@Override
	public String toString() {
		String proc = this.getDepartement() + " - " + this.getProcedure();
		return proc;
	}*/

}
