package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Langilea extends Pertsona implements Serializable{
	/**
	 * 
	 */
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<ArretaElkarrizketa> arretaElkarrizketak=new Vector<ArretaElkarrizketa>();
	private int[] balorazioak=new int [5];
	
	private static final long serialVersionUID = 1L;
	
	public Langilea(){
		super();
	}

	public Langilea(String izena, String abizena1, String abizena2, String erabiltzaileIzena, String pasahitza, String telefonoZbkia, String email, Date jaiotzeData) {
		super(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, email, jaiotzeData);
	}
	
	public void addElkarrizketa(ArretaElkarrizketa elkarrizketa) {
		arretaElkarrizketak.add(elkarrizketa);
	}

	public Vector<ArretaElkarrizketa> getArretaElkarrizketak() {
		return arretaElkarrizketak;
	}
	
	public void removeElkarrizketa(ArretaElkarrizketa ae) {
		arretaElkarrizketak.remove(ae);
	}

	public void addBalorazioa(int balorazioa) {
		if (balorazioa == 1) {
			balorazioak[0]++;
		} else if (balorazioa == 2) {
			balorazioak[1]++;
		} else if (balorazioa == 3) {
			balorazioak[2]++;
		} else if (balorazioa == 4) {
			balorazioak[3]++;
		} else {
			balorazioak[4]++;
		}
	}

	public int[] getBalorazioak() {
		return this.balorazioak;
	}
	
	public int batezBestekoa() {
		if(kopurua()==0) {
			return 0;
		}else {
			return batura()/kopurua();
		}
	}
	
	public int batura() {
		return balorazioak[0]+balorazioak[1]*2+balorazioak[2]*3+balorazioak[3]*4+balorazioak[4]*5;	
	}
	
	public int kopurua() {
		return balorazioak[0]+balorazioak[1]+balorazioak[2]+balorazioak[3]+balorazioak[4];	
	}
	

}
