/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.datumi;

/**
 *
 * @author dobri
 */
public class SrpskiKalendar {

    private static final String[] meseciLatinica = new String[]{
        "Januar",
        "Februar",
        "Mart",
        "April",
        "Maj",
        "Jun",
        "Jul",
        "Avgust",
        "Septembar",
        "Oktobar",
        "Novembar",
        "Decembar"
    };

    public static String getMesecNazivLatinica(int Mesec) {
        return meseciLatinica[Mesec - 1];
    }
}
