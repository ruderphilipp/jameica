/**********************************************************************
 * $Source: /cvsroot/jameica/jameica/src/de/willuhn/jameica/services/MessagingService.java,v $
 * $Revision: 1.1 $
 * $Date: 2008/02/13 01:04:34 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.services;

import de.willuhn.boot.BootLoader;
import de.willuhn.boot.Bootable;
import de.willuhn.boot.SkipServiceException;
import de.willuhn.jameica.messaging.LogMessageConsumer;
import de.willuhn.jameica.messaging.MessagingFactory;


/**
 * Initialisiert das Messaging-System.
 * Wird erst "on demand" gestartet.
 */
public class MessagingService implements Bootable
{
  private MessagingFactory factory = null;

  /**
   * @see de.willuhn.boot.Bootable#depends()
   */
  public Class[] depends()
  {
    return null;
  }

  /**
   * @see de.willuhn.boot.Bootable#init(de.willuhn.boot.BootLoader, de.willuhn.boot.Bootable)
   */
  public void init(BootLoader loader, Bootable caller) throws SkipServiceException
  {
    this.factory = new MessagingFactory();
    this.factory.init();
    this.factory.registerMessageConsumer(new LogMessageConsumer());
  }

  /**
   * Liefert die aktuelle MessagingFactory.
   * @return die MessagingFactory.
   */
  public MessagingFactory getMessagingFactory()
  {
    return this.factory;
  }
  
  /**
   * @see de.willuhn.boot.Bootable#shutdown()
   */
  public void shutdown()
  {
    this.factory.close();
  }

}


/**********************************************************************
 * $Log: MessagingService.java,v $
 * Revision 1.1  2008/02/13 01:04:34  willuhn
 * @N Jameica auf neuen Bootloader umgestellt
 * @C Markus' Aenderungen RMI-Registrierung uebernommen
 *
 **********************************************************************/