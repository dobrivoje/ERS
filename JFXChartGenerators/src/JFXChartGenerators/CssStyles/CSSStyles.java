/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.CssStyles;

/**
 *
 * @author dobri
 */
public class CSSStyles {

    private static String CSSFile;

    public static enum Style {

        // definicije za LineChart :
        DEFAULT_LINE,
        RED_LINE,
        GREEN_LINE,
        YELLOW_LINE,
        // definicije za BarChart :
        DEFAULT_BAR,
        RED_BAR,
        GREEN_BAR,
        YELLOW_BAR
    }

    public static String getCSSStyle(Style CSSStyle) {
        switch (CSSStyle) {
            case RED_LINE:
                CSSFile = "LCG_RED.css";
                break;
            case YELLOW_LINE:
                CSSFile = "LCG_YELLOW.css";
                break;
            case RED_BAR:
                CSSFile = "SBG_RED.css";
                break;
        }

        return CSSFile;
    }
}
