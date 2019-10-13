package graficador_01;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Carlos
 */
public class Graficador_01 {

    
    public static void main(String[] args) {
       Set<String> set1 = new HashSet<>();
       
        set1.add("test"); 
        set1.add("sets"); 
        set1.add("on"); 
        set1.add("java"); 
        set1.add("test"); 
        System.out.print("Your set is: ");
        
        System.out.println(set1);
    }
    
}
