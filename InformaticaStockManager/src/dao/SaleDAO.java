package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Sale;
import util.ConectionDB;

public class SaleDAO {
	private EntityManager em;
	private GenericsDAO<Sale> genericsDAO;
	
	public SaleDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Sale.class);
	}
	
	public void create(Sale sale) {
		genericsDAO.create(sale);
	}
	
	public List<Sale> getAll(){
		return genericsDAO.getAll();
	}
	
	public Sale getById(Integer id) {
		try {
			String jpql = "SELECT s FROM Sale s WHERE s.idSale = :id";
			TypedQuery<Sale> query = em.createQuery(jpql, Sale.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Object[]> getSalesQuantityByDate() {
		try {
			String jpql = "SELECT s.saleDate, SUM(s.quantity) FROM Sale s GROUP BY s.saleDate";
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
			
			return query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public Sale getSaleWithDetails(Integer id) {
		try {
			String jpql = "SELECT s FROM Sale s "
					+ "LEFT JOIN FETCH s.customer "
					+ "LEFT JOIN FETCH s.employee "
					+ "WHERE s.idSale = :id";
			TypedQuery<Sale> query = em.createQuery(jpql, Sale.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Não achei a venda!");
			return null;
		}
	}
	
	public void update(Sale sale) {
		genericsDAO.update(sale);
	}
	
	public void delete(Sale sale) {
		genericsDAO.delete(sale);
	}
}
