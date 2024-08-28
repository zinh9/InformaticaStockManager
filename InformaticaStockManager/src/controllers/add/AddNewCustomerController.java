package controllers.add;

import java.io.IOException;

import dao.AddressDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import models.Address;
import models.Contact;
import models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Classe de controle para adicionar um novo registro de cliente com seus dados
public class AddNewCustomerController {
	private Stage stage;
	private Customer customer;
	private Contact contact;
	private Address address;
	private CustomerDAO customerDAO;
	private ContactDAO contactDAO;
	private AddressDAO addressDAO;
	private String type;

	@FXML
	private Button addNewContactButton;

	@FXML
	private Button addNewCustomerButton;

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
	private TextField cpfField;

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
	private Label emptyComponent;
	
	// Método para inicializar a classe, os atributos de DAO e de recuperação de um valor dentro de SplitMenuButton
	@FXML
	private void initialize() {
		customerDAO = new CustomerDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();
		type = "";

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				type = item.getText();
				contactTypeSplitMenuButton.setText(type);
			});
		}
	}
	
	/*
	 *  Método que vai armazenar todos os dados passados em um formulário, e armazenar em cada variavel, para deipois 
	 *  instânciar os devidos objetos para armazena-los no banco de dados
	 */
	@FXML
	private void addNewCustomer() {
		System.out.println("Achei Aqui!");
		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || cpfField.getText().isEmpty()
				|| phoneField.getText().isEmpty() || emailField.getText().isEmpty()
				|| contactTypeSplitMenuButton.getText().isEmpty() || streetField.getText().isEmpty()
				|| numberField.getText().isEmpty() || complementField.getText().isEmpty()
				|| neighborhoodField.getText().isEmpty() || cityField.getText().isEmpty()
				|| stateField.getText().isEmpty() || countryField.getText().isEmpty()
				|| cepField.getText().isEmpty()) {
			emptyComponent.setText("Algum dos componentes não foram preenchidos corretamente. Verifique e tente novamente.");
			return;
		}

		String firstName = firstNameField.getText(), lastName = lastNameField.getText(), cpf = cpfField.getText(),
				phone = phoneField.getText(), email = emailField.getText(), postalCode = cepField.getText(),
				street = streetField.getText(), number = numberField.getText(),
				neighborhood = neighborhoodField.getText(), city = cityField.getText(), state = stateField.getText(),
				country = countryField.getText(), complement = complementField.getText();

		customer = addCustomer(firstName, lastName, cpf);
		addContact(type, phone, email, customer);
		addAddress(postalCode, street, number, neighborhood, city, state, country, customer, complement);
		
		redirectCustomerViewScreen();
	}
	
	// Método que vai instânciar um objeto de cliente para armazenar no banco de dados, passando para CustomerDAO
	private Customer addCustomer(String firstName, String lastName, String cpf) {
		customer = new Customer(firstName, lastName, cpf);
		customerDAO.create(customer);
		return customer;
	}
	
	/*
	 *  Método que vai instânciar um objeto de contato para armazenar no banco de dados e que vai referenciar a entidade 
	 *  cliente, passando para ContactDAO
	 */
	private void addContact(String type, String phone, String email, Customer customer) {
		contact = new Contact(type, phone, email, customer);
		contactDAO.create(contact);
	}
	
	/*
	 *  Método que vai instânciar um objeto de endereço para armazenar no banco de dados e que vai referenciar a entidade 
	 *  cliente, passando para AddressDAO
	 */
	private void addAddress(String postalCode, String street, String number, String neighborhoodName, String cityName,
			String stateName, String countryName, Customer customer, String complement) {
		address = new Address(street, postalCode, number, complement, neighborhoodName, cityName, stateName,
				countryName, customer);
		addressDAO.create(address);
	}
	
	// Método que vai redirecionar para a tela de visualização de clientes, após armazenar o novo cliente ou cancelar o processo
	@FXML
	public void redirectCustomerViewScreen() {
		try {
			VBox root = FXMLLoader.load(getClass().getResource("/views/CustomerViewScreen.fxml"));
			
			stage = (Stage) cancelButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(false);
			stage.setMaximized(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
