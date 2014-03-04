/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pretrazivac.beans;

/**
 *
 * @author dobri
 */
public class Kalendar {

    private int Godina;
    private int Mesec;
    private boolean noviUpisM;
    private boolean noviUpisG;

    public Kalendar(int Godina, int Mesec) {
        this.Godina = Godina;
        this.Mesec = Mesec;
        this.noviUpisM = true;
        this.noviUpisG = true;
    }

    public int getGodina() {
        return Godina;
    }

    public void setGodina(int godina) {
        if (this.Godina != godina) {
            this.Godina = godina;
            this.noviUpisG = true;
        } else {
            this.noviUpisG = false;
        }
    }

    public int getMesec() {
        return Mesec;
    }

    public void setMesec(int mesec) {
        if (this.Mesec != mesec) {
            this.Mesec = mesec;
            this.noviUpisM = true;
        } else {
            this.noviUpisM = false;
        }
    }

    public void setGM(int godina, int mesec) {
        setGodina(godina);
        setMesec(mesec);
    }

    public boolean getNoviUpis() {
        return noviUpisG || noviUpisM;
    }

    @Override
    public String toString() {
        return "Mesec-" + getMesec() + "  Godina-" + getGodina();
    }
}
