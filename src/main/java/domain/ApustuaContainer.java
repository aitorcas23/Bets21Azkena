package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class ApustuaContainer {
	
	private Apustua apustua;
	private Bezeroa errepikatua;
	
	public ApustuaContainer(Apustua apustua) {
		this.apustua=apustua;
		this.errepikatua=apustua.getErrepikatua();
	}
	
	public ApustuaContainer() {
		this.apustua=null;
		this.errepikatua=null;
	}

	public Apustua getApustua() {
		return apustua;
	}

	public void setApustua(Apustua apustua) {
		this.apustua = apustua;
	}

	public Bezeroa getErrepikatua() {
		return errepikatua;
	}

	public void setErrepikatua(Bezeroa errepikatua) {
		this.errepikatua = errepikatua;
	}

}