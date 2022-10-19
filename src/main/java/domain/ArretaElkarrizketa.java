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
public class ArretaElkarrizketa implements Serializable{
	
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
	private Bezeroa bezeroa;
	@OneToOne
	@XmlIDREF
	private Langilea langilea;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<ArretaMezua> langileakBidalitakoak=new Vector<ArretaMezua>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<ArretaMezua> bezeroakBidalitakoak=new Vector<ArretaMezua>();
	private String gaia;
	boolean amaituta = false;
	boolean azkenaBezeroak;
	
	public ArretaElkarrizketa() {
		super();
	}
	
	public ArretaElkarrizketa(Bezeroa bezeroa, String gaia) {
		this.bezeroa=bezeroa;
		this.gaia=gaia;
		this.langilea=null;
		amaituta=false;
	}

	public Bezeroa getBezeroa() {
		return bezeroa;
	}

	public Langilea getLangilea() {
		return langilea;
	}

	public void setLangilea(Langilea langilea) {
		this.langilea = langilea;
	}
	
	public ArretaMezua addMezua(String mezua, Boolean langileari) {
		ArretaMezua berria = new ArretaMezua(mezua, this);
		if(langileari) {
			bezeroakBidalitakoak.add(berria);
			azkenaBezeroak=true;
		}else {
			langileakBidalitakoak.add(berria);
			azkenaBezeroak=false;
		}
		return berria;
	}
	
	public void addLangileak(ArretaMezua mezua) {
		langileakBidalitakoak.add(mezua);
	}

	public Integer getIdentifikadorea() {
		return identifikadorea;
	}

	public Vector<ArretaMezua> getLangileakBidalitakoak() {
		return langileakBidalitakoak;
	}

	public Vector<ArretaMezua> getBezeroakBidalitakoak() {
		return bezeroakBidalitakoak;
	}

	public String getGaia() {
		return gaia;
	}

	public boolean isAmaituta() {
		return amaituta;
	}

	public void setAmaituta(boolean amaituta) {
		this.amaituta = amaituta;
	}
	
	public String toString() {
		return bezeroa.getErabiltzaileIzena();
	}
	
	public Vector<ArretaMezua> getMezuak(){
		Vector<ArretaMezua> emaitza = new Vector<ArretaMezua>();
		for(int i = 0; i<bezeroakBidalitakoak.size(); i++) {
			emaitza.add(bezeroakBidalitakoak.get(i));
			if(i<langileakBidalitakoak.size()) {
				emaitza.add(langileakBidalitakoak.get(i));
			}
		}
		return emaitza;
	}

	public boolean azkenaBezeroak() {
		return azkenaBezeroak;
	}
	
	public void gelditu() {
		langilea.removeElkarrizketa(this);
		langilea=null;
	}
	
	public void removeMezua(ArretaMezua am) {
		if(this.azkenaBezeroak()) {
			bezeroakBidalitakoak.remove(am);
		}else {
			langileakBidalitakoak.remove(am);
		}
	}
	
	public boolean mezurikEz() {
		return langileakBidalitakoak.isEmpty() && bezeroakBidalitakoak.isEmpty();
	}
	
	public void removeLangilearenMezua(ArretaMezua m) {
		langileakBidalitakoak.remove(m);
	}
}
