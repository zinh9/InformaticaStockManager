package controllers.show;

import controllers.AuthorizationAdminController;
import controllers.update.FormToUpdateSaleDataController;
import dao.SaleDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Sale;

public class ScreenShowingAllSaleDataController {
	private Stage stage;
	private Sale sale;
	private SaleDAO saleDAO;
	
	@FXML
	private Button updateSaleButton;
	
	@FXML
    private Button deleteSaleButton;

    @FXML
    private Label setCustomerCpf;

    @FXML
    private Label setCustomerName;

    @FXML
    private Label setEmployeeRegistration;

    @FXML
    private Label setPaymentMethod;

    @FXML
    private Label setProduct;

    @FXML
    private Label setQuantity;

    @FXML
    private Label setSaleDate;

    @FXML
    private Label setTotalAmount;
    
    public void initialize(Sale sale) {
    	this.sale = sale;
    	
    	setProduct.setText(sale != null && sale.getProduct() != null ? sale.getProduct().getName() : "");
    	setSaleDate.setText(sale != null && sale.getSaleDate() != null ? sale.getSaleDate().toString() : "");
    	setTotalAmount.setText(sale != null ? "R$ " + sale.getTotalAmount() : "");
    	setQuantity.setText(sale != null ? sale.getQuantity().toString() : "");
    	setPaymentMethod.setText(sale != null ? sale.getPaymentMethod() : "");
    	
    	setCustomerName.setText(sale != null && sale.getCustomer() != null ? sale.getCustomer().getFullName() : "");
    	setCustomerCpf.setText(sale != null && sale.getCustomer() != null ? sale.getCustomer().getCpf() : "");
    	
    	setEmployeeRegistration.setText(sale != null && sale.getEmployee() != null ? sale.getEmployee().getRegistration() : "");
    }
    
    @FXML
    private void redirectFormToUpdateSaleDataScreen() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/update/FormToUpdateSaleDataScreen.fxml"));
    		VBox root = loader.load();
			
    		FormToUpdateSaleDataController formUpdateSale = loader.getController();
    		formUpdateSale.initialize(sale);
    		
    		stage = (Stage) updateSaleButton.getScene().getWindow();
    		stage.setScene(new Scene(root));
    		stage.setMaximized(true);
    		stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    private void deleteSale() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationScreen.fxml"));
            VBox root = loader.load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            AuthorizationAdminController authController = loader.getController();
            
            if (authController.isAdminAuthorized()) {
                saleDAO = new SaleDAO();
                saleDAO.delete(sale);
                
                stage = (Stage) deleteSaleButton.getScene().getWindow();
                stage.close();
            }		
            
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
