
package com.service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour deleteUser complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="deleteUser"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tokenAdmin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idUser" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteUser", propOrder = {
    "tokenAdmin",
    "idUser"
})
public class DeleteUser {

    protected String tokenAdmin;
    protected int idUser;

    /**
     * Obtient la valeur de la propriété tokenAdmin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenAdmin() {
        return tokenAdmin;
    }

    /**
     * Définit la valeur de la propriété tokenAdmin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenAdmin(String value) {
        this.tokenAdmin = value;
    }

    /**
     * Obtient la valeur de la propriété idUser.
     * 
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Définit la valeur de la propriété idUser.
     * 
     */
    public void setIdUser(int value) {
        this.idUser = value;
    }

}
