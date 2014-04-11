/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author dobri
 */
public class DatumException extends Exception {

    public DatumException() {
        super();
    }

    public DatumException(String Poruka) {
        super(Poruka);
    }
}
