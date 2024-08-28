package controllers.update;

import controllers.AuthorizationAdminController;
import dao.AddressDAO;
import dao.ContactDAO;
import dao.SupplierDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Address;
import models.Contact;
import models.Supplier;

public class FormToUpdateSupplierDataController {
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
	private Button cancelButton;

	@FXML
	private TextField cepField;

	@FXML
	private TextField cityField;

	@FXML
	private TextField complementField;

	@FXML
	private TextField contactNameField;

	@FXML
	private SplitMenuButton contactTypeSplitMenuButton;

	@FXML
	private TextField countryField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField neighborhoodField;

	@FXML
	private TextArea notesArea;

	@FXML
	private TextField numberField;

	@FXML
	private TextField phoneField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField streetField;

	@FXML
	private TextField supplierNameField;

	@FXML
	private Button updateSupplierButton;

	public void initialize(Supplier redirectSupplier) {
		supplier = redirectSupplier;

		supplierDAO = new SupplierDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();

		contactType = "";

		supplierNameField
				.setText(supplier.getName() != null && !supplier.getName().isEmpty() ? supplier.getName() : "");
		contactNameField.setText(
				supplier.getContactName() != null && !supplier.getContactName().isEmpty() ? supplier.getContactName()
						: "");
		notesArea.setText(supplier.getNotes() != null && !supplier.getNotes().isEmpty() ? supplier.getNotes() : "");

		if (supplier.getContacts() != null && !supplier.getContacts().isEmpty()) {
			Contact contact = supplier.getContacts().iterator().next();
			contactTypeSplitMenuButton
					.setText(contact.getType() != null && !contact.getType().isEmpty() ? contact.getType() : "");
			phoneField.setText(contact.getPhone() != null && !contact.getPhone().isEmpty() ? contact.getPhone() : "");
			emailField.setText(contact.getEmail() != null && !contact.getEmail().isEmpty() ? contact.getEmail() : "");
		} else {
			contactTypeSplitMenuButton.setText("");
			phoneField.setText("");
			emailField.setText("");
		}

		if (supplier.getAddresses() != null && !supplier.getAddresses().isEmpty()) {
			Address address = supplier.getAddresses().iterator().next();
			streetField
					.setText(address.getStreet() != null && !address.getStreet().isEmpty() ? address.getStreet() : "");
			complementField.setText(
					address.getComplement() != null && !address.getComplement().isEmpty() ? address.getComplement()
							: "");
			cepField.setText(
					address.getPostalCode() != null && !address.getPostalCode().isEmpty() ? address.getPostalCode()
							: "");
			numberField
					.setText(address.getNumber() != null && !address.getNumber().isEmpty() ? address.getNumber() : "");
			neighborhoodField.setText(address.getNeighborhood() != null && !address.getNeighborhood().isEmpty()
					? address.getNeighborhood()
					: "");
			cityField.setText(address.getCity() != null && !address.getCity().isEmpty() ? address.getCity() : "");
			stateField.setText(address.getState() != null && !address.getState().isEmpty() ? address.getState() : "");
			countryField.setText(
					address.getCountry() != null && !address.getCountry().isEmpty() ? address.getCountry() : "");
		} else {
			streetField.setText("");
			complementField.setText("");
			cepField.setText("");
			numberField.setText("");
			neighborhoodField.setText("");
			cityField.setText("");
			stateField.setText("");
			countryField.setText("");
		}

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contactType = item.getText();
				contactTypeSplitMenuButton.setText(contactType);
			});
		}
	}

	private void setSupplier(String supplierName, String contactName, String notes) {
		supplier.setName(supplierName);
		supplier.setContactName(contactName);
		supplier.setNotes(notes);
	}

	private void setContact(String contactType2, String phone, String email) {
		if (!supplier.getContacts().isEmpty()) {
			for (Contact contact : supplier.getContacts()) {
				contact.setType(contactType2);
				contact.setPhone(phone);
				contact.setEmail(email);
			}
		} else {
			contact = new Contact(contactType2, phone, email, supplier);

			contactDAO.create(contact);
		}
	}

	private void setAddress(String street, String complement, String postalCode, String number, String neighborhood,
			String city, String state, String country) {
		if (!supplier.getAddresses().isEmpty()) {
			for (Address address : supplier.getAddresses()) {
				address.setStreet(street);
				address.setComplement(complement);
				address.setPostalCode(postalCode);
				address.setNumber(number);
				address.setNeighborhood(neighborhood);
				address.setCity(city);
				address.setState(state);
				address.setCountry(country);
			}

		} else {
			address = new Address(street, postalCode, number, complement, neighborhood, city, state, country, supplier);

			addressDAO.create(address);
		}
	}

	private boolean authorizationAdmin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
			VBox root = loader.load();

			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.showAndWait();

			AuthorizationAdminController authController = loader.getController();

			if (authController.isAdminAuthorized()) {
				closeScreen();
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@FXML
	private void updateCustomer() {
		String supplierName = supplierNameField.getText(), contactName = contactNameField.getText(),
				notes = notesArea.getText(), phone = phoneField.getText(), email = emailField.getText(),
				street = streetField.getText(), complement = complementField.getText(), postalCode = cepField.getText(),
				number = numberField.getText(), neighborhood = neighborhoodField.getText(), city = cityField.getText(),
				state = stateField.getText(), country = countryField.getText();

		if (authorizationAdmin()) {
			setSupplier(supplierName, contactName, notes);
			setContact(contactType, phone, email);
			setAddress(street, complement, postalCode, number, neighborhood, city, state, country);

			supplierDAO.update(supplier);

			closeScreen();
		}

	}

	@FXML
	private void closeScreen() {
		stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
