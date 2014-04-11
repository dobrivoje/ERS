/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 *
 * @author root
 */
public class ReportGeneratorException extends Exception {

        public ReportGeneratorException() {
            super("Report generrator init failed. Either list of parameters(keys) or list of values are not equal or are nulls.");
        }
    }
