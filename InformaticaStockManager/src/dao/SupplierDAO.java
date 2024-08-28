package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Supplier;
import util.ConectionDB;

public class SupplierDAO {
	private EntityManager em;
	private GenericsDAO<Supplier> genericsDAO;
	
	public SupplierDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Supplier.class);
	}
	
	public void create(Supplier supplier) {
		genericsDAO.create(supplier);
	}
	
	public List<Supplier> getAll(){
		List<Supplier> suppliers = genericsDAO.getAll();
		return suppliers;
	}
	
	public Supplier getById(Integer id) {
		Supplier supplier = genericsDAO.getById(id);
		return supplier;
	}
	
	// Métodos específicos do SupplierDAO
	public Supplier getSupplierRegistration(String registrationNumber) {
		try {
			String jpql = "SELECT s FROM Supplier s WHERE s.registrationNumber = :registrationNumber";
			
			TypedQuery<Supplier> query = em.createQuery(jpql, Supplier.class);
			query.setParameter("registrationNumber", registrationNumber);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o fornecedor!");
			return null;
		}
	}
	
	public Supplier getSupplierWithDetails(Integer id) {
		try {
			String jpql = "SELECT s FROM Supplier s "
					+ "LEFT JOIN FETCH s.contacts "
					+ "WHERE s.idSupplier = :id";
			TypedQuery<Supplier> query = em.createQuery(jpql, Supplier.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o fornecedor!");
			return null;
		}
	}
	
	public void update(Supplier supplier) {
		genericsDAO.update(supplier);
	}
	
	public void delete(Supplier supplier) {
		genericsDAO.delete(supplier);
	}
}
