
package graficador_02;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Carlos
 */
public class Graficador_02 {

   
    public static void main(String[] args) {
        Dictionary dic = new Hashtable();
        
        dic.put("123", "numbers");
        dic.put("ABC", "capLetters");
        dic.put("abc", "lowCaseLetters");
        
        System.out.println(dic.get("123"));
        System.out.println(dic.get("ABC"));
        System.out.println(dic.get("abc"));
        
        
        
    }
    
}
