package com.example.BankService.dao;

import com.example.BankService.entity.ClientDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO{
    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ClientDetails.class);
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }

    @Override
    @Transactional
    public void addClient(ClientDetails clientDetails) {
        Session session = getSession();
        session.beginTransaction();
        session.save(clientDetails);
        session.close();
    }

    @Override
    @Transactional
    public void updateClient(ClientDetails clientDetails) {
        Session session = getSession();
        session.beginTransaction();
        session.update(clientDetails);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public void deleteClient(ClientDetails clientDetails) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(clientDetails);
        session.close();
    }

    @Override
    @Transactional
    public ClientDetails getClientDetailsById(long id) {
        Session session = getSession();
        session.beginTransaction();
        return session.get(ClientDetails.class, id);
    }

    public ClientDetails getClientDetailsByEmail(String email) {
        var clients = getAllClientDetails();
        for (ClientDetails client : clients){
            if (client.getClient().getEmail().equals(email)){
                return client;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public List<ClientDetails> getAllClientDetails() {
        Session session = getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClientDetails> criteria = builder.createQuery(ClientDetails.class);
        criteria.from(ClientDetails.class);
        return session.createQuery(criteria).getResultList();
    }
}
