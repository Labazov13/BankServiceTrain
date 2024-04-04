package com.example.BankService.dao;

import com.example.BankService.entity.ClientDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO{
    @Autowired
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
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM ClientDetails WHERE username=:username");
        query.setParameter("username", username, StringType.INSTANCE);
        ClientDetails clientDetails = (ClientDetails) query.uniqueResult();
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
        Session session = sessionFactory.openSession();
        session.delete(clientDetails);
    }

    @Override
    @Transactional
    public ClientDetails getClientDetailsById(long id) {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        List<ClientDetails> clientDetailsList = session.createQuery("from ClientDetails").getResultList();
        return clientDetailsList;
    }
}