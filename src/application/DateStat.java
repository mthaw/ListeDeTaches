//Cette classe est le controlleur qui s'occupe des statistiques. Il héberge la logique pour creer des graphiques statistiques et communique et met a jour le modele et le view

//Cette classe permet aussi de creer des objets de type DateStat. Les objets dateStat representent des graphiques statistiques. 
package application;

//Imports necessaires
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class DateStat {
	@FXML
	private BarChart<String, Integer> barChart;// graphique en barres qui montrera les statistiques

	@FXML
	private CategoryAxis xAxis;// l'axe des categories (axe des x / d'abscisses) de la graphique en barres

	private ObservableList<String> dates = FXCollections.observableArrayList();// Un ObservbleList pour toutes les
																				// categories / dates / etiquettes pour
																				// l'axe des x

	/**
	 * Methode qui initialize les differents elements du graphique en barres
	 */
	@FXML
	private void initialize() {
		// Ajouter les categories: aujourd'hui, demain, apres demain, ... 6 jours apres
		// aujourd'hui, plus que 6 jours apres aujourd'hui au ObservableList "dates"
		dates.add(LocalDate.now().toString());
		dates.add(LocalDate.now().plusDays(1).toString());
		dates.add(LocalDate.now().plusDays(2).toString());
		dates.add(LocalDate.now().plusDays(3).toString());
		dates.add(LocalDate.now().plusDays(4).toString());
		dates.add(LocalDate.now().plusDays(5).toString());
		dates.add(LocalDate.now().plusDays(6).toString());
		dates.add("Delai apres " + LocalDate.now().plusDays(6).toString());

		// Attribuer toutes les categories dans "dates", a l'axe des x, "xAxis"
		xAxis.setCategories(dates);
	}

	/**
	 * Cette methode utilise la liste de taches pour generer les statistiques, et
	 * ainsi, le graphique en barres
	 * 
	 * @param taches
	 */
	public void setStats(List<Tache> taches) {
		// delaiCounter compte la frequence (c-ad nombre de taches dues) dans chaque
		// categorie (0 = aujourd'hui, 1 = demain, ... 7 = plus que 6 jours apres
		// demain)
		int delaiCounter[] = new int[8];

		// Pour chaque tache dans la liste de tache, modifier delaiCounter pour en tenir
		// compte de la tache
		for (Tache t : taches) {
			LocalDate date = t.getDateLimite();// Attribuer la date limite de la tache dans un objet LocalDate, "date"

			// Verifier chaque categorie; si la tache appartient a la categorie, incrementer
			// la frequence / nombres de taches dues dans cette categorie, par 1
			if (date.isEqual(LocalDate.now()))
				delaiCounter[0]++;
			if (date.isEqual(LocalDate.now().plusDays(1)))
				delaiCounter[1]++;
			if (date.isEqual(LocalDate.now().plusDays(2)))
				delaiCounter[2]++;
			if (date.isEqual(LocalDate.now().plusDays(3)))
				delaiCounter[3]++;
			if (date.isEqual(LocalDate.now().plusDays(4)))
				delaiCounter[4]++;
			if (date.isEqual(LocalDate.now().plusDays(5)))
				delaiCounter[5]++;
			if (date.isEqual(LocalDate.now().plusDays(6)))
				delaiCounter[6]++;
			if (date.isAfter(LocalDate.now().plusDays(6)))
				delaiCounter[7]++;
		}

		// Creer un objet XYChart (axe des x a des categories de type String, axe des y
		// a des frequences de type Integer), qui permettra d'organizer les donnees pour
		// le barChart.
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Nombre de taches");// Mettre le nom de "series"

		// Pour chaque tache, l'ajouter au data utilise pour construire le XYChart,
		// "series"
		for (int i = 0; i < delaiCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(dates.get(i), delaiCounter[i]));
		}

		// Mettre le data dans le barChart.
		barChart.getData().add(series);

	}

}
