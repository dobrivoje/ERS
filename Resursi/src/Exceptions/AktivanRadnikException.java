/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author dobri
 */
public class AktivanRadnikException extends Exception {

    public AktivanRadnikException() {
        super();
    }

    public AktivanRadnikException(String Poruka) {
        super(Poruka);
    }
}
