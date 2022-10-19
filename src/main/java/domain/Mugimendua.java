package domain;

import java.io.Serializable;
import java.util.Date;

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
public class Mugimendua implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deskripzioa;
	private Date data;
	private double aldaketa;
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer identifikadorea;
	@OneToOne
	@XmlIDREF
	private Bezeroa bezeroa;
	private String mota;
	
	public Mugimendua(){
		super();
	}
	
	public Mugimendua(String deskripzioa, double aldaketa, String mota) {
		data=new Date();
		this.deskripzioa = deskripzioa;
		this.aldaketa = aldaketa;
		this.mota = mota;
	}
	
	public Mugimendua(String deskripzioa, double aldaketa, Bezeroa bezeroa, String mota) {
		data=new Date();
		this.deskripzioa = deskripzioa;
		this.aldaketa = aldaketa;
		this.bezeroa = bezeroa;
		this.mota = mota;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getIdentifikadorea() {
		return identifikadorea;
	}

	public void setIdentifikadorea(Integer identifikadorea) {
		this.identifikadorea = identifikadorea;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public void setAldaketa(double aldaketa) {
		this.aldaketa = aldaketa;
	}

	public void setBezeroa(Bezeroa bezeroa) {
		this.bezeroa = bezeroa;
	}
	
	public Mugimendua(String deskripzioa, double aldaketa, Bezeroa bezeroa, String mota, Date data) {
		this.data=data;
		this.deskripzioa = deskripzioa;
		this.aldaketa = aldaketa;
		this.bezeroa = bezeroa;
		this.mota = mota;
	}
	
	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}
	
	public double getAldaketa() {
		return aldaketa;
	}
	
	public Bezeroa getBezeroa() {
		return bezeroa;
	}
	
	public String toString() {
		return data+" : "+deskripzioa+"  /  "+aldaketa+ " €";
	}
}
