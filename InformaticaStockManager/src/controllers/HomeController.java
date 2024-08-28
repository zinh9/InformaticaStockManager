package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import dao.SaleDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
	private Stage stage;
	private SaleDAO saleDAO;

	@FXML
	private Button customersButton;
	
	@FXML
	private Button suppliersButton;
	
	@FXML
	private Button employeesButton;
	
	@FXML
	private Button productsButton;
	
	@FXML
	private Button salesButton;
	
	@FXML
	private Button toGoOut;
	
	@FXML
	private BarChart<String, Number> graphicSaleDateBarChart;
	
	@FXML
	public void initialize() {
	    loadSaleDateBarChart();
	}
	
	@FXML
	public void loadSaleDateBarChart() {
		saleDAO = new SaleDAO();
		List<Object[]> salesData = saleDAO.getSalesQuantityByDate();

		ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();

		for (Object[] data : salesData) {
			java.sql.Date sqlDate = (java.sql.Date) data[0];
			LocalDate date = sqlDate.toLocalDate();
            String formattedDate = date.toString();
            Number quantity = (Number) data[1];
            chartData.add(new XYChart.Data<>(formattedDate, quantity));
		}

		XYChart.Series<String, Number> series = new XYChart.Series<>();
	    series.setName("Quantidade de Vendas por Dia");
	    series.setData(chartData);

	    graphicSaleDateBarChart.getData().clear();
	    graphicSaleDateBarChart.getData().add(series);
	}
	
	private void redirectToViewScreen(String fxmlPath, Button sourceButton) {
		try {
			VBox root = FXMLLoader.load(getClass().getResource(fxmlPath));
			
	        stage = (Stage) sourceButton.getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.setMaximized(false);
	        stage.setMaximized(true);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void redirectEmployeesViewScreen() {
		redirectToViewScreen("/views/EmployeesViewScreen.fxml", employeesButton);
	}

	@FXML
	public void redirectProductsViewScreen() {
		redirectToViewScreen("/views/ProductViewScreen.fxml", productsButton);
	}

	@FXML
	public void redirectSuppliersPreviewScreen() {
		redirectToViewScreen("/views/SupplierViewScreen.fxml", suppliersButton);
	}

	@FXML
	public void redirectCustomersViewScreen() {
		redirectToViewScreen("/views/CustomerViewScreen.fxml", customersButton);
	}

	@FXML
	public void redirectSalesScreen() {
		redirectToViewScreen("/views/SaleViewScreen.fxml", salesButton);
	}

	@FXML
	public void redirectLoginScreen() {
		try {
			StackPane root = FXMLLoader.load(getClass().getResource("/views/LoginScreen.fxml"));

			stage = (Stage) toGoOut.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
