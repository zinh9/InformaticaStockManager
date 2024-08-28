package dao;

import java.util.List;

import models.Address;

public class AddressDAO {
	private GenericsDAO<Address> genericsDAO;
	
	public AddressDAO() {
		genericsDAO = new GenericsDAO<>(Address.class);
	}
	
	public void create(Address address) {
		genericsDAO.create(address);
	}
	
	public List<Address> getAll(){
		return genericsDAO.getAll();
	}
}
