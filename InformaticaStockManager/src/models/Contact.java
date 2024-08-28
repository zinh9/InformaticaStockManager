package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contact")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idContact")
	private Integer idContact;

	@Column(name = "type", nullable = false, length = 3)
	private String type;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@Column(name = "email", nullable = false, length = 80, unique = true)
	private String email;

	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "idSupplier")
	private Supplier supplier;

	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(String type, String phone, String email, Employee employee) {
		this.type = type;
		this.phone = phone;
		this.email = email;
		this.employee = employee;
	}
	
	public Contact(String type, String phone, String email, Customer customer) {
		this.type = type;
		this.phone = phone;
		this.email = email;
		this.customer = customer;
	}
	
	public Contact(String type, String phone, String email, Supplier supplier) {
		this.type = type;
		this.phone = phone;
		this.email = email;
		this.supplier = supplier;
	}

	public Integer getIdContact() {
		return idContact;
	}

	public void setIdContact(Integer idContact) {
		this.idContact = idContact;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
