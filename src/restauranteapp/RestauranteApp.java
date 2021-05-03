/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restauranteapp;

import javax.persistence.EntityManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;

import restauranteapp.DAL.Entidade;
/**
 *
 * @author kevin
 */
public class RestauranteApp {

    /**
     * @param args the command line arguments
     */
    
 //   private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RestauranteAppPU");
 //   private static final EntityManager em = entityManagerFactory.createEntityManager();
    
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // TODO code application logic here
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
       // Entidade entidade1 = em.find(Entidade.class, 1);
        
       // System.out.println("nome do cliente: "+entidade1.getNome());
        
        Login login = new Login();
        login.setVisible(true);
    }
    
}
