    
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.nfunk.jep.JEP;
import org.nfunk.jep.type.Complex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laznar, acrua, aserranoc
 */
public class Plot extends JPanel{
    private    JEP Evaluator;
    
    private boolean errorExpression;
    private boolean    errorNumber;
    
    private int pointsInt;
    
    private Font ft12 = new Font("Arial Narrow",Font.PLAIN,12);
    private Font ft14 = new Font("Arial Narrow",Font.PLAIN,14);
    private Font ft10  = new Font("Arial Narrow",Font.PLAIN,10);
    
    private  JTextField functionField;
    private JTextField intervalAField,intervalBField;
    
    private JScrollPane scrollPane;
    
     private JButton graphButton; 
     
    private JPanel graphicPanel;
     private JPanel controlPanel;
     private JPanel buttonPanel;
     private JPanel DisplayPanel1 = new JPanel();
     private JPanel DisplayPanel2 = new JPanel();
     
     private int gHigh,gWidth;       
    private int x0,y0;
    private int xScale,yScale;
    private int increase1,increase2;
    private int intervalA,intervalB;
    private double xmin,xmax,imgx;
    
     private BasicStroke thickness = new BasicStroke(1.5f); 
    private  float[] dash = {5.0f};
    private   BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,5.0f, dash, 0.0f);
    
    private ImageIcon imageIcon;

   
    
    public Plot(Container Container){
        imageIcon = new ImageIcon(getClass().getResource("background.jpg"));
        
        graphButton = new JButton("Graficar");
        
        intervalAField = new JTextField("",3);
        intervalBField = new JTextField("",3);
        functionField = new JTextField("",15); 
        
        gWidth = 900-10;
        gHigh = 70*690/100;
        
         graphicPanel = new GraphZ();
         controlPanel = new JPanel();
         
         scrollPane = new JScrollPane(graphicPanel);
         DisplayPanel1.setLayout(new BorderLayout());
        DisplayPanel1.add(scrollPane, BorderLayout.CENTER);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        controlPanel.setLayout(new GridLayout(4,2));
        controlPanel.add(functionField);
        controlPanel.add(graphButton);
        
        JPanel intervalPanels = new JPanel();
    
        JLabel labelA = new JLabel("Desde: ");
        JLabel labelB = new JLabel("Hasta: ");
        
        intervalPanels.setLayout(new GridLayout(1,6));
        intervalPanels.add(labelA);
        intervalPanels.add(intervalAField);
        intervalPanels.add(labelB);
        intervalPanels.add(intervalBField);    
        controlPanel.add(intervalPanels);	
        
        JPanel numPointsPanel = new JPanel();      
        controlPanel.add(numPointsPanel);
        Border colorline = BorderFactory.createLineBorder(new Color(220,220,220));
        DisplayPanel1.setBorder(colorline);
        
        TitledBorder label;
        label = BorderFactory.createTitledBorder(" Funcion ");
        label.setTitleColor(new Color(0,0,128));
        label.setTitleFont(ft12);
        
        controlPanel.setBorder(label);
        DisplayPanel1.setPreferredSize(new Dimension(gWidth,gHigh-20));	
        
        DisplayPanel2.setLayout(new BorderLayout(1,1));
        DisplayPanel2.add("Center", controlPanel);
        DisplayPanel2.add("West", buttonPanel);	 
        
        Container.setLayout(new BorderLayout());
        Container.add("North",DisplayPanel1);
        Container.add("South",DisplayPanel2);
        
        Evaluator = new JEP();
        Evaluator.addStandardFunctions();  
        Evaluator.addStandardConstants();
        Evaluator.addComplex();
        Evaluator.addFunction("sen", new org.nfunk.jep.function.Sine());
        Evaluator.addVariable("x", 0);
        Evaluator.setImplicitMul(true); 
        
        xScale=30;
        yScale=30;
        x0=gWidth/2;
        y0=gHigh/2;
        increase1=7;
        
        eventManagement evMng = new  eventManagement();	
        
		functionField.addActionListener(evMng);
		graphButton.addActionListener(evMng);
        intervalAField.addActionListener(evMng);
        intervalBField.addActionListener(evMng);
        
    }
    
    private class eventManagement implements ActionListener
{
    public void actionPerformed (ActionEvent evt)
    {
        Object source = evt.getSource ();
        if ( source == graphButton || source == functionField || source == intervalAField || source == intervalBField){
            graphicPanel.repaint();
        }
    }
    }
    
    public class GraphZ extends JPanel  implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private int     offsetX, offsetY;
    private boolean dragging;
    
    GraphZ() 
    {
        setBackground(Color.BLACK);             
        offsetX=x0; offsetY=y0;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    
    
    @Override
    public void mousePressed(MouseEvent evt)
    {
        if (dragging)
            return;
        int x = evt.getX();  
        int y = evt.getY();
        offsetX = x - x0;
        offsetY = y - y0;
        dragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        dragging = false;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        if (dragging == false)
            return;
        int x = evt.getX();   
        int y = evt.getY();
        x0 = x - offsetX;     
        y0 = y - offsetY;
        repaint();
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent evt) {
        int zoom = evt.getWheelRotation();
        yScale += -zoom;
        xScale += -zoom;
        repaint();
    }


    @Override
    public void mouseMoved(MouseEvent evt) {}
    @Override
    public void mouseClicked(MouseEvent evt) { }
    @Override
    public void mouseEntered(MouseEvent evt) { }
    @Override
    public void mouseExited(MouseEvent evt) { }

    @Override
    public void paintComponent(Graphics g)
    {
         super.paintComponent(g);
         Graficar(g, x0, y0);                      
    }
 
    
    void Graficar(Graphics ap, int xg, int yg)
    {
        ap.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), null);

        int xi=0,yi=0,xi1=0,yi1=0,numPoints=1;
        int cxmin,cxmax,cymin,cymax;
        double valxi=0.0, valxi1=0.0, valyi=0.0,valyi1=0.0;
        

       
        Graphics2D g = (Graphics2D) ap;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setFont(ft12); 
       

        
        g.draw(new Line2D.Double(xg, 10, xg, gHigh-10)); 
        g.draw(new Line2D.Double(10, yg, gWidth-10, yg));

        xmin=-1.0*xg/xScale;
        xmax=(1.0*(gWidth-xg)/xScale);
        cxmin=(int)Math.round(xmin); 
        cxmax=(int)Math.round(xmax);

        if(intervalAField.getText().equals("")){
            intervalA = cxmin;
        }
        else
            intervalA = Integer.parseInt(intervalAField.getText());
            
        if(intervalBField.getText().equals("")){
            intervalB = cxmax;
        }
        else
            intervalB = Integer.parseInt(intervalBField.getText());

        pointsInt = 99;
  
        cymin=(int)Math.round(1.0*(yg-gHigh)/yScale);
        cymax=(int)Math.round(1.0*yg/yScale);

        numPoints=gWidth; 

        g.setPaint(new Color(255,255,255)); 
        g.setFont(ft10);

       
        if(xScale>5)
        {
            for(int i=cxmin;i<=cxmax;i++)
            {   
                g.setPaint(new Color(255,255,255)); 
                g.draw(new Line2D.Double(xg+i*xScale, yg-2, xg+i*xScale , yg+2));
                if( (xg+i*xScale) != xg )
                    g.draw(new Line2D.Double(xg+i*xScale, 10, xg+i*xScale, gHigh-10));
                
                if(i>0){
                    g.setPaint(new Color(0,0,0));
                    g.drawString(""+i, xg+i*xScale-increase1, yg+12);
                }
                if(i<0){
                    g.setPaint(new Color(0,0,0));
                    g.drawString(""+i, xg+i*xScale-8, yg+12);
                }
            }
        }

        if(yScale>5)
        {
            for(int i=cymin+1;i<cymax;i++)
            { 
                g.setPaint(new Color(255,255,255));
                g.draw(new Line2D.Double(xg-2, yg-i*yScale, xg+2 , yg-i*yScale));
                if( (yg-i*yScale) != yg )
                    g.draw(new Line2D.Double(10, yg-i*yScale, gWidth-10, yg-i*yScale));
                if(i>0){
                    g.setPaint(new Color(0,0,0));
                    g.drawString(""+i, xg-12,yg-i*yScale+8 );
                }
                if(i<0){
                    g.setPaint(new Color(0,0,0));
                    g.drawString(""+i, xg-14,yg-i*yScale+8 );
                }
            }
        }
        
        g.setPaint(new Color(0,0,0));
        
        g.setStroke(thickness);
  
        Evaluator.parseExpression(functionField.getText());
        errorExpression = Evaluator.hasError(); 
        
      

        if(!errorExpression)
        {
            
            functionField.setForeground(Color.black);
  
            for(int i=(xg+intervalA*xScale);i<(xg+intervalB*yScale)-1;i++)
            {
                valxi =xmin +i*1.0/xScale;
                valxi1=xmin+(i+1)*1.0/xScale;
                Evaluator.addVariable("x", valxi);
                valyi=Evaluator.getValue();  
                Evaluator.addVariable("x", valxi1);
                valyi1 =  Evaluator.getValue();
                xi =(int)Math.round(xScale*(valxi));
                yi =(int)Math.round(yScale*valyi); 
                xi1=(int)Math.round(xScale*(valxi1));
                yi1=(int)Math.round(yScale*valyi1); 


                if(i%(100-pointsInt)==0){
                    Complex valC = Evaluator.getComplexValue();
     
                    double imgx = (double)Math.abs(valC.im()); 
                    if(dist(valxi,valyi,valxi1,valyi1)< 1000 && imgx==0)
                    {
                        g.draw(new Line2D.Double(xg+xi,yg-yi,xg+xi1,yg-yi1)); 
                    }
                }
            }
        }else{
            functionField.setForeground(Color.BLACK);
        }
        
    }
    
 
    double dist(double xA,double yA, double xB,double yB)
    {
        return (xA - xB)*(xA - xB)+(yA - yB)*(yA - yB);
    }
    }  
    
    
    
}
