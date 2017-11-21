
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author laznar, acrua, aserranoc 
 */
public class Window extends JFrame {

   private Plot panel;

    Window() {
        setSize(900, 690);
        setTitle("Graficador POO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container Contain = getContentPane();
         panel = new Plot(Contain);

        this.add(panel);

        setVisible(true);
        setLocationRelativeTo(null);

    }
}
