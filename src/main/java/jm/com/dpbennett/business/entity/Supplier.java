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
package jm.com.dpbennett.business.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import jm.com.dpbennett.business.entity.utils.BusinessEntityUtils;
import jm.com.dpbennett.business.entity.utils.ReturnMessage;

/**
 * @author Desmond Bennett
 */
@Entity
@Table(name = "supplier")
@NamedQueries({
    @NamedQuery(name = "findAllSuppliers", query = "SELECT s FROM Supplier s ORDER BY s.name")
})

@XmlRootElement
public class Supplier implements Serializable, BusinessEntity, Comparable {

    private static final long serialVersionUId = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String number;
    private String type;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    @OneToOne(cascade = CascadeType.ALL)
    private Internet internet;
    @Column(length = 1024)
    private String notes;   
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLastAccessed;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEntered;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEdited;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Employee enteredBy;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Employee editedBy;
    private Boolean tag;
    private String identification;
    private String identificationType;
    private Boolean active;
    private Boolean international;
    @Transient
    private Boolean isDirty;
    private String accountingId;
    @Transient
    private Boolean isNameAndIdEditable;

    /**
     * Constructs a Supplier object.
     * 
     */
    public Supplier() {                
        name = "";
        contacts = new ArrayList<>();
        addresses = new ArrayList<>();
        internet = new Internet();
        active = true;
        international = false;
        isNameAndIdEditable = true;
    }
    
    public Supplier(String name) {        
        this.name = name;
        contacts = new ArrayList<>();
        addresses = new ArrayList<>();
        internet = new Internet();
        active = true;
        international = false;
        isNameAndIdEditable = true;
    }

    public Supplier(String name, Boolean active) {        
        this.name = name;
        contacts = new ArrayList<>();
        addresses = new ArrayList<>();
        internet = new Internet();
        this.active = active;
        international = false;
    }

    public Boolean getIsNameAndIdEditable() {
        return isNameAndIdEditable;
    }

