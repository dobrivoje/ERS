/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import node_klase.interfejsi.actionable.IActionableRadnik;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Menu/Akcije/Radnik",
        id = "servis.manager.akcije.Management_RADNIK"
)
@ActionRegistration(
        iconBase = "ikonice/izvestaji/crna.gif",
        displayName = "#CTL_Management_RADNIK"
)
@ActionReferences({
    @ActionReference(
            path = "Menu/Akcije/Radnik/Management",
            position = 4100, separatorBefore = 4090, separatorAfter = 4110)
})
@Messages("CTL_Management_RADNIK=Management Podataka")
public final class Management_RADNIK implements ActionListener {

    private final IActionableRadnik context;

    public Management_RADNIK(IActionableRadnik context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("ManagementPodatakaTopComponent");
    }
}
