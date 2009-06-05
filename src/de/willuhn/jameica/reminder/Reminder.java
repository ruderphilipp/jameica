/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/reminder/Attic/Reminder.java,v $
 * $Revision: 1.4 $
 * $Date: 2009/06/05 17:17:56 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.reminder;

import java.io.Serializable;
import java.util.Date;


/**
 * Der Container fuer einen einzelnen Reminder.
 * 
 * W I C H T I G!
 * NIEMALS VERTRAULICHE DATEN MIT EINEM REMINDER SENDEN!
 * DIE DATEN WERDEN UNVERSCHLUESSELT ALS XML-DATEI IM cfg-VERZEICHNIS
 * VON JAMEICA GESPEICHERT!
 * 
 * Das Erstellen und Speichern des Reminders geschieht beispielhaft so:
 * 
 * <pre>
 *   Date due = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L));
 *   Hashtable data = new Hashtable();
 *   data.put("foo","bar");
 *   data.put("faellig",due);
 *   Reminder reminder = new Reminder(due,null,data);
 *
 *   // Via Messaging
 *   Application.getMessagingFactory().getMessagingQueue("jameica.reminder").sendMessage(new QueryMessage(reminder));
 *   
 *   // Alternativ direkt
 *   ReminderService service = (ReminderService) Application.getBootloader().getBootable(ReminderService.class);
 *   service.add(reminder);
 * </pre>
 */
public class Reminder implements Serializable, Comparable
{

  /**
   * Generiert von Eclipse.
   */
  private static final long serialVersionUID = -5237884776673903314L;
  
  private Date dueDate        = null;
  private String action       = null;
  private String renderer     = null;
  private Serializable data   = null;

  /**
   * Konstruktor fuer Bean-Konformitaet.
   */
  public Reminder()
  {
  }
  
  /**
   * ct.
   * @param due Faelligkeitsdatum.
   * @param data die eigentlichen Nutzdaten.
   */
  public Reminder(Date due, Serializable data)
  {
    this.dueDate  = due;
    this.data     = data;
  }
  
  /**
   * Liefert das Faelligkeitsdatum.
   * @return Faelligkeitsdatum.
   */
  public Date getDueDate()
  {
    return this.dueDate;
  }
  
  /**
   * Speichert das Faelligkeitsdatum.
   * @param date das Faelligkeitsdatum.
   */
  public void setDueDate(Date date)
  {
    this.dueDate = date;
  }

  /**
   * Liefert den Klassennamen zugehoerigen Renderer.
   * @return Klassennamde des Renderers.
   */
  public String getRenderer()
  {
    return this.renderer;
  }
  
  /**
   * Speichert den Klassennamen des Renderers.
   * @param renderer Klassenname des Renderers.
   */
  public void setRenderer(String renderer)
  {
    this.renderer = renderer;
  }

  /**
   * Liefert eine Action, die bei Faelligkeit ausgefuehrt werden soll.
   * @return Klassennamde der Action.
   */
  public String getAction()
  {
    return this.action;
  }
  
  /**
   * Speichert den Klassennamen einer Action, die bei Faelligkeit ausgefuehrt werden soll.
   * Die Action wird bei Faelligkeit automatisch aufgerufen. Als Parameter wird
   * der handleAction()-Methode das Reminder-Objekt uebergeben.
   * WICHTIG: Der Reminder wird anschliessend automatisch geloescht. Falls
   * der Reminder keine Action sondern nur einen Renderer hat, muss der
   * Reminder im Gegensatz dazu manuell geloescht werden.
   * @param action Klassenname der Action.
   */
  public void setAction(String action)
  {
    this.action = action;
  }

  /**
   * Liefert die Nutzdaten.
   * @return die Nutzdaten.
   */
  public Serializable getData()
  {
    return this.data;
  }
  
  /**
   * Speichert die Nutzdaten.
   * @param data die Nutzdaten.
   */
  public void setData(Serializable data)
  {
    this.data = data;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("due-date: ");
    sb.append(this.dueDate);
    sb.append(", renderer: ");
    sb.append(this.renderer);
    sb.append(", data: ");
    sb.append(this.data);
    return sb.toString();
  }

  /**
   * @see java.lang.Object#hashCode()
   * Generiert von Eclipse.
   */
  public int hashCode()
  {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((data == null) ? 0 : data.hashCode());
    result = PRIME * result + ((dueDate == null) ? 0 : dueDate.hashCode());
    result = PRIME * result + ((renderer == null) ? 0 : renderer.hashCode());
    return result;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   * Generiert von Eclipse.
   */
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Reminder other = (Reminder) obj;
    if (data == null)
    {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    if (dueDate == null)
    {
      if (other.dueDate != null)
        return false;
    } else if (!dueDate.equals(other.dueDate))
      return false;
    if (renderer == null)
    {
      if (other.renderer != null)
        return false;
    } else if (!renderer.equals(other.renderer))
      return false;
    return true;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object other)
  {
    // wir vergleichen anhand der Faelligkeit
    if(this.dueDate == null)
      return -1;
    
    if (other == null || !(other instanceof Reminder))
      return -1;
    Date d1 = this.getDueDate();
    if (d1 == null)
      return -1;

    Date d2 = ((Reminder) other).getDueDate();
    if (d2 == null)
      return 1;
    return d1.compareTo(d2);
  }
  
  
}


/**********************************************************************
 * $Log: Reminder.java,v $
 * Revision 1.4  2009/06/05 17:17:56  willuhn
 * @N Erster Code fuer den GUI-Teil der Reminder
 *
 * Revision 1.3  2009/06/05 16:46:39  willuhn
 * @B debugging
 *
 * Revision 1.2  2008/07/18 10:41:29  willuhn
 * @N Zeitgesteuertes Ausfuehren von Reminder-Actions
 *
 * Revision 1.1  2008/07/17 23:21:27  willuhn
 * @N Generische Darstellung von Remindern mittels "Renderer"-Interface geloest. Es fehlt noch eine Box fuer die Startseite, welche die faelligen Reminder anzeigt.
 * @N Laden und Speichern der Reminder mittels XMLEncoder/XMLDecoder
 *
 **********************************************************************/
