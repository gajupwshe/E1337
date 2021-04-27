/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
/**
 *
 * @author nsp
 */
public class Session {
    private static Preferences session = Preferences.userNodeForPackage(Session.class);
    
    //session set 
    public static void set(String key, String value){
        
        session.put(key, value);
        System.out.println("Session set for " +key + " " +value);
    }
    
    //session get
    public  static String get(String key){
        
        return session.get(key, null);
    
    }
    
    //session destroy 
    public static void destroy(String key){
        
        session.remove(key);
    }
           
    //session destroy for All
     public static void destroyAll(String key){
     
         try {
             session.clear();
         } catch (BackingStoreException ex) {
             Logger.getLogger(Session.class.getName()).log(Level.SEVERE,null,ex);
             
         }
     }

    static void distroyAll() {
       try {
             session.clear();
         } catch (BackingStoreException ex) {
             Logger.getLogger(Session.class.getName()).log(Level.SEVERE,null,ex);
             
         }
    }
    
}
