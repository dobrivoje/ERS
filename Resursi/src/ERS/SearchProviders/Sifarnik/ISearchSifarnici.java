/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ERS.SearchProviders.Sifarnik;

import ent.infsistem.Sifarnik;
import java.util.List;

/**
 *
 * @author dobri
 */
public interface ISearchSifarnici {

    public List<Sifarnik> getSifra(String pojam);
}
