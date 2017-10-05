/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import jm.com.dpbennett.business.entity.BusinessEntity;
import jm.com.dpbennett.business.entity.utils.BusinessEntityUtils;

/**
 *
 * @author desbenn
 */
@FacesValidator("businessEntityValidator")
public class BusinessEntityValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        BusinessEntity entity = (BusinessEntity) value;
        if (entity != null) {
            if (!BusinessEntityUtils.validateName(entity.getName())) {
                throw new ValidatorException(getMessage(component.getId()));
            } 
        }
        else {
            throw new ValidatorException(getMessage(component.getId()));
        }

    }

    private FacesMessage getMessage(String componentId) {
        switch (componentId) { 
            case "businessOffice": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Business Office.", null);
            case "jobClassification": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Job Classification.", null);    
            case "client": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Client.", null);    
            case "billingAddress": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Billing Address.", null);           
            case "clientContact": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Contact.", null);               
            case "department": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Department.", null);    
            case "subContractedDepartment": 
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid Subcontracted Department.", null);      
            default:
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter all required (*) fields.", null);
        }
    }
}
