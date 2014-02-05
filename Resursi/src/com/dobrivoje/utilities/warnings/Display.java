/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.warnings;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;

/**
 *
 * @author dobri
 */
public class Display {

    public static enum TIP_OBAVESTENJA {

        GRESKA,
        INFORMATIVNO,
        UPOZORENJE
    }

    public static boolean obavestenje(String poruka) {
        NotifyDescriptor nd = new NotifyDescriptor.Message(poruka);
        return DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION;
    }

    //<editor-fold defaultstate="collapsed" desc="Obaveštenje Balončić">
    public static void obavestenjeBaloncic(String naslov, String poruka, TIP_OBAVESTENJA tip_obavestenja) {
        String p;
        
        switch (tip_obavestenja) {
            case GRESKA:
                p = "ikonice/errors_warnings_info/error-triangle.gif";
                break;
            case INFORMATIVNO:
                p = "ikonice/errors_warnings_info/info.gif";
                break;
            case UPOZORENJE:
                p = "ikonice/errors_warnings_info/warning-triangle.gif";
                break;
            default:
                p = "ikonice/errors_warnings_info/warning-triangle.gif";
        }
        
        NotificationDisplayer.getDefault()
                .notify(naslov,
                        ImageUtilities.loadImageIcon(p, true),
                        poruka, null);
    }
//</editor-fold>
}
