/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restauranteapp;

import UI.Login;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
/**
 *
 * @author kevin
 */
public class RestauranteApp {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // TODO code application logic here
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        Login login = new Login();
        login.setVisible(true); 
    }
    
}
