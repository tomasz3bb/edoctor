package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IAppointmentDetailDAO;
import pl.edu.wszib.edoctor.model.AppointmentDetail;

import javax.persistence.NoResultException;

@Repository
public class AppointmentDetailDAOImpl implements IAppointmentDetailDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public AppointmentDetail getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<AppointmentDetail> query = session.createQuery("from pl.edu.wszib.edoctor.model.AppointmentDetail where appointment.appointmentId =:id");
        query.setParameter("id", id);
        AppointmentDetail appointmentDetail = null;
        try {
            appointmentDetail = query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        session.close();
        return appointmentDetail;
    }

    @Override
    public boolean save(AppointmentDetail appointmentDetail) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(appointmentDetail);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean update(AppointmentDetail appointmentDetail) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(appointmentDetail);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean delete(AppointmentDetail appointmentDetail) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(appointmentDetail);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }
}
