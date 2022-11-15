package domain;

import java.io.Serializable;
import java.util.Vector;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Bezeroa extends Pertsona implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double dirua;
	private Integer jokatuak;
	private Integer irabaziak;
	private boolean publikoa;
	private double komisioAutomatikoa;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Mugimendua> mugimenduak=new Vector<Mugimendua>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apustua> apustuak=new Vector<Apustua>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<BezeroartekoMezua> bidalitakoBezeroMezuak=new Vector<BezeroartekoMezua>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<BezeroartekoMezua> jasotakoBezeroMezuak=new Vector<BezeroartekoMezua>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Errepikapena> errepikatzaileak=new Vector<Errepikapena>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Errepikapena> errepikatuak=new Vector<Errepikapena>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<ArretaElkarrizketa> arretaElkarrizketak=new Vector<ArretaElkarrizketa>();
	
	
	public Bezeroa(){
		super();
	}
	
	public void setDirua(double dirua) {
		this.dirua = dirua;
	}

	public void setMugimenduak(Vector<Mugimendua> mugimenduak) {
		this.mugimenduak = mugimenduak;
	}

	public Bezeroa(String izena, String abizena1, String abizena2, String erabiltzaileIzena, String pasahitza, String telefonoZbkia, String email, Date jaiotzeData) {
		super(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, email, jaiotzeData);
		this.dirua=0;
		jokatuak=0;
		irabaziak=0;
		publikoa=true;
		komisioAutomatikoa=-1;
	}
	
	public Mugimendua addMugimendua(String deskripzioa, double kuota, String mota) {
		eguneratuDirua(kuota);
		Mugimendua berria = new Mugimendua(deskripzioa, kuota, this, mota);
		mugimenduak.add(berria);
		return berria;
	}
	
	public Mugimendua addMugimendua(String deskripzioa, double kuota, String mota, Date data) {
		eguneratuDirua(kuota);
		Mugimendua berria = new Mugimendua(deskripzioa, kuota, this, mota, data);
		mugimenduak.add(berria);
		if(mota=="irabazi") {
			irabaziak++;
		}
		return berria;
	}
	
	public void eguneratuDirua(double zenbatekoa) {
		dirua+=zenbatekoa;
	}
	
	public double getDirua() {
		return dirua;
	}
	
	public Vector<Mugimendua> getMugimenduak(){
		return mugimenduak;
	}
	
	public Apustua addApustua(Vector<Pronostikoa> pronostikoak, double dirua, Bezeroa nori) {
		Apustua berria = new Apustua(dirua, this, pronostikoak, nori);
		apustuak.add(berria);
		jokatuak=jokatuak+1;
		return berria;
	}
	
	public void removeApustua(Apustua apustua) {
		this.apustuak.remove(apustua);
		jokatuak=jokatuak-1;
	}
	
	public Vector<Apustua> getApustuak(){
		return apustuak;
	}
	
	public Vector<Double> getEtekinInformazioa(){
		Vector<Double> emaitza = new Vector<Double>();
		double irabazia, jokatua, etekina;
		irabazia=0;
		jokatua=0;
		for(Mugimendua mugimendua : mugimenduak) {
			if(mugimendua.getMota().equals("irabazi")) {
				irabazia+=mugimendua.getAldaketa();
			} else
				jokatua = getMugimenduetanJokatua(jokatua, mugimendua);
		}
		etekina=irabazia-jokatua;
		emaitza.add(jokatua);
		emaitza.add(irabazia);
		emaitza.add(etekina);
		return emaitza;
	}

	private double getMugimenduetanJokatua(double jokatua, Mugimendua mugimendua) {
		if(mugimendua.getMota().equals("bueltatu")) {
			jokatua-=mugimendua.getAldaketa();
		}else if(mugimendua.getMota().equals("jokatu")){
			jokatua-=mugimendua.getAldaketa();
		}
		return jokatua;
	}

	public Integer getJokatuak() {
		return jokatuak;
	}

	public void setJokatuak(Integer jokatuak) {
		this.jokatuak = jokatuak;
	}

	public Integer getIrabaziak() {
		return irabaziak;
	}

	public void setIrabaziak(Integer irabaziak) {
		this.irabaziak = irabaziak;
	}
	
	public String toString() {
		return erabiltzaileIzena;
	}
	
	public BezeroartekoMezua addBidalitakoBezeroMezua(BezeroartekoMezua mezua) {
		BezeroartekoMezua bidalitakoMezua = new BezeroartekoMezua(mezua.getMezua(), mezua.getGaia(), mezua.getMota(), mezua.getZenbatApostatu(), mezua.getHilabeteanZenbat(), mezua.getZenbatErrepikatuarentzat(), this, mezua.getHartzailea());
		bidalitakoBezeroMezuak.add(bidalitakoMezua);
		return bidalitakoMezua;
	}
	
	public Vector<BezeroartekoMezua> getBidalitakoBezeroMezuak() {
		return bidalitakoBezeroMezuak;
	}

	public Vector<BezeroartekoMezua> getJasotakoMezuak() {
		return jasotakoBezeroMezuak;
	}

	public void addJasotakoBezeroMezua(BezeroartekoMezua jasotakoMezua) {
		jasotakoBezeroMezuak.add(jasotakoMezua);
	}
	
	public void ezabatuBidalitakoBezeroMezua(BezeroartekoMezua mezua) {
		bidalitakoBezeroMezuak.remove(mezua);
	}
	
	public void ezabatuJasotakoBezeroMezua(BezeroartekoMezua mezua) {
		jasotakoBezeroMezuak.remove(mezua);
	}
	
	public boolean baduMezua(Bezeroa bestea) {
		//erabiltzaileak badu mezuren bat besteak bidalitakoa irakurri gabe.
		for(BezeroartekoMezua m : bidalitakoBezeroMezuak) {
			if(m.getHartzailea().getErabiltzaileIzena().equals(bestea.getErabiltzaileIzena()) &&  !m.isIrakurrita()) {
				return true;
			}
		}
		//besteak badu mezuren bat erabiltzaileak bidalitakoa irakurri gabe.
		for(BezeroartekoMezua m : jasotakoBezeroMezuak) {
			if(m.getIgorlea().getErabiltzaileIzena().equals(bestea.getErabiltzaileIzena()) && !m.isIrakurrita()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean errepikapenErlazioaDu(Bezeroa bestea) {
		for(Errepikapena e : errepikatuak) {
			if(e.getNori().getErabiltzaileIzena().equals(bestea.getErabiltzaileIzena())) {
				return true;
			}
		}
		for(Errepikapena e : errepikatzaileak) {
			if(e.getNork().getErabiltzaileIzena().equals(bestea.getErabiltzaileIzena())) {
				return true;
			}
		}
		return false;
	}

	public boolean isPublikoa() {
		return publikoa;
	}

	public void setPublikoa(boolean publikoa) {
		this.publikoa = publikoa;
	}

	public double getKomisioAutomatikoa() {
		return komisioAutomatikoa;
	}

	public void setKomisioAutomatikoa(double komisioAutomatikoa) {
		this.komisioAutomatikoa = komisioAutomatikoa;
	}
	
	public void eguneratuEzarpenak(boolean publikoa, double komisioAutomatikoa) {
		this.publikoa = publikoa;
		this.komisioAutomatikoa = komisioAutomatikoa;
	}
	
	public Errepikapena addErrepikatzailea(Bezeroa nork, double apustatukoDena, double hilabetekoMax, double komisioa) {
		Errepikapena errepikapenBerria = new Errepikapena(nork, this, apustatukoDena, hilabetekoMax,komisioa);
		this.errepikatzaileak.add(errepikapenBerria);
		return errepikapenBerria;
	}
	
	public void addErrepikatua(Errepikapena e) {
		this.errepikatuak.add(e);
	}

	public Vector<Errepikapena> getErrepikatzaileak() {
		return errepikatzaileak;
	}

	public Vector<Errepikapena> getErrepikatuak() {
		return errepikatuak;
	}
	
	public Errepikapena getErrepikapena(Bezeroa bez) {
		String erabIzena=bez.getErabiltzaileIzena();
		for (Errepikapena erre:errepikatuak) {
			String erabiltzaile=erre.getNori().getErabiltzaileIzena();
			if (erabiltzaile.equals(erabIzena)) {
				return erre;
			}
		}
		return null;
	}
	
	public Apustua baduApustua(Apustua apus) {
		for (Apustua apustu : apustuak) {
			System.out.println(apustu.getErrepikatua()+" "+apus.getBezeroa());
			if (apustu.equals(apus) && apustu.getErrepikatua()!=null && apustu.getErrepikatua().getErabiltzaileIzena().equals(apus.getBezeroa().getErabiltzaileIzena())) {
				return apustu;
			}
		}
		return null;
	}
	
	public boolean baduElkarrizketa() {
		for(ArretaElkarrizketa ae : arretaElkarrizketak) {
			if(!ae.isAmaituta()) {
				return true;
			}
		}
		return false;
	}
	
	public ArretaElkarrizketa addElkarrizketa(String gaia) {
		ArretaElkarrizketa berria = new ArretaElkarrizketa(this, gaia);
		this.arretaElkarrizketak.add(berria);
		return berria;
	}
	
	public Vector<Mezua> getMezuak() {
		Vector<Mezua> emaitza = new Vector<Mezua>();
		emaitza.addAll(jasotakoBezeroMezuak);
		for(ArretaElkarrizketa ae : arretaElkarrizketak) {
			for(ArretaMezua am : ae.getLangileakBidalitakoak()) {
				if(am.isIkusgaiBezeroarentzat()) {
					emaitza.add(am);
				}
			}
		}
		int zaharrenaPos;
		Mezua lag;
		for(int i = 0; i<emaitza.size(); i++) {
			zaharrenaPos = i;
			for(int j = i; j<emaitza.size(); j++) {
				if(emaitza.get(j).getData().before(emaitza.get(zaharrenaPos).getData())) {
					zaharrenaPos = j;
				}
			}
			lag=emaitza.get(i);
			emaitza.set(i, emaitza.get(zaharrenaPos));
			emaitza.set(zaharrenaPos, lag);
		}
		return emaitza;
	}

	public Vector<ArretaElkarrizketa> getArretaElkarrizketak() {
		return arretaElkarrizketak;
	}

	public void removeElkarrizketa(ArretaElkarrizketa ae) {
		arretaElkarrizketak.remove(ae);
	}

	public void ezabatuErrepikatua(Errepikapena errepikatua) {
		errepikatuak.remove(errepikatua);
	}

	public void ezabatuErrepikatzailea(Errepikapena errepikatzailea) {
		errepikatzaileak.remove(errepikatzailea);
	}
	
	public Errepikapena noriErrepikatu(Bezeroa bez) {
		Errepikapena res = null;
		String erabIzena = bez.getErabiltzaileIzena();
		for (Errepikapena erre : errepikatuak) {
			String erabiltzaile = erre.getNori().getErabiltzaileIzena();
			if (erabiltzaile.equals(erabIzena)) {
				res = erre;
			}
		}
		return res;
	}
}