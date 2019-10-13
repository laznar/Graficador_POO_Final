
package graficador03;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Carlos
 */
public class Graficador03 {
    
    
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        String action = "";
        Scanner scan = new Scanner(System.in);
        while (!action.equals("end")) {
            System.out.println("Action: push, pop or end");
            action = scan.next();
            if (action.equals("push")) {
                System.out.println("number: ");
                int num = scan.nextInt();
                stack.push(num);
            } else if (action.equals("pop")) {
                stack.pop();
            }
        }
        
        System.out.println("Final stack");
        System.out.println(stack);

    }
}
