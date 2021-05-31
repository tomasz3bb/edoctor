package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateDoctorScheduleDAOImpl implements IDoctorScheduleDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorSchedule> query = session.createQuery("FROM pl.edu.wszib.edoctor.model.DoctorSchedule where doctor.doctorId  = : doctorId order by dayOfWeek.dayOfWeek");
        query.setParameter("doctorId", doctorId);
        List<DoctorSchedule> doctorScheduleList = query.getResultList();
        session.close();
        return doctorScheduleList;
    }

    @Override
    public List<DoctorSchedule> getDoctorScheduleByDoctor(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorSchedule> query = session.createQuery("FROM pl.edu.wszib.edoctor.model.DoctorSchedule where doctor  = : doctor order by dayOfWeek.dayOfWeek");
        query.setParameter("doctor", doctor);
        List<DoctorSchedule> doctorScheduleList = query.getResultList();
        session.close();
        return doctorScheduleList;
    }

    @Override
    public DoctorSchedule getDoctorScheduleById(int doctorScheduleId) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorSchedule> query = session.createQuery("from pl.edu.wszib.edoctor.model.DoctorSchedule where doctorScheduleId = : doctorScheduleId");
        query.setParameter("doctorScheduleId", doctorScheduleId);
        DoctorSchedule doctorSchedule = null;
        try {
            doctorSchedule = query.getSingleResult();
        }catch (NoResultException e){
            System.out.println("Błąd");
        }
        session.close();
        return doctorSchedule;
    }

    @Override
    public void addDoctorSchedule(DoctorSchedule doctorSchedule) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(doctorSchedule);
            tx.commit();
        } catch (Exception e) {
            if (tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(doctorSchedule);
            tx.commit();
        } catch (Exception e) {
            if (tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void deleteDoctorSchedule(DoctorSchedule doctorSchedule) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(doctorSchedule);
            tx.commit();
        } catch (Exception e) {
            if (tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }
}
