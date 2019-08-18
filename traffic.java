import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class traffic extends Applet implements ItemListener
{
            public void paint(Graphics gr)
            {         
                       gr.drawRect(100, 35, 200, 200);

           		
                        gr.drawOval(165,40,50,50);
                        gr.drawOval(165,100,50,50);
                        gr.drawOval(165,160,50,50);
   msg=cg.getSelectedCheckbox().getLabel();
                       

if(msg.equals("red"))
                        {
                                    gr.setColor(Color.red);
                                    gr.fillOval(165,40,50,50);
                        }         
                        else if(msg.equals("yellow"))
                        {
                                    gr.setColor(Color.yellow);
                                    gr.fillOval(165,100,50,50);
                        }
                        else
                        {
                                    gr.setColor(Color.green);
                                    gr.fillOval(165,160,50,50);
                        }
           
            }
	String msg="";
            Checkbox  red,yellow,green;
            CheckboxGroup cg;
        
  public void init()
            {
                        cg = new CheckboxGroup();
                        red = new Checkbox("red", cg, false);
                        yellow = new Checkbox("yellow", cg, false);
                        green = new Checkbox("green", cg, false);
                        add(red);
                        add(yellow);
                        add(green);
                        red.addItemListener(this);
                        yellow.addItemListener(this);
                        green.addItemListener(this);
                       
            }
       public void itemStateChanged(ItemEvent ie)
            {
                        repaint();
            }

}