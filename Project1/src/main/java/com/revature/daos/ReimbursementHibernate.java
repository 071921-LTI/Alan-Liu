package com.revature.daos;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.util.HibernateUtil;


import com.revature.models.Reimbursement;

public class ReimbursementHibernate implements ReimbursementDao{
	
	@Override
	public Reimbursement getReimbursementById(int id) throws SQLException {
		Reimbursement r = null;
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			r = s.get(Reimbursement.class, id);
		}
		return r;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() throws SQLException {
		List<Reimbursement> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			reimbs = s.createQuery("FROM Reimbursement", Reimbursement.class).getResultList();
		}
		return reimbs;
	}

	@Override
	public List<Reimbursement> getReimbursementByUser(int id) throws SQLException {
		List<Reimbursement> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from Reimbursement as r where r.authorId.id = :user");
			TypedQuery<Reimbursement> nq = s.createQuery(hql, Reimbursement.class);
			nq.setParameter("user", id);
			reimbs = nq.getResultList();
		}
		
		return reimbs;
	}

	@Override
	public int addReimbursement(Reimbursement r) throws SQLException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.persist(r);
			tx.commit();
		}
		return r.getId();
	}

	@Override
	public boolean approveOrDeny(String choice, int id, int resolverId) throws SQLException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			if (s.get(Reimbursement.class, id) == null) {
				return false;
			} else {
				Transaction tx = s.beginTransaction();
				s.merge(getReimbursementById(id));
				tx.commit();

			}
			return true;
		}
	}

	@Override
	public void deleteReimb(Reimbursement r) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.delete(r);
			tx.commit();
		}
		
	}


	@Override
	public List<Reimbursement> getReimbursementByPending() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reimbursement> getReimbursementByResolved() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
