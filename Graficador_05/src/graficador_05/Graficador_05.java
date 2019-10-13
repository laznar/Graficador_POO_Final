
package graficador_05;

import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Graficador_05 {

    
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
            list.add("B2C Development ");
            list.add("Functional requirements");
            list.add("Design");
            list.add("Implementation");
            list.add("Deploy");
            
            System.out.println(list.get(4));
            list.set(4, "Programming");
            System.out.println("Size "+list.size());
            System.out.println(list);
    }
    
}
