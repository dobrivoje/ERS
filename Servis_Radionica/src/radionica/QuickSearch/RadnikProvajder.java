/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.QuickSearch;

import ent.Radnik;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author dobri
 */
public class RadnikProvajder implements Lookup.Provider {

    private static RadnikProvajder instance;
    //
    private final InstanceContent ic = new InstanceContent();
    private final AbstractLookup lookup = new AbstractLookup(ic);
    //
    private static Radnik radnik;

    private RadnikProvajder() {
        if (instance == null) {
            instance = new RadnikProvajder();
            ic.add(new IRadnik() {

                @Override
                public Radnik getRadnik() {
                    return RadnikProvajder.radnik;
                }
            });
        }
    }

    public static RadnikProvajder getDefault() {
        return (instance == null ? new RadnikProvajder() : instance);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

}
