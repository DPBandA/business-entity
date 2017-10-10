/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import jm.com.dpbennett.business.entity.Address;
import jm.com.dpbennett.business.entity.BusinessEntityManager;
import jm.com.dpbennett.business.entity.Client;
import jm.com.dpbennett.business.entity.Contact;
import jm.com.dpbennett.business.entity.Internet;
import jm.com.dpbennett.business.entity.JobManagerUser;
import jm.com.dpbennett.business.entity.PhoneNumber;
import jm.com.dpbennett.business.entity.utils.BusinessEntityUtils;
import org.primefaces.context.RequestContext;
import jm.com.dpbennett.business.entity.ClientOwner;
/**
 *
 * @author dbennett
 */
@Named
@SessionScoped
public class ClientManager implements Serializable, ClientManagement {

    @PersistenceUnit(unitName = "JMTSPU") // tk to be put in resource bundle
    private EntityManagerFactory localEntityManagerFactory;
    // This factory is used by external clients. May be removed in the future
    private EntityManagerFactory externalEntityManagerFactory;
    private Client client;
    private Boolean dirty;    
    private Boolean isNewContact;
    private Boolean isNewAddress;
    private Contact currentContact;
    private Address currentAddress;
    private Boolean taxRegistrationNumberRequired;
    private String dialogMessage;
    private String dialogMessageHeader;
    private String dialogMessageSeverity;
    private Boolean save;
    private Boolean clientNameAndIdEditable;
    //private BusinessEntityManager businessEntityManager;
    private Client clientBackup;
    private Boolean isNewClient;
    private String clientSearchText;
    private List<Client> foundClients;
    private ClientOwner clientOwner;
    private String clientBillingAddressString;  
    private JobManagerUser user;

    /**
     * Creates a new instance of ClientForm
     */
    public ClientManager() {
        isNewContact = false;
        isNewAddress = false;
        isNewClient = false;
        taxRegistrationNumberRequired = false;
        save = true;
        clientNameAndIdEditable = false;
        clientSearchText = "";
        foundClients = new ArrayList<>();
    }

    public Boolean getIsNewClient() {
        return isNewClient;
    }

    public void setIsNewClient(Boolean isNewClient) {
        this.isNewClient = isNewClient;
    }

    public void changeContactType(String contactString) {
        // Reset address types 
        for (Contact contact : getClient().getContacts()) {
            if (contact.getType().equals("Main")) {
                contact.setType("General");
            }
        }
        // Set new main type
        for (Contact contact : getClient().getContacts()) {
            if (contact.toString().trim().equals(contactString.trim())) {
                contact.setType("Main");
            }
        }
    }

    public void addressSelected() {
        changeBillingAddress(getCurrentAddress().toString());
    }

