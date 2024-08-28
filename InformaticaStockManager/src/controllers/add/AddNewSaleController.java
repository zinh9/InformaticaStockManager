package controllers.add;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.SaleDAO;
import dao.products.ProductDAO;
import models.Customer;
import models.Employee;
import models.Sale;
import models.products.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddNewSaleController {
	private Stage stage;
	private Sale sale;
	private SaleDAO saleDAO;
	private Product product;
	private ProductDAO productDAO;
	private Employee employee;
	private EmployeeDAO employeeDAO;
	private Customer customer;
	private CustomerDAO customerDAO;

	private String paymentMethod;

	@FXML
	private Button addNewSaleButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label emptyComponent;

	@FXML
	private TextField cpfCustomerField;

	@FXML
	private SplitMenuButton paymentMehtodSplitMenuButton;

	@FXML
	private TextField productField;

	@FXML
	private TextField quantityField;

	@FXML
	private TextField registrationEmployeeField;

	@FXML
	private DatePicker saleDateDatePicker;

	@FXML
	private void initialize() {
		saleDAO = new SaleDAO();
		productDAO = new ProductDAO();
		customerDAO = new CustomerDAO();
		employeeDAO = new EmployeeDAO();

		for (MenuItem item : paymentMehtodSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				paymentMethod = item.getText();
				paymentMehtodSplitMenuButton.setText(paymentMethod);
			});
		}
	}

	@FXML
	private void addNewSale() {
		if (cpfCustomerField.getText().isEmpty() || paymentMehtodSplitMenuButton.getText().isEmpty()
				|| productField.getText().isEmpty() || quantityField.getText().isEmpty()
				|| registrationEmployeeField.getText().isEmpty() || saleDateDatePicker.getValue() == null) {

			emptyComponent
					.setText("Algum dos componentes não foram preenchidos corretamente. Verifique e tente novamente.");
			return;
		}

		String productName = productField.getText(), registrationEmployee = registrationEmployeeField.getText(),
				cpf = cpfCustomerField.getText();

		Integer quantity = Integer.parseInt(quantityField.getText());

		LocalDate saleLocalDate = saleDateDatePicker.getValue();
		Date saleDate = java.sql.Date.valueOf(saleLocalDate);

		customer = createCustomer(cpf);
		employee = createEmployee(registrationEmployee);
		product = createProduct(productName);

		Double totalAmount = 0.0;

		if (product != null) {
			totalAmount = quantity * product.getPrice();
		}

		createSale(product, quantity, totalAmount, saleDate, paymentMethod, customer, employee);

		redirectSaleViewScreen();
	}

	private void createSale(Product product2, Integer quantity, Double totalAmount, Date saleDate,
			String paymentMethod2, Customer customer2, Employee employee2) {
		sale = new Sale(product2, saleDate, totalAmount, quantity, paymentMethod2, customer2, employee2);
		saleDAO.create(sale);

		setProduct(product2, quantity);
	}

	private void setProduct(Product product2, Integer quantity) {
		Integer newQuantity = product.getQuantityInStock() - quantity;
		product2.setQuantityInStock(newQuantity);
		productDAO.update(product2);
	}

	private Product createProduct(String productName) {
		return productDAO.getProductByName(productName);
	}

	private Employee createEmployee(String registrationEmployee) {
		return employeeDAO.getEmployeeByRegistration(registrationEmployee);
	}

	private Customer createCustomer(String cpf) {
		return customerDAO.getCustomerByCpf(cpf);
	}

	@FXML
	public void redirectSaleViewScreen() {
		redirectToViewScreen("/views/SaleViewScreen.fxml", cancelButton);
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
