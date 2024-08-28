package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import controllers.show.ScreenShowingAllSupplierDataController;
import dao.SupplierDAO;
import models.Contact;
import models.Supplier;
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

public class SupplierViewController {
	private Stage stage;
	private Supplier supplier;
	private SupplierDAO supplierDAO;
	private ObservableList<Supplier> listSuppliers;

	@FXML
	private Button addNewSupplierButton;

	@FXML
	private Button searchButton;

	@FXML
	private Button toGoBackButton;

	@FXML
	private TableView<Supplier> supplierTable;

	@FXML
	private TableColumn<Supplier, Integer> idColumn;

	@FXML
	private TableColumn<Supplier, String> nameColumn;

	@FXML
	private TableColumn<Supplier, String> contactNameColumn;

	@FXML
	private TableColumn<Supplier, String> phoneColumn;

	@FXML
	private TableColumn<Supplier, String> registrationColumn;

	@FXML
	private TableColumn<Supplier, String> notesColumn;

	@FXML
	private TextField idField;

	@FXML
	private TextField registrationField;

	@FXML
	private void initialize() {
		supplierDAO = new SupplierDAO();
		listSuppliers = FXCollections.observableArrayList();

		List<Supplier> suppliers = supplierDAO.getAll();

		listSuppliers.addAll(suppliers);

		supplierTable.setItems(listSuppliers);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("idSupplier"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
		registrationColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
		notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
		phoneColumn.setCellValueFactory(cellData -> {
			Set<Contact> contacts = cellData.getValue().getContacts();

			if (!contacts.isEmpty()) {
				return new SimpleStringProperty(contacts.iterator().next().getPhone());
			} else {
				return new SimpleStringProperty("");
			}
		});
	}

	@FXML
	private void redirectScreenShowingAllSupplierData() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/show/ScreenShowingAllSupplierData.fxml"));
			VBox root = loader.load();

			String idSupplier = idField.getText();
			String registration = registrationField.getText();

			if (!idSupplier.isEmpty()) {
				Integer id = Integer.parseInt(idSupplier);
				supplier = supplierDAO.getSupplierWithDetails(id);
			} else if (!registration.isEmpty()) {
				supplier = supplierDAO.getSupplierRegistration(registration);
				supplier = supplierDAO.getSupplierWithDetails(supplier.getIdSupplier());
			}

			ScreenShowingAllSupplierDataController ssasdc = loader.getController();
			ssasdc.initialize(supplier);

			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void redirectAddNewSupplier() {
		redirectToViewScreen("/views/add/AddNewSupplierScreen.fxml", addNewSupplierButton);
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