    public List<Address> completeClientAddress(String query) {
        List<Address> addresses = new ArrayList<>();

        try {

            for (Address address : getClient().getAddresses()) {
                if (address.toString().toUpperCase().contains(query.toUpperCase())) {
                    addresses.add(address);
                }
            }

            return addresses;
        } catch (Exception e) {

            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public List<Address> getAddressesModel() {
        return getClient().getAddresses();
    }

    public List<Contact> getContactsModel() {
        return getClient().getContacts();
    }

    public String getClientBillingAddressString() {
        return clientBillingAddressString;
    }

    public void setClientBillingAddressString(String clientBillingAddressString) {
        this.clientBillingAddressString = clientBillingAddressString;
    }

    public List<SelectItem> getClientAddresses() {
        ArrayList<SelectItem> addresses = new ArrayList<>();

        for (Address address : getClient().getAddresses()) {
            addresses.add(new SelectItem(address.toString(), address.toString()));
        }

        return addresses;
    }

    public void setClientOwner(ClientOwner clientHandler) {
        this.clientOwner = clientHandler;
    }

    public ClientOwner getClientOwner() {
        return clientOwner;
    }

    public Address getCurrentAddress() {
        if (currentAddress == null) {
            return new Address();
        }
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public Boolean getClientNameAndIdEditable() {
        return clientNameAndIdEditable;
    }

    public void setClientNameAndIdEditable(Boolean clientNameAndIdEditable) {
        this.clientNameAndIdEditable = clientNameAndIdEditable;
    }

    public EntityManager getLocalEntityManager() {
        return localEntityManagerFactory.createEntityManager();
    }

    public JobManagerUser getUser() {
        return this.user;
    }

    public void setUser(JobManagerUser user) {
        this.user = user;
    }

    // Edit the client via the ClientManager
    public void editClient() {
        setSave(true);
    }

    public List<Client> getFoundClients() {
        return foundClients;
    }

    public void setFoundClients(List<Client> foundClients) {
        this.foundClients = foundClients;
    }

    public void doClientSearch() {
        if (clientSearchText.trim().length() > 0) {
            foundClients = Client.findActiveClientsByAnyPartOfName(getLocalEntityManager(), clientSearchText);
        }
    }

    public String getClientSearchText() {
        return clientSearchText;
    }

    public void setClientSearchText(String clientSearchText) {
        this.clientSearchText = clientSearchText;
    }

    public Client getClientBackup() {
        return clientBackup;
    }

    public void setClientBackup(Client clientBackup) {
        this.clientBackup = clientBackup;
    }

//    public BusinessEntityManager getBusinessEntityManager() {
//        return businessEntityManager;
//    }
//
//    @Override
//    public void setBusinessEntityManager(BusinessEntityManager businessEntityManager) {
//        this.businessEntityManager = businessEntityManager;
//    }

    public Boolean getSave() {
        return save;
    }

    @Override
    public void setSave(Boolean save) {
        this.save = save;
    }

    public String getDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    public String getDialogMessageHeader() {
        return dialogMessageHeader;
    }

    public void setDialogMessageHeader(String dialogMessageHeader) {
        this.dialogMessageHeader = dialogMessageHeader;
    }

    public String getDialogMessageSeverity() {
        return dialogMessageSeverity;
    }

    public void setDialogMessageSeverity(String dialogMessageSeverity) {
        this.dialogMessageSeverity = dialogMessageSeverity;
    }

    public Boolean getTaxRegistrationNumberRequired() {
        if (taxRegistrationNumberRequired == null) {
            taxRegistrationNumberRequired = false;
        }
        return taxRegistrationNumberRequired;
    }

    public void setTaxRegistrationNumberRequired(Boolean taxRegistrationNumberRequired) {
        this.taxRegistrationNumberRequired = taxRegistrationNumberRequired;
    }

    @Override
    public void setExternalEntityManagerFactory(EntityManagerFactory externalEntityManagerFactory) {
        this.externalEntityManagerFactory = externalEntityManagerFactory;
    }

    public EntityManager getExternalEntityManager() {
        return externalEntityManagerFactory.createEntityManager();
    }

    public List<Address> completeAddress(String query) {
        // tk
        return client.getAddresses();
    }

    public void changeBillingAddress(String addressString) {
        // Reset address types 
        for (Address address : getClient().getAddresses()) {
            if (address.getType().equals("Billing")) {
                address.setType("General");
            }
        }
        // Set new billing type
        for (Address address : getClient().getAddresses()) {
            if (address.toString().trim().equals(addressString.trim())) {
                address.setType("Billing");
            }
        }
    }

    public void updateClient() {
        setDirty(true);
    }

    public void updateCurrentContact() {
        getClientOwner().setContact(currentContact);
        setDirty(true);
    }

    public void updateCurrentAddress() {
        getClientOwner().setBillingAddress(currentAddress);
        setDirty(true);
    }

    @Override
    public void createNewClient(Client existingClient, Client newClient) {
        if (existingClient != null) {
            clientBackup = existingClient;
        }
        client = newClient;
        getClient().setDateEntered(new Date());
        if (getUser() != null) {
            getClient().setEnteredBy(getUser().getEmployee());
        }
        isNewClient = true;
    }
    
    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
//        if (businessEntityManager != null) {
//            businessEntityManager.setDirty(dirty);
//        }
    }

    @Override
    public Client getClient() {
        if (client == null) {
            return new Client("");
        }
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        setClientBackup(new Client(getClient(), getClient().getActive()));
        clientBillingAddressString = null;
        currentAddress = getClient().getBillingAddress(); //tk
        isNewClient = false;
    }

    public Client getClientById(EntityManager em, Long Id) {

        try {
            return em.find(Client.class, Id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void cancelClientEdit(ActionEvent actionEvent) {

        if (clientBackup != null) {
            getClient().doCopy(clientBackup);
        }

        setDirty(false);
        isNewClient = false;

//        if (businessEntityManager != null) {
//            businessEntityManager.setDirty(false);
//        }

        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void createNewClient() {
        createNewClient(null, new Client("", true));
    }

    public EntityManager getEntityManager() {
        if (externalEntityManagerFactory != null) {
            return getExternalEntityManager();
        } else {
            return getLocalEntityManager();
        }
    }

    public void okClient() {
       
        EntityManager em;

        try {

            em = getEntityManager();

            if (isNewClient) {
                getClient().setDateFirstReceived(new Date());
            }
            isNewClient = false;

            if (save) {
                saveClient(em, true);
                em.close();
            }

            // Set the client of the class that invoked the Client Manager
            if (clientOwner != null) {
                clientOwner.setClient(getClient());
            }

            RequestContext.getCurrentInstance().closeDialog(null);

        } catch (Exception e) {
            isNewClient = false;
            System.out.println(e);
        }
    }

    public void saveClient(EntityManager em, Boolean useTransaction) {

        try {
            if (useTransaction) {
                em.getTransaction().begin();
            }
            // save list of objects first if they are new
            for (Contact contact : getClient().getContacts()) {
                if (contact.getId() == null) {
                    // Save objects in contact first
                    for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
                        if (phoneNumber.getId() == null) {
                            BusinessEntityUtils.saveBusinessEntity(em, phoneNumber);
                        }
                    }
                    for (Address address : contact.getAddresses()) {
                        if (address.getId() == null) {
                            BusinessEntityUtils.saveBusinessEntity(em, address);
                        }
                    }
                    BusinessEntityUtils.saveBusinessEntity(em, contact);
                }
            }
            for (Address address : getClient().getAddresses()) {
                if (address.getId() == null) {
                    BusinessEntityUtils.saveBusinessEntity(em, address);
                }
            }
            BusinessEntityUtils.saveBusinessEntity(em, getClient());

            if (useTransaction) {
                em.getTransaction().commit();
            }

            // reload from database to ensure that all entities recently saved
            // do not have null ids.
            //setClient(em.find(Client.class, getClient().getId()));

            setDirty(false);

//            if (businessEntityManager != null) {
//                businessEntityManager.setDirty(false);
//            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void removeContact() {
        getClient().getContacts().remove(currentContact);
        setDirty(true);
        currentContact = null;
    }

    public void removeAddress() {
        getClient().getAddresses().remove(currentAddress);
        setDirty(true);
        currentAddress = null;
    }

    public Contact getCurrentContact() {
        if (currentContact == null) {
            return new Contact();
        }
        return currentContact;
    }

    public void setCurrentContact(Contact currentContact) {
        this.currentContact = currentContact;
    }

    public void okCurrentContact() {
        if (isNewContact) {
            getClient().getContacts().add(currentContact);
            isNewContact = false;
        }
    }

    public void okCurrentAddress() {
        if (isNewAddress) {
            getClient().getAddresses().add(currentAddress);
            clientBillingAddressString = null;
            isNewAddress = false;
        }
        if (getCurrentAddress().getType().equals("Billing")) {
            changeBillingAddress(getCurrentAddress().toString());
        }
    }

    public void createNewContact() {
        isNewContact = true;
        currentContact = new Contact();
        currentContact.setInternet(new Internet());
        setDirty(false);
    }

    public void createNewAddress() {
        isNewAddress = true;
        currentAddress = new Address();
        currentAddress.setType("Billing");
        setDirty(false);
    }

    public List getContactTypes() { // tk put into Contacts

        throw new UnsupportedOperationException("Not supported yet: getContactTypes() to be put in Contact class");
        //return Application.getStringListAsSortableSelectItems(getEntityManager(), "personalContactTypes");

    }

    public List<Client> completeClient(String query) {
        EntityManager em = getEntityManager();

        try {
            List<Client> clients = Client.findActiveClientsByAnyPartOfName(em, query);

            closeEntityManager(em);

            return clients;

        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeEntityManager(em);
        }
    }

    public void closeEntityManager(EntityManager em) {
        if (em != null) {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
