/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.comboboxmodeli;

import ERS.queries.ERSQuery;
import ent.Statusi;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author dobri
 */
public class StatusiComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private final List<Statusi> statusi;
    private Statusi selectedItem = null;

    public StatusiComboBoxModel() {
        statusi = ERSQuery.listaStatusa();
    }

    public StatusiComboBoxModel(List<Statusi> statusi) {
        this.statusi = statusi;
    }

    @Override
    public int getSize() {
        return (this.statusi != null ? this.statusi.size() : 0);
    }

    @Override
    public Statusi getElementAt(int i) {
        return this.statusi.get(i);
    }

    @Override
    public void setSelectedItem(Object s) {
        selectedItem = (Statusi) s;
    }

    @Override
    public Statusi getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String toString() {
        return selectedItem.toString();
    }
}
