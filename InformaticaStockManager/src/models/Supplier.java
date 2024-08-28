package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Supplier")
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSupplier")
	private Integer idSupplier;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "contactName", length = 100)
	private String contactName;

	@Column(name = "registrationNumber", unique = true, length = 20)
	private String registrationNumber;

	@Column(name = "notes")
	private String notes;
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Contact> contacts;
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Address> addresses;

	public Supplier() {
		// TODO Auto-generated constructor stub
	}

	public Supplier(String name, String contactName, String registrationNumber, String notes) {
		this.name = name;
		this.contactName = contactName;
		this.registrationNumber = registrationNumber;
		this.notes = notes;
	}

	public Integer getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(Integer idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}
	
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
}
