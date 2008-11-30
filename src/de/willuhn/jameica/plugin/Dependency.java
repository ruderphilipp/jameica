/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/plugin/Dependency.java,v $
 * $Revision: 1.3 $
 * $Date: 2008/11/30 22:57:08 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.plugin;

import java.io.Serializable;
import java.util.List;

import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;


/**
 * Implementiert eine einzelne Abhaengigkeit eines Plugins zu einem anderen.
 */
public class Dependency implements Serializable
{
  private String name      = null;
  private String version   = null;
  private boolean required = true;

  /**
   * ct.
   * @param name Name des Plugins.
   * @param version Versionsnummer.
   * Kann mit einem "+" oder "-" vor der Zahl angegeben werden, wenn mindestens
   * oder hoechstens die angegebene Version vorliegen muss.
   * Der Parameter kann <code>null</code> sein, wenn die Versionsnummer egal ist.
   */
  public Dependency(String name, String version)
  {
    this(name,version,true);
  }

  /**
   * ct.
   * @param name Name des Plugins.
   * @param version Versionsnummer.
   * @param required true, wenn die Abhaengigkeit erfuellt sein MUSS (default).
   * Kann mit einem "+" oder "-" vor der Zahl angegeben werden, wenn mindestens
   * oder hoechstens die angegebene Version vorliegen muss.
   * Der Parameter kann <code>null</code> sein, wenn die Versionsnummer egal ist.
   */
  public Dependency(String name, String version, boolean required)
  {
    if (name == null)
      throw new NullPointerException("no plugin name given in dependency");
    
    this.name     = name;
    this.version  = version;
    this.required = required;
  }

  
  /**
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    return this.name + ": " + (this.version == null ? "<any>" : this.version.toString());
  }
  
  /**
   * Prueft, ob die Abhaengigkeit erfuellt ist.
   * @return true, wenn sie erfuellt ist, sonst false.
   */
  public boolean check()
  {
    // Version von Jameica selbst.
    if (this.name.equalsIgnoreCase("jameica"))
      return compareVersion(Application.getManifest().getVersion());

    // true liefern, wenn es keine Pflichtaebhaengigkeit ist
    if (!this.required)
      return true;
    
    List all = Application.getPluginLoader().getManifests();
    for (int i=0;i<all.size();++i)
    {
      Manifest mf = (Manifest) all.get(i);
      String n = mf.getName();
      if (n == null || !this.name.equals(n))
        continue;
      
      // Plugin gefunden - schauen, ob die Versionsnummer passt
      return compareVersion(mf.getVersion());
    }
    
    // Benoetigte Abhaengigkeit nicht installiert
    return false;
  }
  
  /**
   * Prueft, ob die Versionsnummer passt.
   * @param current die vorhandene Versionsnummer.
   * @return true, wenn die Bedingung erfuellt ist, sonst false.
   */
  private boolean compareVersion(double current)
  {
    // Keine bestimmte Versionsnummer gefordert.
    if (this.version == null || this.version.length() == 0)
      return true;
    
    double required = 0.0d;
    try
    {
      required = Double.parseDouble(this.version.replaceAll("[+-]",""));

      // Versionsnummer ist mit einem Minus angegeben.
      // also darf hoechstens die angegebene Versionsnummer vorhanden sein
      if (this.version.endsWith("-"))
        return current < required;
      
      // Versionsnummer mit Plus, also muss die vorhandene Version
      // gleicher ODER groesser sein
      if (this.version.endsWith("+"))
        return current >= required;

      // Kein Vorzeichen, dann muss die Versionsnummer exakt passen
      return current == required;
    }
    catch (Exception e)
    {
      Logger.error("invalid version number: " + this.version + " - " + e.getMessage());
    }
    return false;
  }
  
  /**
   * Liefert den Namen des Plugins.
   * @return Name des Plugins.
   */
  public String getName()
  {
    return this.name;
  }
  
  /**
   * Liefert die benoetigte Versionsnummer oder NULL, wenn es egal ist.
   * @return die benoetigte Versionsnummer oder NULL, wenn es egal ist.
   */
  public String getVersion()
  {
    return this.version;
  }

  /**
   * Generiert von Eclipse.
   * @see java.lang.Object#hashCode()
   */
  public int hashCode()
  {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    result = PRIME * result + ((version == null) ? 0 : version.hashCode());
    return result;
  }

  /**
   * Generiert von Eclipse.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Dependency other = (Dependency) obj;
    if (name == null)
    {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (version == null)
    {
      if (other.version != null)
        return false;
    } else if (!version.equals(other.version))
      return false;
    return true;
  }
}


/**********************************************************************
 * $Log: Dependency.java,v $
 * Revision 1.3  2008/11/30 22:57:08  willuhn
 * @N Neues optionales Attribute "required", um optionale Abhaengigkeiten abbilden zu koennen
 *
 * Revision 1.2  2008/11/11 01:09:20  willuhn
 * @N getVersion
 *
 * Revision 1.1  2008/08/27 14:41:17  willuhn
 * @N Angabe der Versionsnummer von abhaengigen Plugins oder der Jameica RT
 *
 **********************************************************************/
