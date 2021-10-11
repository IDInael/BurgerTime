/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Console;

import burgertime.BurgerTime;
import Controle.CommandeListener;
import Outils.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author 
 */
public class Console extends JPanel{
    private CommandeListener controle;
    private int portDeb=3000;
    
    public Console()
    {
        super();
        controle=new CommandeListener();
        this.addKeyListener(controle);
        
        reset();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    
    //reinitialisation des parametres de la console
    public void reset()
    {
        int port=portDeb;
        int lig= BurgerTime.pl.getLig();//nb ligne
        int col=BurgerTime.pl.getCol();//nb colonne
        
        this.setBackground(new Color(11,22,22));
        this.setLayout(new GridLayout(lig,1));//on crée un tableau de lig ligne et 1colonne
        
        JPanel pan;//panneau provisoire
        Cage c;//cage pour l'affichage
        
        //on ajoute autant de cage qu'il y a dans le plateau
        for(int i=0;i<lig;i++)
        {
            pan= new JPanel();
            pan.setLayout(new GridLayout(1,col));//nb de colonne du plateau
            pan.setBackground(new Color(11,22,22));
            
            for(int j=0;j<col;j++)
            {
                c=new Cage(new Position(i,j));//on ajoute une cage à la position i j
                c.setPort(port);
                pan.add(c);//on ajoute la cage dans le panneau
                
                Thread th=new Thread(c);//thread affichant le contenu de chaque cage
                th.start();
                
                port++;//incrementation du numero de port pour la cage suivante
            }
            
            this.add(pan);//on ajoute le panneau dans le console
        }
        this.setPreferredSize(new Dimension(720,650));
    }
    
}
