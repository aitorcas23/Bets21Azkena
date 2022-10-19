package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso ({BezeroartekoMezua.class})
public abstract class Mezua implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer identifikadorea;
	private String mezua;
	private boolean irakurrita;
	private Date data;
	
	public Mezua(){
		super();
	}
	
	public Mezua (String mezua) {
		data=new Date();
		this.mezua=mezua;
		irakurrita=false;
	}
	
	public String getMezua() {
		return mezua;
	}

	public boolean isIrakurrita() {
		return irakurrita;
	}

	public void setIrakurrita(boolean irakurrita) {
		this.irakurrita = irakurrita;
	}

	public Date getData() {
		return data;
	}

	public Integer getIdentifikadorea() {
		return identifikadorea;
	}
	
	public String toString() {
		return data.toString();
	}
}
