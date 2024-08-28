package controllers.update;

import java.time.LocalDate;
import java.util.Date;

import controllers.AuthorizationAdminController;
import dao.products.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.products.Product;

public class FormToUpdateProductDataController {
	private Stage stage;
	private Product product;
	private ProductDAO productDAO;
	
	@FXML
	private Button cancelButton;

	@FXML
	private TextArea descriptionArea;

	@FXML
	private TextField priceField;

	@FXML
	private TextField productField;

	@FXML
	private TextField quantityInStockField;

	@FXML
	private Button updateProductButton;

	@FXML
	private DatePicker updatedAtDataPicker;
	
	public void initialize(Product redirectProduct) {
		productDAO = new ProductDAO();
		
		product = redirectProduct;
		
		productField.setText(!product.getName().isEmpty() ? product.getName() : "");
		
		String price = String.valueOf(product.getPrice());
		priceField.setText(product.getPrice() > 0 ? price : "");
		
		String quantity = String.valueOf(product.getQuantityInStock());
		quantityInStockField.setText(quantity);
		
		Date updatedAtDate = product.getUpdatedAt();
		if(updatedAtDate != null) {
			java.sql.Date sqlDate = new java.sql.Date(updatedAtDate.getTime());
			LocalDate updatedAt = sqlDate.toLocalDate();
			updatedAtDataPicker.setValue(updatedAt);
		} else {
			updatedAtDataPicker.setValue(null);
		}
		
		descriptionArea.setText(!product.getDescription().isEmpty() ? product.getDescription() : "");
	}
	
	@FXML
	private void updateProduct() {
		String productName = productField.getText(),
				description = descriptionArea.getText();
		
		Integer quantity = Integer.parseInt(quantityInStockField.getText());
		
		Double price = Double.parseDouble(priceField.getText());
		
		LocalDate updatedAtLocal = updatedAtDataPicker.getValue();
		Date updatedAt = java.sql.Date.valueOf(updatedAtLocal);
		
		if(authorizationAdmin()) {
			setProduct(productName, price, description, quantity, updatedAt);
			
			productDAO.update(product);
			
			closeScreen();	
		}		
	}

	private void setProduct(String productName, Double price, String description, Integer quantity, Date updatedAt) {
		product.setName(productName);
		product.setPrice(price);
		product.setDescription(description);
		product.setQuantityInStock(quantity);
		product.setUpdatedAt(updatedAt);
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
