package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class ErrepikatuakContainer {
	
	private Bezeroa errepikatzailea;
	private Bezeroa errepikatua;
	private Errepikapena errepikapena;
	
	public ErrepikatuakContainer(Errepikapena errepikatzailea) {
		this.errepikapena=errepikatzailea;
		this.errepikatzailea=errepikatzailea.getNork();
		this.errepikatua=errepikatzailea.getNori();
	}
	public ErrepikatuakContainer() {
		this.errepikapena=null;
		this.errepikatzailea=null;
	}
	public Errepikapena getErrepikapena() {
		return this.errepikapena;
	}
	public Bezeroa getErrepikatzailea() {
		return this.errepikatzailea;
	}
	public Bezeroa getErrepikatua() {
		return this.errepikatua;
	}

}