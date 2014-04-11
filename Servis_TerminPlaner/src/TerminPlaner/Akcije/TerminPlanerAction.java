/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.Akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "TerminPlaner.Akcije.TerminPlanerAction"
)
@ActionRegistration(
        iconBase = "ikonice/servis/TerminPlaner3.gif",
        displayName = "#CTL_TerminPlanerAction"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/g1_Management", position = 44000)
})
@Messages("CTL_TerminPlanerAction=Radni Nalozi")
public final class TerminPlanerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("RadoviNaAutomobiluTopComponent");
    }
}
