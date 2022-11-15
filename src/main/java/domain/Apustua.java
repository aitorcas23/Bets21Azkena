package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Apustua implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double kopurua;
	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer identifikadorea;
	@XmlIDREF
	private Bezeroa bezeroa;
	@XmlIDREF
	private Bezeroa errepikatua;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Pronostikoa> pronostikoak=new Vector<Pronostikoa>();
	private Integer pronostikoKop;
	private Integer asmatutakoKop;
	private double kuotaTotala;
	
	public Apustua(){
		super();
	}
	
	public Apustua(double kopurua, Bezeroa bezeroa, Vector<Pronostikoa> pronostikoak, Bezeroa errepikatua) {
		this.kopurua = kopurua;
		this.bezeroa = bezeroa;
		this.pronostikoak = pronostikoak;
		this.pronostikoKop = pronostikoak.size();
		asmatutakoKop=0;
		double kuota = 1;
		for(Pronostikoa p : pronostikoak) {
			kuota=kuota*p.getKuota();
		}
		kuotaTotala=kuota;
		this.errepikatua=errepikatua;
	}
	
	public Integer getPronostikoKop() {
		return pronostikoKop;
	}

	public void setPronostikoKop(Integer pronostikoKop) {
		this.pronostikoKop = pronostikoKop;
	}

	public Integer getAsmatutakoKop() {
		return asmatutakoKop;
	}

	public void setAsmatutakoKop(Integer asmatutakoKop) {
		this.asmatutakoKop = asmatutakoKop;
	}

	public double getKuotaTotala() {
		return kuotaTotala;
	}

	public void setKuotaTotala(double kuotaTotala) {
		this.kuotaTotala = kuotaTotala;
	}

	public void setKopurua(double kopurua) {
		this.kopurua = kopurua;
	}

	public void setIdentifikadorea(Integer identifikadorea) {
		this.identifikadorea = identifikadorea;
	}

	public void setBezeroa(Bezeroa bezeroa) {
		this.bezeroa = bezeroa;
	}

	public void setPronostikoak(Vector<Pronostikoa> pronostikoak) {
		this.pronostikoak = pronostikoak;
	}

	public Apustua(double kopurua) {
		this.kopurua = kopurua;
	}
	
	public double getKopurua() {
		return kopurua;
	}
	
	public Integer getIdentifikadorea() {
		return identifikadorea;
	}
	
	public Bezeroa getBezeroa() {
		return bezeroa;
	}
	
	public Vector<Pronostikoa> getPronostikoak() {
		return pronostikoak;
	}
	
	public String toString() {
		if(errepikatua!=null) {
			return identifikadorea+" ("+errepikatua+")";
		}
		return identifikadorea+"";
	}
	
	public int removePronostikoa(Pronostikoa p) {
		pronostikoKop--;
		kuotaTotala=kuotaTotala/p.getKuota();
		this.pronostikoak.remove(p);
		return pronostikoKop;
	}
	
	public boolean eguneratuAsmatutakoKop() {
		asmatutakoKop=asmatutakoKop+1;
		System.out.println(asmatutakoKop+"=="+pronostikoKop);
		return asmatutakoKop==pronostikoKop;
	}

	public Bezeroa getErrepikatua() {
		return errepikatua;
	}

	public void setErrepikatua(Bezeroa errepikatua) {
		this.errepikatua = errepikatua;
	}
	
	public boolean equals(Apustua apus) {
		if(this.pronostikoKop!=apus.getPronostikoKop()) {
			return false;
		}
		for(Pronostikoa p : pronostikoak) {
			if(!apus.baduPronostikoa(p)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean baduPronostikoa(Pronostikoa pronos) {
		for(Pronostikoa p : pronostikoak) {
			if(pronos.equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	
}
