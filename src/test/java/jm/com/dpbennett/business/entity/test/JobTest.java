/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity.test;

import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jm.com.dpbennett.business.entity.Job;
import org.junit.Test;

/**
 *
 * @author Desmond Bennett <info@dpbennett.com.jm at http//dpbennett.com.jm>
 */
public class JobTest {
    @Test
    public void test() {
        HashMap prop = new HashMap();

        prop.put("javax.persistence.jdbc.user",
                "root");
        prop.put("javax.persistence.jdbc.password",
                ""); // NB: REMOVE PWD WHEN DONE
        prop.put("javax.persistence.jdbc.url",
                "jdbc:mysql://localhost:3306/jmts");
        prop.put("javax.persistence.jdbc.driver",
                "com.mysql.jdbc.Driver");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU", prop);
        EntityManager em = emf.createEntityManager();

       // These instantiations cause the respective database tables to be created
       // if they don't already exist.
       Job job = new Job();

    }
}