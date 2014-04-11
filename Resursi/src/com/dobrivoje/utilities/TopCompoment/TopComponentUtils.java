/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.TopCompoment;

import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author dobri
 */
public class TopComponentUtils {

    public static void OpenTopComponent(String TCID) {
        TopComponent tc = WindowManager
                .getDefault()
                .findTopComponent(TCID);

        if (!tc.isOpened()) {
            tc.open();
        }

        tc.requestVisible();
        tc.requestActive();
    }
}
