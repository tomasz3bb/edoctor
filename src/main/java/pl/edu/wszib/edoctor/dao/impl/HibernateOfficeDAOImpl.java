package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IOfficeDAO;
import pl.edu.wszib.edoctor.model.Office;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateOfficeDAOImpl implements IOfficeDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Office getById(int officeId) {
        Session session = this.sessionFactory.openSession();
        Query<Office> officeQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Office where officeId = :officeId");
        officeQuery.setParameter("officeId", officeId);
        Office office = null;
        try {
            office = officeQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono gabinetu!");
        }
        session.close();
        return office;
    }

    @Override
    public List<Office> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Office> officeQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Office");
        List<Office> officeList = officeQuery.getResultList();
        session.close();
        return officeList;
    }

    @Override
    public void save(Office office) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(office);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Office office) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(office);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void update(Office office) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(office);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }
}
