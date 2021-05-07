package application;

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
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> dates = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		dates.add(LocalDate.now().toString());
		dates.add(LocalDate.now().plusDays(1).toString());
		dates.add(LocalDate.now().plusDays(2).toString());
		dates.add(LocalDate.now().plusDays(3).toString());
		dates.add(LocalDate.now().plusDays(4).toString());
		dates.add(LocalDate.now().plusDays(5).toString());
		dates.add(LocalDate.now().plusDays(6).toString());
		dates.add("Delai apres " + LocalDate.now().plusDays(6).toString());
		xAxis.setCategories(dates);
	}

	public void setStats(List<Tache> taches) {
		int delaiCounter[] = new int[8];
		for (Tache t : taches) {
			LocalDate date = t.getDateLimite();
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

		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Nombre de taches");

		for (int i = 0; i < delaiCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(dates.get(i), delaiCounter[i]));
		}
		
		barChart.getData().add(series);
		
	}

}
