//Cette classe permet de faire la sauvegarde des fichiers sous forme XML.
package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "taches") //La racine dans notre fichier XML est "taches"
public class TacheListWrapper {
	private List<Tache> taches;

	
	//Getters et Setters pour la liste "taches":
	
	@XmlElement(name = "tache")//Les elements dans le fichier XML sont nommes "tache"
	public List<Tache> getTaches() {
		return taches;
	}

	public void setTaches(List<Tache> taches) {
		this.taches = taches;
	}
}
