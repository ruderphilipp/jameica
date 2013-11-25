/**********************************************************************
 *
 * Copyright (c) by Olaf Willuhn
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.gui.internal.views;

import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.internal.controller.LicenseControl;
import de.willuhn.jameica.gui.parts.FormTextPart;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.I18N;

/**
 * View fuer die Lizenz-Informationen
 */
public class License extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {

		I18N i18n = Application.getI18n();
		GUI.getView().setTitle(i18n.tr("Lizenzinformationen"));
    
		LicenseControl control = new LicenseControl(this);

		FormTextPart libs = control.getLibList();
		libs.paint(getParent());
  }

  /**
   * @see de.willuhn.jameica.gui.AbstractView#canBookmark()
   */
  public boolean canBookmark()
  {
    return false;
  }

}
