package dao;

import models.Contact;

public class ContactDAO {
	private GenericsDAO<Contact> genericsDAO;
	
	public ContactDAO() {
		genericsDAO = new GenericsDAO<>(Contact.class);
	}
	
	public void create(Contact contact) {
		genericsDAO.create(contact);
	}
}
