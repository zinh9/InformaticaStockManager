package controllers.show;

import controllers.AuthorizationAdminController;
import controllers.update.FormToUpdateProductDataController;
import dao.products.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.products.Product;

public class ScreenShowingAllProductDataController {
	private Stage stage;
	private Product product;
	private ProductDAO productDAO;
	
	@FXML
	private Button updateProductButton;
	
	@FXML
    private Button deleteProductButton;

    @FXML
    private Label setCategory;

    @FXML
    private Label setCreatedAt;

    @FXML
    private Label setDescription;

    @FXML
    private Label setPrice;

    @FXML
    private Label setProduct;

    @FXML
    private Label setQuantityInStock;

    @FXML
    private Label setSku;

    @FXML
    private Label setSupplier;

    @FXML
    private Label setUpdateAt;
    
    public void initialize(Product product){
    	this.product = product;
    	
    	setProduct.setText(product != null ? product.getName() : "");
    	setDescription.setText(product != null ? product.getDescription() : "");
    	setPrice.setText(product != null ? "R$ " + product.getPrice().toString() : "");
    	setQuantityInStock.setText(product != null ? product.getQuantityInStock().toString() : "");
    	setSku.setText(product != null ? product.getSku() : "");
    	setCreatedAt.setText(product != null && product.getCreatedAt() != null ? product.getCreatedAt().toString() : "");
    	setUpdateAt.setText(product != null && product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "");
    	
    	setCategory.setText(product != null && product.getCategory() != null ? product.getCategory().getCategoryName() : "");
    	
    	setSupplier.setText(product != null && product.getSupplier() != null ? product.getSupplier().getName() : "");
    }
    
    @FXML
    private void redirectFormToUpdateProductDataScreen() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/update/FormToUpdateProductDataScreen.fxml"));
			VBox root = loader.load();
			
			FormToUpdateProductDataController formUpdateProduct = loader.getController();
			formUpdateProduct.initialize(product);
			
			stage = (Stage) updateProductButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void deleteProduct() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
            VBox root = loader.load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            AuthorizationAdminController authController = loader.getController();
            
            if (authController.isAdminAuthorized()) {
                productDAO = new ProductDAO();
                productDAO.delete(product);
                
                stage = (Stage) deleteProductButton.getScene().getWindow();
                stage.close();
            }		
            
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
