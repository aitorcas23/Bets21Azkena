package exceptions;
public class EventAlreadyExist extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EventAlreadyExist()
  {
    super();
  }
  /**This exception is triggered if the event already exists 
  *@param s String of the exception
  */
  public EventAlreadyExist(String s)
  {
    super(s);
  }
}