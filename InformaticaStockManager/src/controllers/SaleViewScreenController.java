package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import controllers.show.ScreenShowingAllSaleDataController;
import dao.SaleDAO;
import models.Sale;
import models.products.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaleViewScreenController {
	private Stage stage;
	private Sale sale;
	private SaleDAO saleDAO;
	private ObservableList<Sale> listSale;

	@FXML
	private Button addNewSaleButton;

	@FXML
	private Button searchButton;

	@FXML
	private Button toGoBackButton;

	@FXML
	private TableView<Sale> saleTable;

	@FXML
	private TableColumn<Sale, Integer> idColumn;

	@FXML
	private TableColumn<Sale, String> productColumn;

	@FXML
	private TableColumn<Sale, Integer> quantityColumn;

	@FXML
	private TableColumn<Sale, Date> saleDateColumn;

	@FXML
	private TableColumn<Sale, Double> totalAmountColumn;

	@FXML
	private TableColumn<Sale, String> paymentMethodColumn;

	@FXML
	private TextField idField;

	@FXML
	public void initialize() {
		saleDAO = new SaleDAO();
		listSale = FXCollections.observableArrayList();

		List<Sale> sales = saleDAO.getAll();
		listSale.addAll(sales);
		saleTable.setItems(listSale);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("idSale"));
		productColumn.setCellValueFactory(cellData -> {
			Product product = cellData.getValue().getProduct();

			if (product != null) {
				return new SimpleStringProperty(product.getName());
			} else {
				return new SimpleStringProperty("");
			}
		});
		saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
		totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
	}

	@FXML
	private void redirectScreenShowingAllSaleData() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/show/ScreenShowingAllSaleData.fxml"));
			VBox root = loader.load();

			String idSale = idField.getText();

			if (!idSale.isEmpty()) {
				Integer id = Integer.parseInt(idSale);
				sale = saleDAO.getSaleWithDetails(id);
			}

			ScreenShowingAllSaleDataController ssasdc = loader.getController();
			ssasdc.initialize(sale);

			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void redirectAddNewSaleScreen() {
		redirectToViewScreen("/views/add/AddNewSaleScreen.fxml", addNewSaleButton);
	}

	@FXML
	public void redirectHomeScreen() {
		redirectToViewScreen("/views/HomeScreen.fxml", toGoBackButton);
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
}
