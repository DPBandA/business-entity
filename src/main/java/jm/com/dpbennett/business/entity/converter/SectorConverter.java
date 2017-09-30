/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.business.entity.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import jm.com.dpbennett.business.entity.Sector;

/**
 *
 * @author desbenn
 */
@FacesConverter("sectorConverter")
public class SectorConverter extends ConverterAdapter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        try {
            Sector sector = Sector.findSectorByName(getEntityManager(), value);

            if (sector == null) {
                sector = new Sector(value);
            }

            return sector;

        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fatal Error: Sector retrieval failed!", null));
        }
    }
}
