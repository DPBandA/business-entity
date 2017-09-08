/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import jm.com.dpbennett.business.utils.BusinessEntityUtils;

/**
 *
 * @author dbennett
 */
@Entity
@Table(name = "samplerequest")
public class SampleRequest implements Serializable, BusinessEntity, Form {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @OneToOne(cascade = CascadeType.ALL)
    private Client receivedFrom;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact representative;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Employee inspector;
    @Column(length = 1024)
    private String comments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductInspection> products;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfRequest;

    public SampleRequest() {
        products = new ArrayList<ProductInspection>();
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public List<ProductInspection> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInspection> products) {
        this.products = products;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Employee getInspector() {
        if (inspector == null) {
            return new Employee();
        }
        return inspector;
    }

    public void setInspector(Employee inspector) {
        this.inspector = inspector;
    }

    public Client getReceivedFrom() {
        if (receivedFrom == null) {
            receivedFrom = new Client("");
        }
        return receivedFrom;
    }

    public void setReceivedFrom(Client receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public Contact getRepresentative() {
        if (representative == null) {
            representative = new Contact();
        }
        return representative;
    }

    public void setRepresentative(Contact representative) {
        this.representative = representative;
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
        if (!(object instanceof SampleRequest)) {
            return false;
        }
        SampleRequest other = (SampleRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jm.org.bsj.entity.SampleRequest[id=" + id + "]";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    public static List<SampleRequest> findSampleRequestsByDateSearchField(
            EntityManager em,
            JobManagerUser user,
            String dateSearchField,
            String searchType,
            String originalSearchText,
            Date startDate,
            Date endDate) {

        List<SampleRequest> foundSampleRequests = null;
        String searchQuery = null;
        String searchTextAndClause = "";
        String searchText;

        if (originalSearchText != null) {
            searchText = originalSearchText.replaceAll("'", "''");
        } else {
            searchText = "";
        }

        if (searchType.equals("General")) {
            if (!searchText.equals("")) {
                searchTextAndClause =
                        " AND ("
                        + " UPPER(sampleRequest.type) LIKE '%" + searchText.toUpperCase() + "%'"
                        + " OR UPPER(inspector.firstName) LIKE '%" + searchText.toUpperCase() + "%'"
                        + " OR UPPER(inspector.lastName) LIKE '%" + searchText.toUpperCase() + "%'"
                        + " OR UPPER(receivedFrom.name) LIKE '%" + searchText.toUpperCase() + "%'"
                        + " OR UPPER(sampleRequest.comments) LIKE '%" + searchText.toUpperCase() + "%'"
                        + " )";
            }
            if ((startDate == null) || (endDate == null)) {
                searchQuery =
                        "SELECT sampleRequest FROM SampleRequest sampleRequest"
                        + " JOIN sampleRequest.inspector inspector"
                        + " JOIN sampleRequest.receivedFrom receivedFrom"
                        + " WHERE (0 = 0)" // used as place holder
                        + searchTextAndClause
                        + " ORDER BY sampleRequest.id DESC";
            } else {
                searchQuery =
                        "SELECT sampleRequest FROM SampleRequest sampleRequest"
                        + " JOIN sampleRequest.inspector inspector"
                        + " JOIN sampleRequest.receivedFrom receivedFrom"
                        + " WHERE (sampleRequest." + dateSearchField + " >= " + BusinessEntityUtils.getDateString(startDate, "'", "YMD", "-")
                        + " AND sampleRequest." + dateSearchField + " <= " + BusinessEntityUtils.getDateString(endDate, "'", "YMD", "-") + ")"
                        + searchTextAndClause
                        + " ORDER BY sampleRequest.id DESC";
            }
        } else if (searchType.equals("?")) {
        }

        try {
            foundSampleRequests = em.createQuery(searchQuery, SampleRequest.class).getResultList();
            if (foundSampleRequests == null) {
                foundSampleRequests = new ArrayList<SampleRequest>();
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        return foundSampleRequests;
    }
}