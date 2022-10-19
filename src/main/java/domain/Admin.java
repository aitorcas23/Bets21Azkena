package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends Pertsona implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Admin(){
		super();
	}

	public Admin(String izena, String abizena1, String abizena2, String erabiltzaileIzena, String pasahitza, String telefonoZbkia, String email, Date jaiotzeData) {
		super(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, email, jaiotzeData);
	}
}
