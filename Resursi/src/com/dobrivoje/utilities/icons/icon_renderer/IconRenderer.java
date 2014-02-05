/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.icons.icon_renderer;

import com.dobrivoje.utilities.icons.icon_renderer.IconType.Automobil;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Automobil.SASIJA;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Automobil.TABLICE;
import com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar;
import com.dobrivoje.utilities.icons.icon_renderer.IconType.RadniNalog;
import com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne;
import com.dobrivoje.utilities.icons.icon_renderer.IconType.Wallpapers;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Wallpapers.APP_WALLPAPER;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Wallpapers.WINDOW_WALLPAPER;

/**
 *
 * @author dobri
 */
public class IconRenderer {

    private static IconRenderer instance;
    private static String IconURL = "";

    public static String getIconURL() {
        return IconURL;
    }

    private IconRenderer() {
    }

    public static IconRenderer getDefault() {
        return (instance == null
                ? instance = new IconRenderer()
                : instance);
    }

    //<editor-fold defaultstate="collapsed" desc="Brand Icons Generator">
    public void generateIcons(ProfCentar pc, INodeIconRenderer iNodeIconRenderer) {

        switch (pc) {
            case KOMPANIJA:
                IconURL = "ikonice/kompanija/komp16.gif";
                break;
            case FIRMA:
                IconURL = "ikonice/firma/firma16.gif";
                break;
            case ORGJED:
                IconURL = "ikonice/orgjed/OrgJed16.gif";
                break;
            case RADNIK:
                IconURL = "ikonice/users/user4.24x24.gif";
                break;
            case RADDAN:
                IconURL = "ikonice/raddan/8.16x16.gif";
                break;
            default:
                IconURL = "ikonice/auto.16x16.jpg";
        }

        iNodeIconRenderer.node_setIconBaseWithExtension(IconURL);
    }

    public void generateIcons(Univerzalne u, INodeIconRenderer iNodeIconRenderer) {

        switch (u) {
            case OK:
                IconURL = "ikonice/ok.gif";
                break;
            case RADOVI:
                IconURL = "ikonice/raddan/radovi.jpg";
                break;
            case STRANKE:
                IconURL = "ikonice/stranke/stranka2.jpg";
                break;
            case RACUNI:
                IconURL = "ikonice/racuni/racun1.jpg";
                break;
            default:
                IconURL = "";
        }

        iNodeIconRenderer.node_setIconBaseWithExtension(IconURL);
    }

    public void generateIcons(Automobil a, INodeIconRenderer iNodeIconRenderer) {

        switch (a) {
            case SASIJA:
                IconURL = "ikonice/auto/car2.jpg";
                break;
            case TABLICE:
                IconURL = "ikonice/auto/car2.jpg";
                break;
            default:
                IconURL = "ikonice/auto/car2.jpg";
        }

        iNodeIconRenderer.node_setIconBaseWithExtension(IconURL);
    }

    public void generateIcons(Wallpapers w, INodeIconRenderer iNodeIconRenderer) {

        switch (w) {
            case APP_WALLPAPER:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/passat-cc3.croped.3.jpg";
                break;
            case WINDOW_WALLPAPER:
                break;
        }

        iNodeIconRenderer.node_setIconBaseWithExtension(IconURL);
    }

    public void generateIcons(RadniNalog r, INodeIconRenderer iNodeIconRenderer) {

        switch (r) {
            case RADNINALOG:
                IconURL = "ikonice/raddan/5.16x16.gif";
                break;
            case FAKTURA:
                IconURL = "ikonice/raddan/7.16x16.gif";
                break;
        }

        iNodeIconRenderer.node_setIconBaseWithExtension(IconURL);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generator wallpaper-a">
    public String getWallpaper(Wallpapers w) {

        switch (w) {
            case APP_WALLPAPER:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/passat-cc-interior.jpg";
                break;
            case APP_WALLPAPER2:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/passat-cc1.jpg";
                break;
            case APP_WALLPAPER3:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/passat-cc2.jpg";
                break;
            case KPI_WALLPAPER4:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/wallpaper.1400.gif";
                break;
            case WINDOW_WALLPAPER:
                IconURL = "com/dobrivoje/utilities/cars/wallpapers/passat-cc3.croped.3.jpg";
                break;
        }

        return IconURL;
    }
    //</editor-fold>
}
