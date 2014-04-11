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
        category = "Edit",
        id = "radionica.akcije.OpenWindow"
)
@ActionRegistration(
        iconBase = "ikonice/servis/FinishFlag2.gif",
        displayName = "#CTL_OpenWindow"
)
@ActionReference(path = "Toolbars/c1_Servis_Majstori", position = 12000)
@Messages("CTL_OpenWindow=Dnevna Evidencija Svih Majstora")
public final class OpenWindowDnevnaEvidencijaSvihMajstoraAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("DnevnaEvidencijaTopComponent");
    }
}
