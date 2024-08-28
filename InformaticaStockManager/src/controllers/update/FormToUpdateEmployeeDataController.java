package controllers.update;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import controllers.AuthorizationAdminController;
import dao.AddressDAO;
import dao.ContactDAO;
import dao.EmployeeDAO;
import dao.PositionDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

public class FormToUpdateEmployeeDataController {
	private Stage stage;
	private Employee employee;
	private EmployeeDAO employeeDAO;
	private Position position;
	private PositionDAO positionDAO;
	private Contact contact;
	private ContactDAO contactDAO;
	private Address address;
	private AddressDAO addressDAO;
	private BCryptUtil crypt;

	private String contractType;
	private String contactType;
	private String positionItem;

	@FXML
	private Button updateEmployeeButton;

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
	private DatePicker contractEndDateDatePicker;

	@FXML
	private DatePicker dateOfBirthDatePicker;

	@FXML
	private SplitMenuButton contractTypeSplitMenuButton;

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
	private PasswordField passwordField;

	@FXML
	private TextField phoneField;

	@FXML
	private SplitMenuButton positionSplitMenuButton;

	@FXML
	private TextField salaryField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField streetField;

	public void initialize(Employee redirectEmployee) {
		employeeDAO = new EmployeeDAO();
		positionDAO = new PositionDAO();
		contactDAO = new ContactDAO();
		addressDAO = new AddressDAO();
		crypt = new BCryptUtil();

		contractType = "";
		contactType = "";
		positionItem = "";

		employee = redirectEmployee;

		firstNameField.setText(!employee.getFirstName().isEmpty() ? employee.getFirstName() : "");
		lastNameField.setText(!employee.getLastName().isEmpty() ? employee.getLastName() : "");

		String salary = String.valueOf(employee.getSalary());
		salaryField.setText(employee.getSalary() > 0 ? salary : "");

		if (employee.getContractType() != null && !employee.getContractType().isEmpty()) {
			contractTypeSplitMenuButton.setText(employee.getContractType());
			contractType = employee.getContractType();

		} else {
			contractTypeSplitMenuButton.setText("Tipo de Contrato");
		}

		Date dateOfBirthDate = employee.getDateOfBith();
		if (dateOfBirthDate != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateOfBirthDate.getTime());
			LocalDate dateOfBirth = sqlDate.toLocalDate();
			dateOfBirthDatePicker.setValue(dateOfBirth);
		} else {
			dateOfBirthDatePicker.setValue(null);
		}

		Date contractEndDate = employee.getContractEndDate();
		if (contractEndDate != null) {
			java.sql.Date sqlDate = new java.sql.Date(contractEndDate.getTime());
			LocalDate contractEndDateLocal = sqlDate.toLocalDate();
			contractEndDateDatePicker.setValue(contractEndDateLocal);
		} else {
			contractEndDateDatePicker.setValue(null);
		}

		if (employee.getPosition() != null && !employee.getPosition().getPositionName().isEmpty()) {
			positionSplitMenuButton.setText(employee.getPosition().getPositionName());
			positionItem = employee.getPosition().getPositionName();
			positionCheck(positionItem);
		} else {
			positionSplitMenuButton.setText("Cargo");
		}

		if (employee.getContacts() != null && !employee.getContacts().iterator().next().getType().isEmpty()) {
			contactTypeSplitMenuButton.setText(employee.getContacts().iterator().next().getType());
			contactType = employee.getContacts().iterator().next().getType();
		} else {
			contactTypeSplitMenuButton.setText("Tipo de Contato");
		}

		phoneField
				.setText(!employee.getContacts().isEmpty() ? employee.getContacts().iterator().next().getPhone() : "");
		emailField
				.setText(!employee.getContacts().isEmpty() ? employee.getContacts().iterator().next().getEmail() : "");

		streetField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getStreet() : "");
		complementField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getComplement() : "");
		cepField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getPostalCode() : "");
		numberField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getNumber() : "");
		neighborhoodField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getNeighborhood() : "");
		cityField
				.setText(!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getCity() : "");
		stateField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getState() : "");
		countryField.setText(
				!employee.getAddresses().isEmpty() ? employee.getAddresses().iterator().next().getCountry() : "");

		for (MenuItem item : contractTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contractType = item.getText();
				System.out.println("Achei");
				contractTypeSplitMenuButton.setText(contractType);
			});
		}

		for (MenuItem item : contactTypeSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				contactType = item.getText();
				System.out.println("Achei");
				contactTypeSplitMenuButton.setText(contactType);
			});
		}

		for (MenuItem item : positionSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				positionItem = item.getText();
				System.out.println("Achei");
				positionSplitMenuButton.setText(positionItem);
				positionCheck(positionItem);
			});
		}
	}

	@FXML
	private void updateEmployee() {
		String firstName = firstNameField.getText(), lastName = lastNameField.getText(), phone = phoneField.getText(),
				email = emailField.getText(), password = passwordField.getText(), street = streetField.getText(),
				complement = complementField.getText(), postalCode = cepField.getText(), number = numberField.getText(),
				neighborhood = neighborhoodField.getText(), city = cityField.getText(), state = stateField.getText(),
				country = countryField.getText();

		Double salary = Double.parseDouble(salaryField.getText());

		LocalDate dateOfBirthLocal = dateOfBirthDatePicker.getValue(),
				contractEndDateLocal = contractEndDateDatePicker.getValue();
		Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthLocal),
				contractEndDate = java.sql.Date.valueOf(contractEndDateLocal);

		if (authorizationAdmin()) {
			setEmployee(firstName, lastName, password, dateOfBirth, salary, contractType, contractEndDate, position);
			setContact(contactType, phone, email);
			setAddress(street, complement, postalCode, number, neighborhood, city, state, country);

			employeeDAO.update(employee);

			closeScreen();
		}
	}

	private void setEmployee(String firstName, String lastName, String password, Date dateOfBirth, Double salary,
			String contractType2, Date contractEndDate, Position position2) {
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setDateOfBith(dateOfBirth);
		employee.setSalary(salary);
		employee.setContractType(contractType2);
		employee.setContractEndDate(contractEndDate);
		employee.setPosition(position2);

		if (!password.equals("")) {
			password = crypt.createPasswordHash(password);
			employee.setPassword(password);
		}
	}

	private void setContact(String contactType2, String phone, String email) {
		if (!employee.getContacts().isEmpty()) {
			for (Contact contact : employee.getContacts()) {
				contact.setType(contactType2);
				contact.setPhone(phone);
				contact.setEmail(email);
			}

		} else {
			contact = new Contact(contactType2, phone, email, employee);

			contactDAO.create(contact);
		}
	}

	private void setAddress(String street, String complement, String postalCode, String number, String neighborhood,
			String city, String state, String country) {
		if (!employee.getAddresses().isEmpty()) {
			for (Address address : employee.getAddresses()) {
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
			address = new Address(street, postalCode, number, complement, neighborhood, city, state, country, employee);

			addressDAO.create(address);
		}
	}

	private void positionCheck(String positionCHeck) {
		List<Position> positions = positionDAO.getAll();

		for (Position positionFor : positions) {
			if (positionFor.getPositionName().equals(positionCHeck)) {
				position = positionFor;
				break;
			}
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
	private void closeScreen() {
		stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
