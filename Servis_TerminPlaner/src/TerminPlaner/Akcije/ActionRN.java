/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.Akcije;

import ent.infsistem.Nald001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import node_klase.interfejsi.actionable.IActionableRN;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
        category = "Menu/Akcije/Radni Nalozi",
        id = "izvestaji.akcije.ActionRN"
)
@ActionRegistration(
        iconBase = "ikonice/izvestaji/plava.gif",
        displayName = "#CTL_ActionRN"
)
@ActionReference(path = "Menu/Akcije/Radni Nalozi", position = 3333, separatorBefore = 3283, separatorAfter = 3383)
@Messages("CTL_ActionRN=Akcija za Radni Nalog")
public final class ActionRN implements ActionListener {

    private Lookup.Result<Nald001> rnalozi;
    private final IActionableRN context;

    public ActionRN(IActionableRN context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        rnalozi = Utilities.actionsGlobalContext().lookupResult(Nald001.class);
        rnalozi.addLookupListener(new LookupListener() {

            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<? extends Nald001> nalozi = lr.allInstances();

                if (!nalozi.isEmpty()) {
                    for (Nald001 n : nalozi) {
                        StatusDisplayer.getDefault().setStatusText(
                                n.getRadnal() + "[" + n.getDatum() + "]"
                        );
                    }
                }
            }
        });
    }
}
