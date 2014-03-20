/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlserver.startupcheck;

import ERS.queries.ERSQuery;
import INFSYS.Queries.INFSistemQuery;
import javax.persistence.EntityManager;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    private static EntityManager em;

    @Override
    public void restored() {
        /*
         Exception [EclipseLink-4002] (Eclipse Persistence Services - 2.3.2.v20111125-r10461): org.eclipse.persistence.exceptions.DatabaseException
         Internal Exception: java.sql.SQLException: Network error IOException: Connection refused: connect
         Error Code: 0
         */
        // postavi proveru sql serverra : 
        // java.sql.SQLException: Network error IOException: Connection refused: connect
        // Error Code: 0

        try {
            em = ERSQuery.getEm();
        } catch (Exception e) {
            NotifyDescriptor nd = new NotifyDescriptor.Message(
                    "Ne postoji veza prema SQL server bazi ERS.");

            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
                    @Override
                    public void run() {
                        LifecycleManager.getDefault().exit();
                    }
                });
            }
        }

        try {
            em = INFSistemQuery.getEm();
        } catch (Exception e) {
            NotifyDescriptor nd = new NotifyDescriptor.Message(
                    "Ne postoji veza prema SQL server bazi INF. System.");

            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
                    @Override
                    public void run() {
                        LifecycleManager.getDefault().exit();
                    }
                });
            }
        }
    }
}
