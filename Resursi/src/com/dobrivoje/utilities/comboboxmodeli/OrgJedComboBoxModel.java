/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.comboboxmodeli;

import ERS.queries.ERSQuery;
import ent.Orgjed;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author dobri
 */
public class OrgJedComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private final List<Orgjed> orgjedinice;
    private Orgjed selectedItem = null;

    public OrgJedComboBoxModel() {
        orgjedinice = ERSQuery.listaSvihORGJED();
    }

    public OrgJedComboBoxModel(List<Orgjed> orgjedinice) {
        this.orgjedinice = orgjedinice;
    }

    @Override
    public int getSize() {
        return (this.orgjedinice != null ? this.orgjedinice.size() : 0);
    }

    @Override
    public Orgjed getElementAt(int i) {
        return this.orgjedinice.get(i);
    }

    @Override
    public void setSelectedItem(Object s) {
        selectedItem = (Orgjed) s;
    }

    @Override
    public Orgjed getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String toString() {
        return selectedItem.toString();
    }
}