/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.comboboxmodeli;

import ERS.queries.ERSQuery;
import ent.Firma;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author dobri
 */
public class FirmaComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private final List<Firma> firme;
    private Firma selectedItem = null;

    public FirmaComboBoxModel() {
        firme = ERSQuery.listaSvihFirmi();
    }

    public FirmaComboBoxModel(List<Firma> firme) {
        this.firme = firme;
    }

    @Override
    public int getSize() {
        return (this.firme != null ? this.firme.size() : 0);
    }

    @Override
    public Firma getElementAt(int i) {
        return this.firme.get(i);
    }

    @Override
    public void setSelectedItem(Object s) {
        selectedItem = (Firma) s;
    }

    @Override
    public Firma getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String toString() {
        return selectedItem.toString();
    }
}