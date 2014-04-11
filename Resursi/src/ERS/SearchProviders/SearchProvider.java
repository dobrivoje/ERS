/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ERS.SearchProviders;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author dobri
 */
public abstract class SearchProvider implements Lookup.Provider {

    private final Lookup lookup;
    private final InstanceContent ic;

    public SearchProvider() {
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public InstanceContent getInstanceContent() {
        return ic;
    }
}
