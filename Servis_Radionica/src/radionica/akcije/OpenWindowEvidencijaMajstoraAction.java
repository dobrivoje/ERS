/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Menu/Akcije/Radnik",
        id = "radionica.akcije.OpenWindowEvidencijaMajstoraAction"
)
@ActionRegistration(
        iconBase = "ikonice/servis/TerminPlaner.gif",
        displayName = "#CTL_OpenWindowEvidencijaMajstoraAction"
)
@ActionReference(path = "Toolbars/c1_Servis_Majstori", position = 11000)
@Messages("CTL_OpenWindowEvidencijaMajstoraAction=Evidencija Majstora")
public final class OpenWindowEvidencijaMajstoraAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("RadnikTopComponent");
    }
}
