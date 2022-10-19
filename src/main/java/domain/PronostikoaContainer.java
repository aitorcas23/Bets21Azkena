package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType; 

@XmlAccessorType(XmlAccessType.FIELD) 
public class PronostikoaContainer {
	
	private Pronostikoa pronostikoa; 
	private Question question; 
	private Event gertaera;
	
	public PronostikoaContainer(Pronostikoa p) {
		pronostikoa=p;
		question=p.getQuestion();
		gertaera=question.getEvent(); 
    }
	
	public PronostikoaContainer() {
		pronostikoa=null;
		question=null;
		gertaera=null; 
	}

	public Pronostikoa getPronostikoa() {
		return pronostikoa;
	}

	public void setPronostikoa(Pronostikoa pronostikoa) {
		this.pronostikoa = pronostikoa;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Event getGertaera() {
		return gertaera;
	}

	public void setGertaera(Event gertaera) {
		this.gertaera = gertaera;
	}
	
	
}
