/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author dobri
 */
public class StatusException extends Exception {

    public StatusException() {
        super();
    }

    public StatusException(String Poruka) {
        super(Poruka);
    }
}
