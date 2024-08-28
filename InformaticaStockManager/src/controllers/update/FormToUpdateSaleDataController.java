package controllers.update;

import java.time.LocalDate;
import java.util.Date;

import controllers.AuthorizationAdminController;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.SaleDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;
import models.Employee;
import models.Sale;

public class FormToUpdateSaleDataController {
	private Stage stage;
	private Sale sale;
	private SaleDAO saleDAO;
	private Employee employee;
	private EmployeeDAO employeeDAO;
	private Customer customer;
	private CustomerDAO customerDAO;

	private String paymentMethod;

	@FXML
	private Button updateSaleButton;

	@FXML
	private Button cancelButton;

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

	public void initialize(Sale redirectSale) {
		sale = redirectSale;

		saleDAO = new SaleDAO();
		employeeDAO = new EmployeeDAO();
		customerDAO = new CustomerDAO();

		paymentMethod = "";

		productField.setText(
				sale.getProduct() != null && !sale.getProduct().getName().isEmpty() ? sale.getProduct().getName() : "");

		String quantity = String.valueOf(sale.getQuantity());
		quantityField.setText(sale.getQuantity() > 0 ? quantity : "");

		Date saleDateUtil = sale.getSaleDate();
		if (saleDateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(saleDateUtil.getTime());
			LocalDate saleDate = sqlDate.toLocalDate();
			saleDateDatePicker.setValue(saleDate);
		} else {
			saleDateDatePicker.setValue(null);
		}

		if (!sale.getPaymentMethod().isEmpty()) {
			paymentMehtodSplitMenuButton.setText(sale.getPaymentMethod());
			paymentMethod = sale.getPaymentMethod();
		} else {
			paymentMehtodSplitMenuButton.setText("Método de Pagamento");
		}

		cpfCustomerField.setText(
				sale.getCustomer() != null && !sale.getCustomer().getCpf().isEmpty() ? sale.getCustomer().getCpf()
						: "");

		registrationEmployeeField.setText(sale.getEmployee() != null && !sale.getEmployee().getRegistration().isEmpty()
				? sale.getEmployee().getRegistration() : "");
		
		for(MenuItem item : paymentMehtodSplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				paymentMethod = item.getText();
				paymentMehtodSplitMenuButton.setText(paymentMethod);
			});
		}
	}
	
	private void setEmployee(String registrationEmployee) {
		if(sale.getEmployee() != null) {
			sale.getEmployee().setRegistration(registrationEmployee);
		} else {
			System.out.println("O funcionário está como nulo. Vou verificar se ele existe!");
			
			employee = employeeDAO.getEmployeeByRegistration(registrationEmployee);
			
			System.out.println("Achei o: " + employee.getRegistration());
			
			if(employee != null) {
				sale.setEmployee(employee);
			}
		}
	}

	private void setCustomer(String cpfCustomer) {
		if(sale.getCustomer() != null) {
			sale.getCustomer().setCpf(cpfCustomer);
		} else {
			customer = customerDAO.getCustomerByCpf(cpfCustomer);
			
			if(customer != null) {
				sale.setCustomer(customer);
			}
		}
	}

	private void setSale(Integer quantity, Date saleDate, String paymentMethod2) {
		sale.setQuantity(quantity);
		sale.setSaleDate(saleDate);
		sale.setPaymentMethod(paymentMethod2);
	}

	private void setProduct(String product2) {
		if(sale.getProduct() != null) {
			sale.getProduct().setName(product2);
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
	private void updateSale() {
		String product = productField.getText(),
				cpfCustomer = cpfCustomerField.getText(),
				registrationEmployee = registrationEmployeeField.getText();
		
		Integer quantity = Integer.parseInt(quantityField.getText());
		
		LocalDate saleDateLocal = saleDateDatePicker.getValue();
		Date saleDate = java.sql.Date.valueOf(saleDateLocal);
		
		if(authorizationAdmin()) {
			setProduct(product);
			setSale(quantity, saleDate, paymentMethod);
			setCustomer(cpfCustomer);
			setEmployee(registrationEmployee);
			
			saleDAO.update(sale);
			
			closeScreen();
		}
	}
	
	@FXML
	private void closeScreen() {
		stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
