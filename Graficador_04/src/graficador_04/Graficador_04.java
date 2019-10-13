
package graficador_04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Carlos
 */
public class Graficador_04 {

    public static void main(String[] args) {
       Queue<Integer> queue = new LinkedList<>(); 
       
       String action = "";
        Scanner scan = new Scanner(System.in);
        while (!action.equals("end")) {
            System.out.println("Action: add, remove or end");
            action = scan.next();
            if (action.equals("add")) {
                System.out.println("number: ");
                int num = scan.nextInt();
                queue.add(num);
            } else if (action.equals("remove")) {
                queue.remove();
            }
        }
        
        System.out.println("Final queue");
        System.out.println(queue);
    }
    
}
