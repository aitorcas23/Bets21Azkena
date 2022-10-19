package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class BezeroartekoMezua extends Mezua implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne
	private Bezeroa igorlea;
	@OneToOne
	private Bezeroa hartzailea;
	private String mota;
	private double zenbatApostatu;
	private double hilabeteanZenbat;
	private double zenbatErrepikatuarentzat;
	private String gaia;
	
	public BezeroartekoMezua() {
		super();
	}
	
	public BezeroartekoMezua(String mezua, String gaia, String mota, double zenbatApostatu, double hilabeteanZenbat, double zenbatErrepikatuarentzat, Bezeroa igorlea, Bezeroa hartzailea) {
		super(mezua);
		this.gaia=gaia;
		this.mota=mota;
		this.zenbatApostatu=zenbatApostatu;
		this.hilabeteanZenbat=hilabeteanZenbat;
		this.igorlea=igorlea;
		this.hartzailea=hartzailea;
		this.zenbatErrepikatuarentzat=zenbatErrepikatuarentzat;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public double getZenbatApostatu() {
		return zenbatApostatu;
	}

	public void setZenbatApostatu(double zenbatApostatu) {
		this.zenbatApostatu = zenbatApostatu;
	}

	public double getHilabeteanZenbat() {
		return hilabeteanZenbat;
	}

	public void setHilabeteanZenbat(double hilabeteanZenbat) {
		this.hilabeteanZenbat = hilabeteanZenbat;
	}

	public Bezeroa getIgorlea() {
		return igorlea;
	}

	public Bezeroa getHartzailea() {
		return hartzailea;
	}

	public double getZenbatErrepikatuarentzat() {
		return zenbatErrepikatuarentzat;
	}

	public void setZenbatErrepikatuarentzat(double zenbatErrepikatuarentzat) {
		this.zenbatErrepikatuarentzat = zenbatErrepikatuarentzat;
	}

	public String getGaia() {
		return gaia;
	}

	public void setGaia(String gaia) {
		this.gaia = gaia;
	}

}
