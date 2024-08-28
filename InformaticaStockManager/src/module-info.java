module InformaticaStockManager {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.persistence;
	requires java.transaction;
	requires org.hibernate.orm.core;
	requires jbcrypt;
	requires java.sql;
	
	opens dao;
	opens dao.products;
	opens util;
	opens models;
	opens models.products;
	opens application to javafx.graphics, javafx.fxml;
	opens views to javafx.graphics, javafx.fxml;
	opens views.add to javafx.graphics, javafx.fxml;
	opens views.show to javafx.fxml, javafx.graphics;
	opens views.update to javafx.fxml, javafx.graphics;
	opens controllers to javafx.fxml;
	opens controllers.add to javafx.fxml;
	opens controllers.show to javafx.fxml;
	opens controllers.update to javafx.fxml;
}
