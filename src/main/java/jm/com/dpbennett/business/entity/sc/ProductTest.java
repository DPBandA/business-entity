/*
Business Entity Library (BEL) - A foundational library for JSF web applications 
Copyright (C) 2017  D P Bennett & Associates Limited

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

Email: info@dpbennett.com.jm
 */

package jm.com.dpbennett.business.entity.sc;

import jm.com.dpbennett.business.entity.mt.Test;
import jm.com.dpbennett.business.entity.hrm.Employee;
import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import jm.com.dpbennett.business.entity.BusinessEntity;
import jm.com.dpbennett.business.entity.util.ReturnMessage;

/**
 *
 * @author Desmond Bennett
 */
@Entity
@Table(name = "producttest")
public class ProductTest implements Test, Serializable, Comparable, BusinessEntity  {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double hourlyRate;
    private String type;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date testDate;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Employee testDoneBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date reTestDate;
    private String category;
    @Transient
    private Boolean isDirty;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductTest)) {
            return false;
        }
        ProductTest other = (ProductTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jm.org.bsj.entity.FoodTest[id=" + id + "]";
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
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Date getTestDate() {
        return testDate;
    }

    @Override
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public Employee getTestDoneBy() {
        return testDoneBy;
    }

    @Override
    public void setTestDoneBy(Employee testDoneBy) {
        this.testDoneBy = testDoneBy;
    }

    @Override
    public Date getReTestDate() {
        return reTestDate;
    }

    @Override
    public void setReCalibrationDate(Date reTestDate) {
        this.reTestDate = reTestDate;
    }

    @Override
    public int compareTo(Object o) {
       return Collator.getInstance().compare(this.toString(), o.toString());
    }

    @Override
    public String getCateogy() {
       return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public ReturnMessage save(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReturnMessage validate(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
