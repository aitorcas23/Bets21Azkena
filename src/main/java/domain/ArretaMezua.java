package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class ArretaMezua extends Mezua implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne
	@XmlIDREF
	ArretaElkarrizketa elkarrizketa;
	boolean ikusgaiBezeroarentzat;
	boolean azkena;
	
	public ArretaMezua() {
		super();
	}
	
	public ArretaMezua(String mezua, ArretaElkarrizketa elkarrizketa) {
		super(mezua);
		this.elkarrizketa=elkarrizketa;
		ikusgaiBezeroarentzat=true;
		azkena=false;
	}

	public ArretaElkarrizketa getElkarrizketa() {
		return elkarrizketa;
	}

	public boolean isIkusgaiBezeroarentzat() {
		return ikusgaiBezeroarentzat;
	}

	public void setIkusgaiBezeroarentzat(boolean ikusgaiBezeroarentzat) {
		this.ikusgaiBezeroarentzat = ikusgaiBezeroarentzat;
	}

	public boolean isAzkena() {
		return azkena;
	}

	public void setAzkena(boolean azkena) {
		this.azkena = azkena;
	}
	
	
}
