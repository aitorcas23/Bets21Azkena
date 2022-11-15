package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Pronostikoa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deskripzioa;
	private double kuota;
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer identifikadorea;
	@OneToOne
	@XmlIDREF
	private Question question;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apustua> apustuak=new Vector<Apustua>();
	
	public Pronostikoa(){
		super();
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public void setKuota(double kuota) {
		this.kuota = kuota;
	}

	public void setIdentifikadorea(Integer identifikadorea) {
		this.identifikadorea = identifikadorea;
	}

	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	

	public Pronostikoa(String deskripzioa, double kuota) {
		this.deskripzioa = deskripzioa;
		this.kuota = kuota;
	}

	public Pronostikoa(String deskripzioa, double kuota, Question question) {
		this.deskripzioa = deskripzioa;
		this.kuota = kuota;
		this.question = question;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}

	public double getKuota() {
		return kuota;
	}
	
	public Integer getIdentifikadorea() {
		return identifikadorea;
	}
	
	public Vector<Apustua> getApustuak(){
		return apustuak;
	}
	
	public String toString() {
		return deskripzioa + " : " + kuota;
	}
	
	public void addApustua(Apustua apustua) {
		apustuak.add(apustua);
	}
	
	public void removeApustua(Apustua a) {
		this.apustuak.remove(a);
	}
	
	public boolean equals(Pronostikoa p) {
		return p.deskripzioa.equals(this.deskripzioa) && p.question.getQuestionNumber()==question.getQuestionNumber();
	}
}
