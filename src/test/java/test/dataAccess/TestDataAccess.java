package test.dataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Admin;
import domain.ArretaElkarrizketa;
import domain.ArretaMezua;
import domain.Bezeroa;
import domain.BezeroartekoMezua;
import domain.Event;
import domain.Langilea;
import domain.Mezua;
import domain.Pertsona;
import domain.Question;
import exceptions.UserAlreadyExist;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();

	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.doesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		public ArrayList<Mezua> getMezuak(Bezeroa bezeroa){
			Bezeroa erabiltzailea = db.find(Bezeroa.class, bezeroa.getErabiltzaileIzena());
			return erabiltzailea.getMezuak();
		}
		
		public BezeroartekoMezua bidaliMezua(Bezeroa nork, Bezeroa nori, String mezua, String gaia, String mota, double zenbatApostatu, double hilabeteanZenbat, double zenbatErrepikatuarentzat) {
			Bezeroa igorlea = db.find(Bezeroa.class, nork.getErabiltzaileIzena());
			Bezeroa hartzailea = db.find(Bezeroa.class, nori.getErabiltzaileIzena());
			db.getTransaction().begin();
			BezeroartekoMezua mez = new BezeroartekoMezua(mezua, gaia, mota, zenbatApostatu, hilabeteanZenbat, zenbatErrepikatuarentzat, igorlea, hartzailea);
			BezeroartekoMezua mezuBerria = igorlea.addBidalitakoBezeroMezua(mez);
			hartzailea.addJasotakoBezeroMezua(mezuBerria);
			db.persist(mezuBerria);
			db.getTransaction().commit();
			return mezuBerria;
	    }
	
		public Pertsona register(Pertsona per, String mota) throws UserAlreadyExist{
			TypedQuery<Pertsona> query = db.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzaileIzena=?1", Pertsona.class);
			query.setParameter(1, per.erabiltzaileIzena);
			List<Pertsona> pertsona = query.getResultList();
			if(!pertsona.isEmpty()) {
				throw new UserAlreadyExist();
			}else {
				return pertsonaSortu(per, mota);
			}
		}
		
		private Pertsona pertsonaSortu(Pertsona per, String mota) {
			Pertsona berria = null;
			if(mota.equals("admin")) {
				berria = new Admin(per.izena, per.abizena1, per.abizena2, per.erabiltzaileIzena, per.pasahitza, per.telefonoZbkia, per.email, per.jaiotzeData);
			}else if (mota.equals("langilea")) {
				berria = new Langilea(per.izena, per.abizena1, per.abizena2, per.erabiltzaileIzena, per.pasahitza, per.telefonoZbkia, per.email, per.jaiotzeData);
			}else if (mota.equals("bezeroa")) {
				berria = new Bezeroa(per.izena, per.abizena1, per.abizena2, per.erabiltzaileIzena, per.pasahitza, per.telefonoZbkia, per.email, per.jaiotzeData);
			}
			db.getTransaction().begin();
			db.persist(berria);
			db.getTransaction().commit();
			return berria;
		}
		
		public boolean removeMezua(Mezua mezua) {
			if(mezua instanceof BezeroartekoMezua) {
				BezeroartekoMezua m = db.find(BezeroartekoMezua.class, mezua.getIdentifikadorea());
				Bezeroa nork = m.getIgorlea();
				Bezeroa nori = m.getHartzailea();
				db.getTransaction().begin();
				nork.ezabatuBidalitakoBezeroMezua(m);
				nori.ezabatuJasotakoBezeroMezua(m);
				db.remove(m);
				db.getTransaction().commit();
			}else {
				ArretaMezua m = db.find(ArretaMezua.class, mezua.getIdentifikadorea());
				ArretaElkarrizketa elkarrizketa = m.getElkarrizketa();
				db.getTransaction().begin();
				if(elkarrizketa.isAmaituta()) {
					elkarrizketa.removeMezua(m);
					db.remove(m);
					if(elkarrizketa.mezurikEz()) {
						db.remove(elkarrizketa);
					}
				}else {
					m.setIkusgaiBezeroarentzat(false);
					db.getTransaction().commit();
					return false;
				}
				db.getTransaction().commit();

			}
			return true;
	    }
		public void removePertsona(Pertsona p) {
			Pertsona p1 = db.find(Pertsona.class, p.getErabiltzaileIzena());
			db.getTransaction().begin();
			db.remove(p1);
			db.getTransaction().commit();
		}
		
		public ArretaElkarrizketa arretaElkarrizketaSortu(Bezeroa bezeroa, String gaia, String mezua) {
			Bezeroa erabiltzailea = db.find(Bezeroa.class, bezeroa.getErabiltzaileIzena());
			db.getTransaction().begin();
			ArretaElkarrizketa elkarrizketa = erabiltzailea.addElkarrizketa(gaia);
			elkarrizketa.addMezua(mezua, true);
			db.persist(elkarrizketa);
			db.getTransaction().commit();
			return elkarrizketa;
		}
		
		public ArretaElkarrizketa arretaMezuaBidali(ArretaElkarrizketa elkarrizketa, String mezua, boolean langileari) {
			ArretaElkarrizketa elkar = db.find(ArretaElkarrizketa.class, elkarrizketa.getIdentifikadorea());
			db.getTransaction().begin();
			elkar.addMezua(mezua, langileari);
			db.getTransaction().commit();
			return elkar;
		}
		
		public void amaituElkarrizketa(ArretaElkarrizketa ae) {
			ArretaElkarrizketa elkar = db.find(ArretaElkarrizketa.class, ae.getIdentifikadorea());
			db.getTransaction().begin();
			for(ArretaMezua am : elkar.getBezeroakBidalitakoak()) {
				db.remove(am);
			}
			elkar.getLangilea().removeElkarrizketa(elkar);
			for(ArretaMezua am : elkar.getLangileakBidalitakoak()) {
				if(!am.isIkusgaiBezeroarentzat()) {
					db.remove(am);
				}else {
					am.setIrakurrita(true);
				}
			}
			ArretaMezua m = elkar.addMezua("", false);
			m.setAzkena(true);
			elkar.setAmaituta(true);
			db.getTransaction().commit();
		}
		
		public ArretaElkarrizketa bezeroaEsleitu(Langilea langilea) {
			TypedQuery<ArretaElkarrizketa> query = db.createQuery("SELECT ae FROM ArretaElkarrizketa ae WHERE ae.langilea IS NULL AND ae.amaituta=false", ArretaElkarrizketa.class);
			
			;
			List<ArretaElkarrizketa> elkarrizketak = query.getResultList();
			if(elkarrizketak.isEmpty()) {
				return null;
			}
			Langilea l = db.find(Langilea.class, langilea.getErabiltzaileIzena());
			db.getTransaction().begin();
		    ArretaElkarrizketa elkarrizketa = elkarrizketak.get(0);

		    elkarrizketa.setLangilea(l);
			l.addElkarrizketa(elkarrizketa);
			db.getTransaction().commit();
			return elkarrizketa;
		}
		
		public Langilea getLangilea(String ErabiltzaileIzena) {
			Langilea erabiltzaile = db.find(Langilea.class, ErabiltzaileIzena);
			return erabiltzaile;
		}
		
		public Bezeroa getBezeroa(String ErabiltzaileIzena) {
			Bezeroa erabiltzaile = db.find(Bezeroa.class, ErabiltzaileIzena);
			return erabiltzaile;
		}
		
		public ArretaElkarrizketa getArretaElkarrizketa(Integer id) {
			ArretaElkarrizketa emaitza = db.find(ArretaElkarrizketa.class, id);
			return emaitza;
		}
}

