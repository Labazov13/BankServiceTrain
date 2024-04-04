package com.example.BankService.dao;

import com.example.BankService.entity.ClientDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO{
    private final SessionFactory sessionFactory;

    public ClientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void addClientDetails(ClientDetails clientDetails) {
        Session session = sessionFactory.openSession();
        session.save(clientDetails);
        session.close();
    }

    @Override
    @Transactional
    public ClientDetails findByUsername(String username) {
        ClientDetails clientDetails;
        try (Session session = sessionFactory.openSession()) {
            Query<ClientDetails> query = session.createQuery("FROM ClientDetails WHERE username=:username");
            query.setParameter("username", username, StringType.INSTANCE);
            clientDetails = query.uniqueResult();
        }
        return clientDetails;
    }

    @Override
    @Transactional
    public void updateClient(ClientDetails clientDetails) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(clientDetails);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void deleteClient(ClientDetails clientDetails) {
        try(Session session = sessionFactory.openSession()){
            session.delete(clientDetails);
        }
    }

    @Override
    @Transactional
    public ClientDetails getClientDetailsById(long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(ClientDetails.class, id);
        }
    }

    public ClientDetails getClientDetailsByEmail(String email) {
        ClientDetails clientDetails;
        try (Session session = sessionFactory.openSession()) {
            Query<ClientDetails> query = session.createQuery("FROM ClientDetails WHERE email=:email");
            query.setParameter("email", email, StringType.INSTANCE);
            clientDetails = query.uniqueResult();
        }
        return clientDetails;
    }

    @Override
    @Transactional
    public List<ClientDetails> getAllClientDetails() {
        List<ClientDetails> clientDetailsList;
        try (Session session = sessionFactory.openSession()) {
            clientDetailsList = session.createQuery("from ClientDetails").getResultList();
        }
        return clientDetailsList;
    }
}