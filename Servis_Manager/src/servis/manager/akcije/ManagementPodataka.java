/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Menu/Akcije/Firma",
        id = "servis.manager.akcije.ManagementPodataka"
)
@ActionRegistration(
        iconBase = "ikonice/servis/zastavica.gif",
        displayName = "#CTL_ManagementPodataka"
)
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Firma/Management", position = 4100, separatorBefore = 4090, separatorAfter = 4110),
    @ActionReference(path = "Toolbars/g1_Management", position = 50000)
})
@Messages("CTL_ManagementPodataka=Management Podataka")
public final class ManagementPodataka implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("ManagementPodatakaTopComponent");
    }
}
