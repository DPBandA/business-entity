/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import jm.com.dpbennett.business.entity.utils.BusinessEntityUtils;
import jm.com.dpbennett.business.entity.utils.ReturnMessage;

/**
 *
 * @author dbennett
 */
@Entity
@Table(name = "accountingcode")
@NamedQueries({
    @NamedQuery(name = "findAllAccountingCodes",
            query = "SELECT a FROM AccountingCode a ORDER BY a.name")
})
public class AccountingCode implements Serializable, BusinessEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length = 1024)
    private String description;
    @Transient
    private Boolean isDirty;

    public AccountingCode() {
        name = "";
        type = "";
        description = "";
    }

    public AccountingCode(String name) {
        this.name = name;
        type = "";
        description = "";
    }

    public static List<AccountingCode> findAllAccountingCodes(EntityManager em) {

        try {
            List<AccountingCode> codes = em.createNamedQuery("findAllAccountingCodes", AccountingCode.class).getResultList();

            return codes;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static List<AccountingCode> findAccountingCodesByNameAndDescription(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<AccountingCode> accountingCodes
                    = em.createQuery("SELECT a FROM AccountingCode a WHERE UPPER(a.name) LIKE '%"
                            + value.toUpperCase().trim() + "%' OR UPPER(a.description) LIKE '%"
                            + value.toUpperCase().trim() + "%' ORDER BY a.name",
                            AccountingCode.class).getResultList();
            return accountingCodes;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountingCode)) {
            return false;
        }
        AccountingCode other = (AccountingCode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ReturnMessage save(EntityManager em) {
        try {
            em.getTransaction().begin();
            BusinessEntityUtils.saveBusinessEntity(em, this);
            em.getTransaction().commit();

            return new ReturnMessage();
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ReturnMessage(false, "AccountingCode not saved");
    }

    @Override
    public ReturnMessage validate(EntityManager em) {
        return new ReturnMessage();
    }

    @Override
    public Boolean getIsDirty() {
        if (isDirty == null) {
            isDirty = false;
        }
        return isDirty;
    }

    @Override
    public void setIsDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    public static AccountingCode findByName(EntityManager em, String value) {

        try {
            value = value.trim().replaceAll("'", "''").replaceAll("&amp;", "&");

            List<AccountingCode> accountingCodes = em.createQuery("SELECT a FROM AccountingCode a "
                    + "WHERE UPPER(a.name) "
                    + "= '" + value.toUpperCase() + "'", AccountingCode.class).getResultList();
            if (accountingCodes.size() > 0) {
                return accountingCodes.get(0);
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
