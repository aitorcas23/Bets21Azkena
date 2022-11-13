package businesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Apustua;
import domain.ArretaElkarrizketa;
import domain.Bezeroa;
import domain.BezeroaContainer;
import domain.BezeroartekoMezua;
import domain.Errepikapena;
import domain.ErrepikatuakContainer;
import domain.Event;
import domain.ExtendedEventIterator;
import domain.Langilea;
import domain.Mezua;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.PronostikoaContainer;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.PronosticAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businesslogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		String initialize = "initialize";
		dbManager=new DataAccess(c.getDataBaseOpenMode().equals(initialize));
		if (c.getDataBaseOpenMode().equals(initialize)) {
			dbManager.initializeDB();
			dbManager.close();
		}
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();
			
		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question,  double betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public ArrayList<Event> getEvents(Date date)  {
		dbManager.open(false);
		ArrayList<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public ArrayList<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		ArrayList<Date>  dates= dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
    @WebMethod
    public Pertsona isLogin(String erabiltzaileIzena, String pasahitza) {
    	dbManager.open(false);
		Pertsona emaitza = dbManager.isLogin(erabiltzaileIzena, pasahitza);
		dbManager.close();
		return emaitza;
    }
    
    @WebMethod
	public Pertsona register(Pertsona per, String mota) throws UserAlreadyExist{
    	dbManager.open(false);
		Pertsona emaitza = dbManager.register(per, mota);
		dbManager.close();
		return emaitza;
    }
    
    @WebMethod
    public void createEvent(String description, Date eventDate) throws EventAlreadyExist{
    	dbManager.open(false);
		dbManager.createEvent(description, eventDate);
		dbManager.close();
    }
    
    @WebMethod	
	public ArrayList<Question> getQuestions(Event event)  {
		dbManager.open(false);
		ArrayList<Question>  questions=dbManager.getQuestions(event);
		dbManager.close();
		return questions;
	}
    
    @WebMethod
    public Pronostikoa createPronostic(Question question, String description, double kuota) throws PronosticAlreadyExist{
 		dbManager.open(false);
 		Pronostikoa p=dbManager.createPronostic(question,description,kuota);		
 		dbManager.close();
 		return p;
    };
   
    @WebMethod
    public void emaitzaIpini(Question question, Pronostikoa pronostikoa){
    	dbManager.open(false);
 		dbManager.emaitzaIpini(question,pronostikoa);		
 		dbManager.close();
    }
    
    @WebMethod
    public Bezeroa apustuaEgin(List<Pronostikoa> pronostikoak, double a, Bezeroa bezero) {
    	dbManager.open(false);
    	System.out.println(bezero);
    	Bezeroa bezeroa = dbManager.apustuaEgin(pronostikoak,a,bezero);
    	dbManager.close();
    	return bezeroa;
    }
    
    @WebMethod
    public Bezeroa deleteApustua(Apustua a) throws EventFinished{ 	
    	dbManager.open(false);
    	Bezeroa bezeroa = dbManager.deleteApustua(a);
    	dbManager.close();
    	return bezeroa;
    }
    
    @WebMethod
    public Bezeroa diruaSartu(double u, Bezeroa bezero) {
    	dbManager.open(false);
    	Bezeroa bezeroa = dbManager.diruaSartu(u,bezero);
    	dbManager.close();
    	return bezeroa;
    }
    
    @WebMethod
    public void ezabatuGertaera(Event event) {
    	dbManager.open(false);
    	dbManager.ezabatuGertaera(event);
    	dbManager.close();
    }
    
    @WebMethod 
    public Bezeroa getBezeroa(String erabiltzaileIzena) {
    	dbManager.open(false);
    	Bezeroa b = dbManager.getBezeroa(erabiltzaileIzena);
    	dbManager.close();
    	return b;
    }
    
    @WebMethod 
    public Langilea getLangilea(String erabiltzaileIzena) {
    	dbManager.open(false);
    	Langilea l = dbManager.getLangilea(erabiltzaileIzena);
    	dbManager.close();
    	return l;
    }
    
    @WebMethod 
    public ArrayList<Bezeroa> getBezeroak(String username, Bezeroa bezeroa){
    	dbManager.open(false);
    	ArrayList<Bezeroa> bezeroak = dbManager.getBezeroak(username, bezeroa);
    	dbManager.close();
    	return bezeroak;
    }
    
    @WebMethod 
    public Bezeroa bidaliMezua(Bezeroa nork, Bezeroa nori, BezeroartekoMezua mezua) {
    	dbManager.open(false);
    	Bezeroa bezeroa = dbManager.bidaliMezua(nork, nori, mezua).getIgorlea();
    	dbManager.close();
    	return bezeroa;
    }
    
    @WebMethod 
    public ArrayList<Mezua> getMezuak(Bezeroa bezeroa){
    	dbManager.open(false);
    	ArrayList<Mezua> mezuak = dbManager.getMezuak(bezeroa);
    	dbManager.close();
    	return mezuak;
    }
    
    
    @WebMethod 
    public void mezuaIrakurri(Mezua mezua) {
    	dbManager.open(false);
    	dbManager.mezuaIrakurri(mezua);
    	dbManager.close();
	}
    
    @WebMethod 
    public boolean removeMezua(Mezua mezua) {
    	dbManager.open(false);
    	boolean r = dbManager.removeMezua(mezua);
    	dbManager.close();
    	return r;
    }
    
    @WebMethod 
    public Bezeroa eguneratuEzarpenak(Bezeroa b,double komisioa, boolean publikoa) {
    	dbManager.open(false);
    	Bezeroa berria = dbManager.eguneratuEzarpenak(b,komisioa,publikoa);
    	dbManager.close();
    	return berria;
    }
    
    @WebMethod 
    public void errepikatu(Bezeroa nork, Bezeroa nori, double apustatukoDena, double hilabetekoMax, double komisioa) {
    	dbManager.open(false);
    	Errepikapena a = new Errepikapena(nork,nori,apustatukoDena,hilabetekoMax,komisioa);
    	dbManager.errepikatu(a);
    	dbManager.close();
    }

	@WebMethod 
	public ArrayList<PronostikoaContainer> getPronostikoak(Apustua a){
		dbManager.open(false);
    	ArrayList<PronostikoaContainer> emaitza = dbManager.getPronostikoak(a);
    	dbManager.close();
    	return emaitza;
	}
	
	@WebMethod 
	public ArretaElkarrizketa arretaMezuaBidali(ArretaElkarrizketa elkarrizketa, String mezua, boolean langileari) {
		dbManager.open(false);
		ArretaElkarrizketa emaitza = dbManager.arretaMezuaBidali(elkarrizketa, mezua, langileari);
    	dbManager.close();
    	return emaitza;
	}

	@WebMethod 
	public ArretaElkarrizketa bezeroaEsleitu(Langilea langilea) {
		dbManager.open(false);
		ArretaElkarrizketa elkarrizketa = dbManager.bezeroaEsleitu(langilea);
    	dbManager.close();
    	return elkarrizketa;
	}
	
	@WebMethod 
	public ArretaElkarrizketa arretaElkarrizketaSortu(Bezeroa bezeroa, String gaia, String mezua) {
		dbManager.open(false);
		ArretaElkarrizketa erantzuna = dbManager.arretaElkarrizketaSortu(bezeroa, gaia, mezua);
    	dbManager.close();
    	return erantzuna;
	}
	
	@WebMethod 
	public BezeroaContainer getBezeroaContainer(Bezeroa b) {
		dbManager.open(false);
		BezeroaContainer bezeroaEguneratuta = dbManager.getBezeroaContainer(b);
    	dbManager.close();
    	return bezeroaEguneratuta;
	}
	
	@WebMethod 
	public void geldituElkarrizketa(ArretaElkarrizketa ae) {
		dbManager.open(false);
		dbManager.geldituElkarrizketa(ae);
    	dbManager.close();
	}
	
	@WebMethod
	public void amaituElkarrizketa(ArretaElkarrizketa ae) {
		dbManager.open(false);
		dbManager.amaituElkarrizketa(ae);
    	dbManager.close();
	}
	
	@WebMethod 
	public void gehituPuntuazioa(ArretaElkarrizketa l, Integer x) {
		dbManager.open(false);
		dbManager.gehituPuntuazioa(l,x);
    	dbManager.close();
	}
	
	@WebMethod
	public void eguneratuErrepikapenak() {
		dbManager.open(false);
		dbManager.eguneratuErrepikapenak();
		dbManager.close();
	}
	
	@WebMethod
	public ArrayList<Langilea> getLangileak() {
		dbManager.open(false);
		ArrayList<Langilea> list = dbManager.getLangileak();
		dbManager.close();
		return list;
	}
	
	@WebMethod
	public void jarraitzeariUtzi(Errepikapena errepikapena) {
		dbManager.open(false);
		dbManager.jarraitzeariUtzi(errepikapena);
		dbManager.close();

	}

	@WebMethod
	public ArrayList<ErrepikatuakContainer> getErrepikatzaileak(Bezeroa bezeroa) {
		dbManager.open(false);
		ArrayList<ErrepikatuakContainer> errepikapenak = dbManager.getErrepikatzaileak(bezeroa);
		dbManager.close();
		return errepikapenak;
	}

	@WebMethod
	public ArrayList<ErrepikatuakContainer> getErrepikapenak(Bezeroa bezeroa) {
		dbManager.open(false);
		ArrayList<ErrepikatuakContainer> errepikapenak = dbManager.getErrepikapenak(bezeroa);
		dbManager.close();
		return errepikapenak;
	}
	
	@WebMethod 
	public ArretaElkarrizketa getArretaElkarrizketa(Integer id) {
		dbManager.open(false);
		ArretaElkarrizketa emaitza = dbManager.getArretaElkarrizketa(id);
		dbManager.close();
		return emaitza;
	}

	public void removePertsona(String string) {
		dbManager.open(false);
		dbManager.removePertsona(string);
		dbManager.close();
	}
	
	public ExtendedEventIterator<Event> getEventIterator(Date date) {
		List<Event> list = dbManager.getEvents(date);
		ExtendedEventIterator<Event> i = new ExtendedEventIterator<Event>(list);
		return i;
	}

	public void createBLFacade(ConfigXML c) {
		// TODO Auto-generated method stub
		
	}
}

