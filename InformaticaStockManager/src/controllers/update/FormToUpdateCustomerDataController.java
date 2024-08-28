package controllers.update;

import controllers.AuthorizationAdminController;
import dao.AddressDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Address;
import models.Contact;
import models.Customer;

// Classe de controle para fazer a alteração de um ou mais atributos de cliente, com a permissão de um funcionário admin
public class FormToUpdateCustomerDataController {
	private Stage stage;
	private Customer customer;
	private CustomerDAO customerDAO;
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
	private SplitMenuButton contactTypeSplitMenuButton;

	@FXML
	private TextField countryField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField neighborhoodField;

	@FXML
	private TextField numberField;

	@FXML
	private TextField phoneField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField streetField;

	@FXML
	private Button updateCustomerButton;
	
	// Método que inicializa os componentes já com os dados existentes de um cliente
	public void initialize(Customer redirectCustomer) {
		customerDAO = new CustomerDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();

		this.customer = redirectCustomer;

		contactType = "";

		firstNameField.setText(!customer.getFirstName().isEmpty() ? customer.getFirstName() : "");
		lastNameField.setText(!customer.getLastName().isEmpty() ? customer.getLastName() : "");

		if (customer.getContacts() != null && !customer.getContacts().isEmpty()) {
			contactTypeSplitMenuButton.setText(customer.getContacts().iterator().next().getType());
			contactType = customer.getContacts().iterator().next().getType();
		} else {
			contactTypeSplitMenuButton.setText("Tipo de Contato");
		}

		phoneField
				.setText(!customer.getContacts().isEmpty() ? customer.getContacts().iterator().next().getPhone() : "");
		emailField
				.setText(!customer.getContacts().isEmpty() ? customer.getContacts().iterator().next().getEmail() : "");

		streetField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getStreet() : "");
		complementField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getComplement() : "");
		cepField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getComplement() : "");
		numberField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getNumber() : "");
		neighborhoodField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getNeighborhood() : "");
		cityField
				.setText(!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getCity() : "");
		stateField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getState() : "");
		countryField.setText(
				!customer.getAddresses().isEmpty() ? customer.getAddresses().iterator().next().getCountry() : "");

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contactType = item.getText();
				contactTypeSplitMenuButton.setText(contactType);
			});
		}
	}
	
	// Método que vai armazenar todos os dados passados em variaveis, para ser instânciado com cada entidade e assim poder atualizar
	@FXML
	private void updateCustomer() {
		String firstName = firstNameField.getText(), lastName = lastNameField.getText(), phone = phoneField.getText(),
				email = emailField.getText(), street = streetField.getText(), complement = complementField.getText(),
				postalCode = cepField.getText(), number = numberField.getText(),
				neighborhood = neighborhoodField.getText(), city = cityField.getText(), state = stateField.getText(),
				country = countryField.getText();
		
		if(authorizationAdmin()) {
			setCustomer(firstName, lastName);
			setContact(contactType, phone, email);
			setAddress(street, complement, postalCode, number, neighborhood, city, state, country);
			
			customerDAO.update(customer);
			
			closeScreen();
		}
	}
	
	// Método que vai alterar um atributo de endereço de um cliente se o endereço existir, se não cria um novo endereço
	private void setAddress(String street, String complement, String postalCode, String number, String neighborhood,
			String city, String state, String country) {
		if(!customer.getAddresses().isEmpty()) {
			for(Address address : customer.getAddresses()) {
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
			address = new Address(street, postalCode, number, complement, neighborhood, city, state, country, customer);
			
			addressDAO.create(address);
		}
	}
	
	// Método que vai alterar um atributo de contato de um cliente se o contato existir, se não cria um novo contato
	private void setContact(String contactType2, String phone, String email) {
		if(!customer.getContacts().isEmpty()) {
			for(Contact contact : customer.getContacts()) {
				contact.setType(contactType2);
				contact.setPhone(phone);
				contact.setEmail(email);
			}
		} else {
			contact = new Contact(contactType2, phone, email, customer);
			
			contactDAO.create(contact);
		}
	}
	
	// Método que vai alterar as informações de um cliente
	private void setCustomer(String firstName, String lastName) {
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
	}
	
	// Método que vai verificar se a alteração pode ser feita com autorização de um funcionário admin
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
	
	// Método que fecha a atual tela
	@FXML
	private void closeScreen() {
		stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}