    public void setIsNameAndIdEditable(Boolean isNameAndIdEditable) {
        this.isNameAndIdEditable = isNameAndIdEditable;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getAccountingId() {
        if (accountingId == null) {
            accountingId = "";
        }

        return accountingId;
    }

    public void setAccountingId(String accountingId) {
        this.accountingId = accountingId;
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

    public Date getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public Employee getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Employee editedBy) {
        this.editedBy = editedBy;
    }

    public Supplier(Supplier src, Boolean active) {
        doCopy(src);
        this.active = active;
    }


    public Boolean getInternational() {
        if (international == null) {
            international = false;
        }
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Employee getEnteredBy() {
        if (enteredBy == null) {
            return new Employee();
        }
        return enteredBy;
    }

    public void setEnteredBy(Employee enteredBy) {
        this.enteredBy = enteredBy;
    }

    /**
     * Copy the Supplier without copying the id field
     *
     * @param src
     */
    public final void doCopy(Supplier src) {
        contacts = new ArrayList<>();
        addresses = new ArrayList<>();
        name = src.name;
        number = src.number;
        type = src.type;
        if (src.contacts != null) {
            for (Contact contact : src.contacts) {
                contacts.add(new Contact(contact));
            }
        }
        if (src.addresses != null) {
            for (Address address : src.addresses) {
                addresses.add(new Address(address));
            }
        }
        if (src.internet != null) {
            internet = new Internet(src.internet);
        }
        notes = src.notes;
        dateLastAccessed = src.dateLastAccessed;
        tag = src.tag;
        active = src.active;
        identification = src.identification;
        identificationType = src.identificationType;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        if (active == null) {
            active = true;
        }
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIsActive() {
        if (getActive()) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public void setIsActive(String active) {
        if (active.equals("Yes")) {
            setActive(true);
        } else {
            setActive(false);
        }
    }

    public Internet getInternet() {
        if (internet == null) {
            internet = new Internet();
        }
        return internet;
    }

    public void setInternet(Internet internet) {
        this.internet = internet;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method guards against returning very long names. This is used in an
     * autocomplete JSF component for instance to prevent the list of suppliers
     * from extending beyond the screen. In the future, the maximum length of
     * say 50 will be a value stored in the resource bundle of the BEL.
     *
     * @return
     */
    public String getTruncatedName() {
        if (getName().length() >= 50) {
            return getName().substring(0, 50);
        } else {
            return getName();
        }
    }

    public void setTruncatedName(String name) {
        setName(name);
    }

    @XmlTransient
    public List<Address> getAddresses() {
        if (addresses != null) {
            Collections.sort(addresses);
        } else {
            addresses = new ArrayList<>();
        }

        return addresses;
    }

    public List<Address> getBillingAddresses() {
        ArrayList<Address> billingAddresses = new ArrayList<>();

        for (Address address : getAddresses()) {
            if (address.getType().equals("Billing")) {
                billingAddresses.add(address);
            }
        }

        return billingAddresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @XmlTransient
    public List<Contact> getContacts() {
        if (contacts != null) {
            Collections.sort(contacts);
        } else {
            contacts = new ArrayList<>();
        }

        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getStringListOfContactPhoneNumbers() {
        String list = "";

        for (Contact contact : getContacts()) {
            //for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            if (list.equals("")) // first? 
            {
                list = contact.getMainPhoneNumber().getLocalNumber();
            } else {
                list = list + ", " + contact.getMainPhoneNumber().getLocalNumber();
            }
            //}
        }

        return list;
    }

    /**
     * Get the first main contact which is treated as the main contact in the
     * list of contacts.
     *
     * @return
     */
    public Contact getDefaultContact() {
        if (!getContacts().isEmpty()) {
            // Use the last found contact as the main contact if none was found.            
            return getContacts().get(getContacts().size() - 1);
        } else {
            return new Contact("", "", "Main");
        }
    }

    /**
     * Get the main contact which is treated as the main contact in the list of
     * contacts.
     *
     * @return
     */
    public Contact getMainContact() {
        if (!getContacts().isEmpty()) {
            //return getContacts().get(0);
            for (Contact contact : getContacts()) {
                if (contact.getType().equals("Main")) {
                    return contact;
                }
            }
            // use the first found address as the billing address
            Contact contact = getContacts().get(0);
            contact.setType("Main");
            return contact;
        } else {
            Contact contact = new Contact();
            contact.setType("Main");
            getContacts().add(contact);
            return getContacts().get(0);
        }
    }

    public Contact addContact(Contact contact) {
        getContacts().add(contact);
        return getContacts().get(0);
    }

    /**
     * Returns the first found address with billing type "Billing" as the main
     * billing address.
     *
     * @return
     */
    public Address getDefaultAddress() {
        if (!getBillingAddresses().isEmpty()) {

            return getBillingAddresses().get(getBillingAddresses().size() - 1);

        } else if (!getAddresses().isEmpty()) {

            return getAddresses().get(getAddresses().size() - 1);

        } else {
            return new Address("", "Billing");
        }
    }

    public Address addAddress(Address address) {
        getAddresses().add(address);
        return getAddresses().get(0);
    }

    public Date getDateLastAccessed() {
        return dateLastAccessed;
    }

    public void setDateLastAccessed(Date dateLastAccessed) {
        this.dateLastAccessed = dateLastAccessed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;

        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        if (name != null) {
            return name.replaceAll("&#38;", "&");
        } else {
            return "";
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

   
    public void setType(String type) {
        this.type = type;
    }

    public static List<Supplier> findSuppliersByIdentification(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<Supplier> suppliers;
            suppliers = em.createQuery("SELECT s FROM Supplier s where UPPER(s.identification) like '"
                    + value.toUpperCase() + "%' ORDER BY s.identification", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<String> findActiveSupplierNames(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<String> names
                    = em.createQuery("SELECT s FROM Supplier s WHERE UPPER(s.name) like '"
                            + value.toUpperCase() + "%'"
                            + " AND s.active = 1"
                            + " ORDER BY s.name", String.class).getResultList();
            return names;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<Supplier> findActiveSuppliersByFirstPartOfName(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<Supplier> suppliers
                    = em.createQuery("SELECT s FROM Supplier s WHERE s.name like '"
                            + value + "%'"
                            + " AND s.active = 1"
                            + " ORDER BY s.id", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<Supplier> findActiveSuppliersByAnyPartOfName(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<Supplier> suppliers
                    = em.createQuery("SELECT s FROM Supplier s WHERE s.name like '%"
                            + value + "%'"
                            + " AND s.active = 1"
                            + " ORDER BY s.id", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<Supplier> findSuppliersByAnyPartOfName(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<Supplier> suppliers
                    = em.createQuery("SELECT s FROM Supplier s WHERE s.name like '%"
                            + value + "%'"
                            + " ORDER BY s.id", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<String> findSupplierNames(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<String> names
                    = em.createQuery("SELECT s FROM Supplier s where UPPER(s.name) like '"
                            + value.toUpperCase()
                            + "%' ORDER BY s.name", String.class).getResultList();
            return names;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<Supplier> findSuppliersByFirstPartOfName(EntityManager em, String value) {

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            List<Supplier> suppliers
                    = em.createQuery("SELECT s FROM Supplier s where UPPER(s.name) like '"
                            + value.toUpperCase()
                            + "%' ORDER BY s.name", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static List<Supplier> getAllSuppliers(EntityManager em) {

        try {
            List<Supplier> suppliers = em.createNamedQuery("findAllSuppliers", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static Supplier findSupplierByName(EntityManager em, String value, Boolean ignoreCase) {

        List<Supplier> suppliers;

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            if (ignoreCase) {
                suppliers = em.createQuery("SELECT s FROM Supplier s "
                        + "WHERE UPPER(s.name) "
                        + "= '" + value.toUpperCase() + "'", Supplier.class).getResultList();
            } else {
                suppliers = em.createQuery("SELECT s FROM Supplier s "
                        + "WHERE s.name "
                        + "= '" + value + "'", Supplier.class).getResultList();
            }

            if (!suppliers.isEmpty()) {
                return suppliers.get(0);
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static Supplier findSupplierById(EntityManager em, Long id) {

        try {
            return em.find(Supplier.class, id);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static Supplier findActiveSupplierByName(EntityManager em, String value, Boolean ignoreCase) {

        List<Supplier> suppliers;

        try {
            value = value.replaceAll("'", "''").replaceAll("&amp;", "&");

            if (ignoreCase) {
                suppliers = em.createQuery("SELECT s FROM Supplier s "
                        + "WHERE UPPER(s.name) "
                        + "= '" + value.toUpperCase() + "'"
                        + " AND s.active = 1", Supplier.class).getResultList();
            } else {
                suppliers = em.createQuery("SELECT s FROM Supplier s "
                        + "WHERE s.name "
                        + "= '" + value + "'"
                        + " AND s.active = 1", Supplier.class).getResultList();
            }

            if (!suppliers.isEmpty()) {
                return suppliers.get(0);
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public static Supplier findActiveDefaultSupplier(
            EntityManager em,
            String name,
            Boolean useTransaction) {
        
        Supplier supplier = findActiveSupplierByName(em, name, false);

        if (supplier == null) {
            supplier = new Supplier(name);
            supplier.setActive(true);
            supplier.setInternet(Internet.findDefaultInternet(em, "--", useTransaction));
            supplier.setEnteredBy(Employee.findDefaultEmployee(em, "--", "--", useTransaction));

            if (useTransaction) {
                em.getTransaction().begin();
                BusinessEntityUtils.saveBusinessEntity(em, supplier);
                em.getTransaction().commit();
            } else {
                BusinessEntityUtils.saveBusinessEntity(em, supplier);
            }
        }

        return supplier;
    }

    public static List<Supplier> findSuppliersBySearchPattern(EntityManager em, String searchPattern) {

        try {
            List<Supplier> suppliers = em.createQuery("SELECT s FROM Supplier s "
                    + "WHERE UPPER(s.name) "
                    + "LIKE '" + searchPattern.toUpperCase() + "%' "
                    + "ORDER BY s.name", Supplier.class).getResultList();
            return suppliers;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ReturnMessage save(EntityManager em) {
        try {
            // Save contacts and addresses
            for (Contact contact : getContacts()) {
                if (contact.getId() == null) {
                    contact.save(em);
                }
            }
            for (Address address : getAddresses()) {
                if (address.getId() == null) {
                    address.save(em);
                }
            }

            em.getTransaction().begin();
            BusinessEntityUtils.saveBusinessEntity(em, this);
            em.getTransaction().commit();

            return new ReturnMessage();
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ReturnMessage(false, "Supplier not saved");
    }

    @Override
    public ReturnMessage validate(EntityManager em) {
        return new ReturnMessage();
    }

    @Override
    public int compareTo(Object o) {
        return Collator.getInstance().compare(this.getName(), ((Supplier) o).getName());
    }
}
