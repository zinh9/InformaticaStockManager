package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import util.ConectionDB;

public class GenericsDAO<E> {
	private EntityManager em;
	private Class<E> c;
	
	public GenericsDAO() {
		em = ConectionDB.createEntityManager();
	}

	public GenericsDAO(Class<E> c) {
		em = ConectionDB.createEntityManager();
		this.c = c;
	}
	
	public GenericsDAO<E> openTransaction(){
		em.getTransaction().begin();
		return this;
	}
	
	public GenericsDAO<E> closeTransaction(){
		em.getTransaction().commit();
		return this;
	}
	
	public GenericsDAO<E> rollback(){
		em.getTransaction().rollback();
		return this;
	}
	
	public GenericsDAO<E> createAtomic(E entity){
		em.persist(entity);
		return this;
	}
	
	public void create(E entity) {
		try {
			openTransaction().createAtomic(entity).closeTransaction();
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	public List<E> getAll(){
		return this.getAll(100, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getAll(int maxResults, int firstResults){
		String jpql = "SELECT e FROM " + c.getName() + " e";
		
		Query query = em.createQuery(jpql);
		
		query.setMaxResults(maxResults);
		query.setFirstResult(firstResults);
		
		return query.getResultList();
	}
	
	public E getById(Integer id){
		String jpql = "SELECT e FROM " + c.getName() + " e WHERE e.id = :id";
		
		TypedQuery<E> query = em.createQuery(jpql, c);
		query.setParameter("id", id);
		
		return query.getSingleResult();
	}
	
	public void update(E entity) {
		try {
			openTransaction();
			em.detach(entity);
			em.merge(entity);
			closeTransaction();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public void delete(E entity) {
		try {
			openTransaction();
			em.remove(em.contains(entity) ? entity : em.merge(entity));
			closeTransaction();			
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
}
