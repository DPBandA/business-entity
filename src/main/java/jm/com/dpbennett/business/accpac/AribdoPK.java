/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jm.com.dpbennett.business.accpac;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author DBennett
 */
@Embeddable
public class AribdoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CNTBTCH")
    private int cntbtch;
    @Basic(optional = false)
    @Column(name = "CNTITEM")
    private int cntitem;
    @Basic(optional = false)
    @Column(name = "CNTLINE")
    private int cntline;
    @Basic(optional = false)
    @Column(name = "OPTFIELD")
    private String optfield;

    public AribdoPK() {
    }

    public AribdoPK(int cntbtch, int cntitem, int cntline, String optfield) {
        this.cntbtch = cntbtch;
        this.cntitem = cntitem;
        this.cntline = cntline;
        this.optfield = optfield;
    }

    public int getCntbtch() {
        return cntbtch;
    }

    public void setCntbtch(int cntbtch) {
        this.cntbtch = cntbtch;
    }

    public int getCntitem() {
        return cntitem;
    }

    public void setCntitem(int cntitem) {
        this.cntitem = cntitem;
    }

    public int getCntline() {
        return cntline;
    }

    public void setCntline(int cntline) {
        this.cntline = cntline;
    }

    public String getOptfield() {
        return optfield;
    }

    public void setOptfield(String optfield) {
        this.optfield = optfield;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cntbtch;
        hash += (int) cntitem;
        hash += (int) cntline;
        hash += (optfield != null ? optfield.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AribdoPK)) {
            return false;
        }
        AribdoPK other = (AribdoPK) object;
        if (this.cntbtch != other.cntbtch) {
            return false;
        }
        if (this.cntitem != other.cntitem) {
            return false;
        }
        if (this.cntline != other.cntline) {
            return false;
        }
        if ((this.optfield == null && other.optfield != null) || (this.optfield != null && !this.optfield.equals(other.optfield))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jm.org.bsj.entity.accpac.AribdoPK[cntbtch=" + cntbtch + ", cntitem=" + cntitem + ", cntline=" + cntline + ", optfield=" + optfield + "]";
    }

}
