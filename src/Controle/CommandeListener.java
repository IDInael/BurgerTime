/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Environnement.Structure;
import Joueur.Joueur;
import burgertime.BurgerTime;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;

/**
 *
 * @author dinael
 */
public class CommandeListener implements KeyListener{
    private Joueur jr;
    public static int port=2344;
    
    public CommandeListener()
    {
        this.jr=BurgerTime.j;
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        if(jr!=null)
        {
            int cmd=this.decodeCommande(e);
            
            Structure sc=jr.getStructure();//si l'objet n'est pas null
                if(sc!=null)
                {
                    if(cmd==5)
                        sc.attaque();
                    else
                        sc.setCommande(cmd);
                }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(jr!=null)
        {
            int cmd=this.decodeCommande(e);
            
            Structure sc=jr.getStructure();//si l'objet n'est pas null
                if(sc!=null)
                {
                    if(cmd==5)
                        sc.attaque();
                    else
                        sc.setCommande(cmd);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(jr!=null)
        {
            if(jr.getStructure()!=null)
                jr.getStructure().setCommande(0);
        }
    }
    
        private int decodeCommande(KeyEvent e)
    {
       // System.out.println("lecture clavier");
        int cmd=0;
        switch(e.getKeyCode())
        {
            case VK_RIGHT://fleche droite pour avancer
                cmd=1;
                break;
            case VK_DOWN://fleche bas pour descendre
                cmd=2;
                break;
            case VK_LEFT://flèche gauche pour reculer
                cmd=3;
                break;
            case VK_UP://fleche haut pour monter
                cmd=4;
                break;
            case VK_SPACE://espace pour attaquer
                cmd=5;
                break;
        }
        return cmd;
    }
    
}
