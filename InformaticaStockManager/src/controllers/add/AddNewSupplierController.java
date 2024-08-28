package controllers.add;

import java.io.IOException;

import dao.AddressDAO;
import dao.ContactDAO;
import dao.SupplierDAO;
import models.Address;
import models.Contact;
import models.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

public class AddNewSupplierController {
	private Stage stage;
	private Supplier supplier;
	private SupplierDAO supplierDAO;
	private Contact contact;
	private ContactDAO contactDAO;
	private Address address;
	private AddressDAO addressDAO;
	private String contactType;

	@FXML
	private Button addNewContactButton;

	@FXML
	private Button addNewSupplierButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label emptyComponent;

	@FXML
	private TextField contactNameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField phoneField;

	@FXML
	private TextField registrationField;

	@FXML
	private TextArea notesArea;

	@FXML
	private TextField streetField;

	@FXML
	private TextField complementField;

	@FXML
	private TextField cepField;

	@FXML
	private TextField numberField;

	@FXML
	private TextField neighborhoodField;

	@FXML
	private TextField cityField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField countryField;

	@FXML
	private SplitMenuButton contactTypeSplitMenuButton;

	@FXML
	private void initialize() {
		supplierDAO = new SupplierDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();
		contactType = "";

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contactType = item.getText();
				contactTypeSplitMenuButton.setText(contactType);
			});
		}
	}

	@FXML
	private void addNewSupplier() {
		if (contactNameField.getText().isEmpty() || emailField.getText().isEmpty() || nameField.getText().isEmpty()
				|| phoneField.getText().isEmpty() || registrationField.getText().isEmpty()
				|| notesArea.getText().isEmpty() || streetField.getText().isEmpty()
				|| complementField.getText().isEmpty() || cepField.getText().isEmpty()
				|| numberField.getText().isEmpty() || neighborhoodField.getText().isEmpty()
				|| cityField.getText().isEmpty() || stateField.getText().isEmpty() || countryField.getText().isEmpty()
				|| contactTypeSplitMenuButton.getText().isEmpty()) {

			emptyComponent
					.setText("Algum dos componentes não foram preenchidos corretamente. Verifique e tente novamente.");
			return;
		}

		String name = nameField.getText(), contactName = contactNameField.getText(),
				registration = registrationField.getText(), notes = notesArea.getText(), phone = phoneField.getText(),
				email = emailField.getText(), street = streetField.getText(), complement = complementField.getText(),
				postalCode = cepField.getText(), number = numberField.getText(),
				neighborhood = neighborhoodField.getText(), city = cityField.getText(), state = stateField.getText(),
				country = countryField.getText();

		supplier = createSupplier(name, contactName, registration, notes);
		createContact(contactType, phone, email, supplier);
		createAddress(street, complement, postalCode, number, neighborhood, city, state, country, supplier);

		redirectSupplierViewScreen();
	}

	private void createAddress(String street, String complement, String postalCode, String number, String neighborhood,
			String city, String state, String country, Supplier supplier2) {
		address = new Address(street, postalCode, number, complement, neighborhood, city, state, country, supplier2);
		addressDAO.create(address);
	}

	private void createContact(String type, String phone, String email, Supplier supplier2) {
		contact = new Contact(type, phone, email, supplier2);
		contactDAO.create(contact);
	}

	private Supplier createSupplier(String name, String contactName, String registration, String notes) {
		Supplier createSupplier = new Supplier(name, contactName, registration, notes);
		supplierDAO.create(createSupplier);
		return createSupplier;
	}

	@FXML
	public void redirectSupplierViewScreen() {
		redirectToViewScreen("/views/SupplierViewScreen.fxml", cancelButton);
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
