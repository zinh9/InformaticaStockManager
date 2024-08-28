package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.products.Product;

@Entity
@Table(name = "Sale")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSale")
	private Integer idSale;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	private Product product;

	@Column(name = "saleDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date saleDate;

	@Column(name = "totalAmount", nullable = false)
	private Double totalAmount;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "paymentMethod", nullable = false, length = 20)
	private String paymentMethod;

	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;

	public Sale() {
		// Default constructor
	}

	public Sale(Product product, Date saleDate, Double totalAmount, Integer quantity, String paymentMethod,
			Customer customer, Employee employee) {
		this.product = product;
		this.saleDate = saleDate;
		this.totalAmount = totalAmount;
		this.quantity = quantity;
		this.paymentMethod = paymentMethod;
		this.customer = customer;
		this.employee = employee;
	}

	public Integer getIdSale() {
		return idSale;
	}

	public void setIdSale(Integer idSale) {
		this.idSale = idSale;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Product getProduct() {
		return product;
	}
}
