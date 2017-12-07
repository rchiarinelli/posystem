/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="customerAddress")
public class CustomerAddressView {

	private String id;

	private String street;
	
	private String number;
	
	private String complement;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private String country;

	/**
	 * @return the id
	 */
	@XmlElement
	public String getId() {
		return id;
	}

	/**
	 * @return the street
	 */
	@XmlElement
	public String getStreet() {
		return street;
	}

	/**
	 * @return the number
	 */
	@XmlElement
	public String getNumber() {
		return number;
	}

	/**
	 * @return the complement
	 */
	@XmlElement
	public String getComplement() {
		return complement;
	}

	/**
	 * @return the city
	 */
	@XmlElement
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	@XmlElement
	public String getState() {
		return state;
	}

	/**
	 * @return the zipCode
	 */
	@XmlElement
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @return the country
	 */
	@XmlElement
	public String getCountry() {
		return country;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @param complement the complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
}
