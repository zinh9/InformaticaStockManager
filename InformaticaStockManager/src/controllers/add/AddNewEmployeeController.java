package controllers.add;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import dao.AddressDAO;
import dao.ContactDAO;
import dao.EmployeeDAO;
import dao.PositionDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Address;
import models.Contact;
import models.Employee;
import models.Position;
import util.BCryptUtil;

public class AddNewEmployeeController {
	private Stage stage;
	private Employee employee;
	private Contact contact;
	private BCryptUtil crypt;
	private Position position;
	private Address address;
	private EmployeeDAO employeeDAO;
	private PositionDAO positionDAO;
	private ContactDAO contactDAO;
	private AddressDAO addressDAO;
	private String contactType;
	private String gender;
	private String contractType;
	private String positionItem;

	@FXML
	private Button add;

	@FXML
	private Button addNewContactButton;

	@FXML
	private DatePicker dateOfBirthDatePicker;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField cepField;

	@FXML
	private TextField cityField;

	@FXML
	private TextField complementField;

	@FXML
	private DatePicker contractEndDateDatePicker;

	@FXML
	private DatePicker contractStartDateDatePicker;

	@FXML
	private TextField countryField;

	@FXML
	private TextField cpfField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField firstNameField;

	@FXML
	private SplitMenuButton genderSplitMenuButton;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField neighborhoodField;

	@FXML
	private TextField numberField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField phoneField;

	@FXML
	private SplitMenuButton positionSplitMenuButton;

	@FXML
	private TextField registrationField;

	@FXML
	private TextField salaryField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField streetField;

	@FXML
	private SplitMenuButton contractTypeSplitMenuButton;

	@FXML
	private SplitMenuButton contactTypeSplitMenuButton;

	@FXML
	private Label emptyComponent;
	
	@FXML
	private void initialize() {
		employeeDAO = new EmployeeDAO();
		positionDAO = new PositionDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();
		crypt = new BCryptUtil();
		contactType = "";
		contractType = "";
		gender = "";
		positionItem = "";

		for (MenuItem item : contractTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contractType = item.getText();
				contractTypeSplitMenuButton.setText(contractType);
			});
		}

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contactType = item.getText();
				contactTypeSplitMenuButton.setText(contactType);
			});
		}

		for (MenuItem item : genderSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				gender = item.getText();
				genderSplitMenuButton.setText(gender);
			});
		}

		for (MenuItem item : positionSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				positionItem = item.getText();
				positionSplitMenuButton.setText(positionItem);
				positionCheck(positionItem);
			});
		}

	}

	private void positionCheck(String positionItem2) {
		List<Position> positions = positionDAO.getAll();

		for (Position positionCheck : positions) {
			if (positionCheck.getPositionName().equals(positionItem2)) {
				System.out.println("Achei");
				position = positionCheck;
				break;
			}
		}
	}

	@FXML
	private void addNewEmployee() {
		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || cpfField.getText().isEmpty()
				|| phoneField.getText().isEmpty() || emailField.getText().isEmpty()
				|| contactTypeSplitMenuButton.getText().isEmpty() || streetField.getText().isEmpty()
				|| numberField.getText().isEmpty() || complementField.getText().isEmpty()
				|| neighborhoodField.getText().isEmpty() || cityField.getText().isEmpty()
				|| stateField.getText().isEmpty() || countryField.getText().isEmpty() || cepField.getText().isEmpty()
				|| registrationField.getText().isEmpty() || salaryField.getText().isEmpty()
				|| passwordField.getText().isEmpty() || contractTypeSplitMenuButton.getText().isEmpty()
				|| genderSplitMenuButton.getText().isEmpty() || positionSplitMenuButton.getText().isEmpty()
				|| contractStartDateDatePicker.getValue() == null || contractEndDateDatePicker.getValue() == null
				|| dateOfBirthDatePicker.getValue() == null) {

			emptyComponent
					.setText("Algum dos componentes não foram preenchidos corretamente. Verifique e tente novamente.");
			return;
		}

		String firstName = firstNameField.getText(), lastName = lastNameField.getText(),
				registration = registrationField.getText() + "TI", cpf = cpfField.getText(),
				password = passwordField.getText();

		String passwordHash = crypt.createPasswordHash(password);

		Double salary = Double.parseDouble(salaryField.getText());

		LocalDate dateOfBirthLocalDate = dateOfBirthDatePicker.getValue(),
				crontactStartDateLocalDate = contractStartDateDatePicker.getValue(),
				contractEndDateLocalDate = contractEndDateDatePicker.getValue();

		Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthLocalDate),
				contractStartDate = java.sql.Date.valueOf(crontactStartDateLocalDate),
				contractEndDate = java.sql.Date.valueOf(contractEndDateLocalDate);

		// Inicializando as variaveis de cadastro de contato do funcionário
		String phone = phoneField.getText(), email = emailField.getText();

		// Inicializando as variaveis de cadastro de endereço do funcionário
		String street = streetField.getText(), postalCode = cepField.getText(), number = numberField.getText(),
				complement = complementField.getText(), neighborhood = neighborhoodField.getText(),
				city = cityField.getText(), state = stateField.getText(), country = countryField.getText();

		employee = createEmployee(firstName, lastName, dateOfBirth, cpf, gender, registration, passwordHash, salary,
				contractType, contractStartDate, contractEndDate, position);
		createContact(contactType, phone, email, employee);
		createAddress(street, postalCode, number, complement, neighborhood, city, state, country, employee);
		redirectEmployeeViewScreen();
	}

	private Employee createEmployee(String firstName, String lastName, Date dateOfBirth, String cpf, String gender,
			String registration, String passwordHash, Double salary, String contactType, Date contractStartDate,
			Date contractEndDate, Position position) {
		Employee employee = new Employee(firstName, lastName, dateOfBirth, cpf, gender, registration, passwordHash,
				salary, contactType, contractStartDate, contractEndDate, position);

		employeeDAO.create(employee);

		return employee;
	}

	private void createContact(String type, String phone, String email, Employee employee) {
		contact = new Contact(type, phone, email, employee);
		contactDAO.create(contact);
	}

	private void createAddress(String street, String postalCode, String number, String complement, String neighborhood,
			String city, String state, String country, Employee employee) {
		address = new Address(street, postalCode, number, complement, neighborhood, city, state, country, employee);
		addressDAO.create(address);
	}

	@FXML
	public void redirectEmployeeViewScreen() {
		redirectToViewScreen("/views/EmployeesViewScreen.fxml", cancelButton);
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
