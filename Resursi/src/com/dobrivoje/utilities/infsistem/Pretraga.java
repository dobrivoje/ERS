/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.infsistem;

/**
 *
 * @author root
 */
public class Pretraga {

    public static enum NACIN_PRETRAGE {

        KOMPLETNO,
        DELIMICNO
    }

    public static enum RADNI_NALOG_NACIN_PRETRAGE {

        RADNI_NALOG,
        OTVOREN,
        ZATVOREN_NEFAKTURISAN,
        FAKTURISAN,
        STORNO
    }

    public static enum DATUM_PRETRAGE {

        PO_DATUMU,
        BEZ_DATUMA
    }

    public static enum AUTO_PRETRAGA {

        SASIJA,
        REGISTRACIJA,
        NAZIV,
        SIFRA,
        PIB,
        MATBR
    };

    public static enum RADNINALOG_VIZUELNA_REPREZENTACIJA {

        RN_FAKTURA,
        AUTOMOBIL_BREND_IKONA,
        RADOVI_NA_AUTOMOBILU,
        GARANCIJE
    };

    public static enum SIFARNIK_PRETRAGA {

        KUPAC_PO_NAZIVU,
        KUPAC_PO_SIFRI,
        KUPAC_PO_PIB,
        KUPAC_PO_MATBR
    };
}
