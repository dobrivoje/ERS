/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.cars.icon_renderer;

/**
 *
 * @author dobri
 */
public class ChasisIconRenderer {

    private static ChasisIconRenderer instance;
    private static String ChasisIdentifier;

    private ChasisIconRenderer() {
    }

    public static ChasisIconRenderer getDefault() {
        return (instance == null
                ? instance = new ChasisIconRenderer()
                : instance);
    }

    //<editor-fold defaultstate="collapsed" desc="Brand Icons Generator">
    public void generateIcons(String VIN, INodeIconRenderer iNodeIconRenderer) {

        try {
            ChasisIdentifier = VIN.substring(0, 3);
        } catch (StringIndexOutOfBoundsException e) {
        }

        switch (ChasisIdentifier) {
            case "WVW":
            case "WV1":
            case "WV2":
            case "WVG":
            case "1VW":
            case "3VW":
            case "9BW":
            case "EVE":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/vw.24x24.jpg");
                break;
            case "WAU":
            case "TRUE":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/audi.24x24.jpg");
                break;
            case "SB1":
            case "VNK":
            case "VAN":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/toyota24x24.gif");
                break;
            case "4US":
            case "WBA":
            case "WBS":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/bmw.24x24.jpg");
                break;
            case "VSS":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/seat.24x24.gif");
                break;
            case "TMB":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/skoda.24x24.jpg");
                break;
            case "VX1":
            case "ZFA":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/fiat.24x24.jpg");
                break;
            case "VF3":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/peugeot.24x24.jpg");
                break;
            case "VF1":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/Renault.16x16.jpg");
                break;
            case "W0L":
            case "WOL":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/opel2.gif");
                break;
            case "YV1":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/volvo2_24x24.jpg");
                break;
            case "VSK":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/nissan-logo3.24x24.jpg");
                break;
            case "WDD":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/mercedes-logo3.24x24.jpg");
                break;
            case "WF0":
            case "WFO":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/ford2.gif");
                break;
            case "JMZ":
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/automobili/brands/mazda24x24.transp.gif");
                break;
            default:
                iNodeIconRenderer.node_setIconBaseWithExtension("ikonice/auto.16x16.jpg");
        }
    }
    //</editor-fold>
}
