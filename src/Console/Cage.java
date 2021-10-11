/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Console;

import Environnement.Plateau;
import Environnement.Structure;
import Outils.Position;
import burgertime.BurgerTime;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author 
 */
public class Cage extends JButton implements Runnable{
    Position pos;//position dans le plateau de jeu
    int port;//numero de port associé
    public Cage(Position p)
    {
        super();
        pos=p;
        super.setBorder(BorderFactory.createEmptyBorder());
        this.setContentAreaFilled(false);
        this.setBackground(new Color(11,22,22));
        
        this.setForeground(Color.white);
    }

    
    @Override
    public void run() 
    {
        Plateau p=BurgerTime.pl;
        while(true)
        {
            Structure c=p.getStructure(pos);
            if(c!=null)
            {
                //this.setText(c.getImage());
                super.setIcon(new ImageIcon(getClass().getResource("/Media/"+c.getImage()+".png")));
            }
            else
            {
                this.setText("");
                super.setIcon(null);
            }
            try
            {
                Thread.currentThread().sleep(10);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }   
    }
    
    public void setPort(int p)
    {
        this.port=p;
    }
}
