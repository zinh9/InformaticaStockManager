package controllers.add;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import dao.SupplierDAO;
import dao.products.CategoryDAO;
import dao.products.ProductDAO;
import models.Supplier;
import models.products.Category;
import models.products.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddNewProductController {
	private Product product;
	private ProductDAO productDAO;
	private Supplier supplier;
	private SupplierDAO supplierDAO;
	private Category category;
	private CategoryDAO categoryDAO;
	private Stage stage;
	private String categoryCheck;

	@FXML
	private TextField productField;
	
	@FXML
	private TextField priceField;
	
	@FXML
	private TextArea descriptionField;
	
	@FXML
	private TextField quantityField;
	
	@FXML
	private TextField skuField;
	
	@FXML
	private TextField registrationField;
	
	@FXML
	private SplitMenuButton categorySplitMenuButton;
	
	@FXML
	private DatePicker createAtDatePicker;
	
	@FXML
	private Label emptyComponent;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button addNewProductButton;

	@FXML
	private void initialize() {
		productDAO = new ProductDAO();
		supplierDAO = new SupplierDAO();
		categoryDAO = new CategoryDAO();

		for (MenuItem item : categorySplitMenuButton.getItems()) {
			item.setOnAction(event -> {
				categoryCheck = item.getText();
				categorySplitMenuButton.setText(categoryCheck);
				createCategory(categoryCheck);
			});
		}
	}

	private void createCategory(String categoryName) {
		category = categoryDAO.getCategoryByName(categoryName);
	}

	@FXML
	private void addNewProduct() {
		if (productField.getText().isEmpty() || priceField.getText().isEmpty() || descriptionField.getText().isEmpty()
		        || quantityField.getText().isEmpty() || skuField.getText().isEmpty() || registrationField.getText().isEmpty()
		        || categorySplitMenuButton.getText().isEmpty() || createAtDatePicker.getValue() == null) {
		        
		        emptyComponent.setText("Algum dos componentes não foram preenchidos corretamente. Verifique e tente novamente.");
		        return;
		}        
		
		String productName = productField.getText(), description = descriptionField.getText(), sku = skuField.getText(),
				registrationSupplier = registrationField.getText();

		Integer quantity = Integer.parseInt(quantityField.getText());

		Double price = Double.parseDouble(priceField.getText());

		LocalDate createAtLocaDate = createAtDatePicker.getValue();
		Date createAt = java.sql.Date.valueOf(createAtLocaDate);

		supplier = createSupplier(registrationSupplier);
		createProduct(productName, description, sku, quantity, price, createAt, category, supplier);

		redirectProductViewScreen();
	}

	private void createProduct(String productName, String description, String sku, Integer quantity, Double price,
			Date createAt, Category category2, Supplier supplier2) {
		product = new Product(productName, description, price, quantity, sku, supplier2, category2, createAt);
		productDAO.create(product);
	}

	private Supplier createSupplier(String registrationSupplier) {
		return supplierDAO.getSupplierRegistration(registrationSupplier);
	}

	@FXML
	public void redirectProductViewScreen() {
		redirectToViewScreen("/views/ProductViewScreen.fxml", cancelButton);
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
