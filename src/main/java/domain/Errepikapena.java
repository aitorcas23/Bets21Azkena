package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Errepikapena implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer identifikadorea;
	@OneToOne
	@XmlIDREF
	private Bezeroa nork;
	@OneToOne
	@XmlIDREF
	private Bezeroa nori;
	private double hilabeteHonetanGeratzenDena;
	private double apustatukoDena;
	private double hilabetekoMax;
	private double komisioa;
	
	public Errepikapena() {
		super();
	}
	
	public Errepikapena(Bezeroa nork, Bezeroa nori, double apustatukoDena, double hilabetekoMax, double komisioa) {
		this.nork=nork;
		this.nori=nori;
		this.apustatukoDena=apustatukoDena;
		this.hilabetekoMax=hilabetekoMax;
		hilabeteHonetanGeratzenDena=hilabetekoMax;
		this.komisioa=komisioa;
	}
	
	public Bezeroa getNork() {
		return nork;
	}

	public Bezeroa getNori() {
		return nori;
	}

	public double getKomisioa() {
		return komisioa;
	}

	public void setIdentifikadorea(Integer identifikadorea) {
		this.identifikadorea = identifikadorea;
	}

	public double getApustatukoDena() {
		return apustatukoDena;
	}

	public void setApustatukoDena(double apustatukoDena) {
		this.apustatukoDena = apustatukoDena;
	}

	public double getHilabetekoMax() {
		return hilabetekoMax;
	}

	public void setHilabetekoMax(double hilabetekoMax) {
		this.hilabetekoMax = hilabetekoMax;
	}
	
	public double getHilabeteHonetanGeratzenDena() {
		return hilabeteHonetanGeratzenDena;
	}
	
	public void eguneratuHilHonetanGeratzenDena(double x) {
		hilabeteHonetanGeratzenDena+=x;
	}
	
	public void setHilHonetanGeratzenDena(double x) {
		this.hilabeteHonetanGeratzenDena = x;
	}

	public Integer getIdentifikadorea() {
		return identifikadorea;
	}

}
