/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.comboboxmodeli;

import ERS.queries.ERSQuery;
import ent.Kompanija;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author dobri
 */
public class KompanijaComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private final List<Kompanija> kompanije;
    private Kompanija selectedItem = null;

    public KompanijaComboBoxModel() {
        kompanije = ERSQuery.listaSvihKompanija();
    }

    public KompanijaComboBoxModel(List<Kompanija> kompanije) {
        this.kompanije = kompanije;
    }

    @Override
    public int getSize() {
        return (this.kompanije != null ? this.kompanije.size() : 0);
    }

    @Override
    public Kompanija getElementAt(int i) {
        return this.kompanije.get(i);
    }

    @Override
    public void setSelectedItem(Object s) {
        selectedItem = (Kompanija) s;
    }

    @Override
    public Kompanija getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String toString() {
        return selectedItem.toString();
    }
}