package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IAppSlotDAO;
import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

@Repository
public class AppSlotDAOImpl implements IAppSlotDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean save(AppSlot appSlot) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(appSlot);
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
    public boolean update(AppSlot appSlot) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(appSlot);
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
    public boolean delete(AppSlot appSlot) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(appSlot);
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
    public AppSlot getById(int appSlotId) {
        Session session = this.sessionFactory.openSession();
        Query<AppSlot> appSlotQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.AppSlot where appSlotId =: appSlotId");
        appSlotQuery.setParameter("appSlotId", appSlotId);
        AppSlot appSlot = null;
        try {
            appSlot = appSlotQuery.getSingleResult();
        }catch (NoResultException e){
            e.printStackTrace();
        }
        session.close();
        return appSlot;
    }

    @Override
    public List<AppSlot> getAllByDoctorAndDate(Doctor doctor, Date keyword) {
        Session session = this.sessionFactory.openSession();
        Query<AppSlot> appSlotQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.AppSlot where doctor =: doctor and appointmentDate =: keyword and isAvailable = true ");
        appSlotQuery.setParameter("doctor", doctor);
        appSlotQuery.setParameter("keyword", keyword);
        List<AppSlot> appSlotList = appSlotQuery.getResultList();
        session.close();
        return appSlotList;
    }
}
