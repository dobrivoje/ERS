/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.CssStyles;

import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author dobri
 */
public class CSSStyles {

    private static String CSSFile;

    public static enum Style {

        DEFAULT,
        RED,
        GREEN,
        YELLOW
    }

    public static String getCSSStyle(Style CSSStyle) {
        switch (CSSStyle) {
            case RED:
                CSSFile = "LCG_RED.css";
                break;
            case YELLOW:
                CSSFile = "LCG_YELLOW.css";
                break;

            default:
            case GREEN:
                DEFAULT:
                CSSFile = "Default.css";
                break;
        }

        return CSSFile;
    }
}
