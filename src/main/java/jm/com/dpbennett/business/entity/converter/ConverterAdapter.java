/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jm.com.dpbennett.business.entity.BusinessEntity;

/**
 *
 * @author desbenn
 */
public class ConverterAdapter implements Converter {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public ConverterAdapter() {        
        emf = Persistence.createEntityManagerFactory("JMTSPU"); // tk get from bundle       
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((BusinessEntity) value).getName();
    }
    
}