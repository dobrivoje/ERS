/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author dobri
 */
public class NalogException extends Exception {

    public NalogException() {
        super();
    }

    public NalogException(String Poruka) {
        super(Poruka);
    }
}
