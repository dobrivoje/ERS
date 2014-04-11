/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.comboboxmodeli;

import ERS.queries.ERSQuery;
import ent.TipRadnika;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author dobri
 */
public class TipRadnikaComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private final List<TipRadnika> tipoviRadnika;
    private TipRadnika selectedItem = null;

    public TipRadnikaComboBoxModel() {
        tipoviRadnika = ERSQuery.sviTipoviRadnika();
    }

    public TipRadnikaComboBoxModel(List<TipRadnika> tipoviRadnika) {
        this.tipoviRadnika = tipoviRadnika;
    }

    @Override
    public int getSize() {
        return (this.tipoviRadnika != null ? this.tipoviRadnika.size() : 0);
    }

    @Override
    public TipRadnika getElementAt(int i) {
        return this.tipoviRadnika.get(i);
    }

    @Override
    public void setSelectedItem(Object s) {
        selectedItem = (TipRadnika) s;
    }

    @Override
    public TipRadnika getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String toString() {
        return selectedItem.toString();
    }
}