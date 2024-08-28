package dao.products;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.GenericsDAO;
import models.products.Product;
import util.ConectionDB;

public class ProductDAO {
	private EntityManager em;
	private GenericsDAO<Product> genericsDAO;
	
	public ProductDAO() {
		em = ConectionDB.createEntityManager();
		genericsDAO = new GenericsDAO<>(Product.class);
	}
	
	public void create(Product product) {
		genericsDAO.create(product);
	}
	
	public List<Product> getAll(){
		List<Product> products = genericsDAO.getAll();
		return products;
	}
	
	public Product getProductByName(String productName) {
		try {
			String likeProductName = "%" + productName + "%";
			String jpql = "SELECT p FROM Product p WHERE p.name LIKE :likeProductName";
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			query.setParameter("likeProductName", likeProductName);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o produto");
			return null;
		}
	}
	
	public Product getProductBySku(String sku) {
		try {
			String jpql = "SELECT p FROM Product p WHERE p.sku = :sku";
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			query.setParameter("sku", sku);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o product!");
			return null;
		}
	}
	
	public Product getProductWithDetails(Integer id) {
		try {
			String jpql = "SELECT p FROM Product p "
					+ "LEFT JOIN FETCH p.category "
					+ "LEFT JOIN FETCH p.supplier "
					+ "WHERE p.idProduct = :id";
			TypedQuery<Product> query = em.createQuery(jpql, Product.class);
			query.setParameter("id", id);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não achei o product!");
			return null;
		}
	}
	
	public void update(Product product) {
		genericsDAO.update(product);
	}
	
	public void delete(Product product) {
		genericsDAO.delete(product);
	}
}
