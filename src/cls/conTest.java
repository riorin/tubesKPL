/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cls;

import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author NEO
 */
public class conTest {
    public static void main(String[] args) {
        Connection con = ClassDB.getkoneksi();
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
        }
        else {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal");
        }
    }
    
}
