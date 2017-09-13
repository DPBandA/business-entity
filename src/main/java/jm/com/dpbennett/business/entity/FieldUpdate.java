/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author desbenn
 */
@Entity
public class FieldUpdate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fieldName;
    private String className;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateUpdated;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Employee updatedBy;

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Employee updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    /**
     * Get the value of dateUpdated
     *
     * @return the value of dateUpdated
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Set the value of dateUpdated
     *
     * @param dateUpdated new value of dateUpdated
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }


    /**
     * Get the value of className
     *
     * @return the value of className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the value of className
     *
     * @param className new value of className
     */
    public void setClassName(String className) {
        this.className = className;
    }


    public FieldUpdate() {
    }

    /**
     * Get the value of fieldName
     *
     * @return the value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Set the value of fieldName
     *
     * @param fieldName new value of fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof FieldUpdate)) {
            return false;
        }
        FieldUpdate other = (FieldUpdate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jm.com.dpbennett.business.entity.FieldUpdate[ id=" + id + " ]";
    }

}
