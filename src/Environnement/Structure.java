/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Joueur.Joueur;
import Outils.Position;
import java.io.Serializable;
import burgertime.BurgerTime;

/**
 *
 * @author 
 */
public abstract class Structure implements Serializable{
    protected Position pos;
    protected int cmd; //commande envoyé par le joueur
    protected int dir; //direction des deplacements
    protected Joueur jr=null; //joueur assigné à l'objet
    protected boolean online=false; //permet de savoir s'il s'agit d'une partie en ligne ou en local
    
    public Structure()
    {
        this.pos=new Position();
        cmd=0;
    }
    
    public Structure(Position p)
    {
        this.pos=p;
        cmd=0;
    }
    
    public void setPosition(Position p)
    {
        this.pos=p;
    }
    
    public void setPosition(int x, int y)
    {
        this.pos=new Position(x,y);
    }
    
    public Position getPosition(){return this.pos;}
    
    public int getCommande(){return cmd;}
    public void setCommande(int c){cmd=c;}
    
    public void setJoueur(Joueur j){jr=j;}
    public Joueur getJoueur(){return jr;}
    
    
     /**
     * renvoie la position suivante de l'objet selon sa direction
     * @return 
     */
    protected Position getPositionSuivante()
    {
        Position p=null;
        switch(cmd)
        {
            case 1:
                p=new Position(pos.getX(),pos.getY()+1);//avancer
                break;
            case 2:
                p=new Position(pos.getX()+1,pos.getY());//descendre
                break;
            case 3:
                p=new Position(pos.getX(),pos.getY()-1);//reculer
                break;
            case 4:
                p=new Position(pos.getX()-1,pos.getY());//monter
                break;
            default:
                try
                {
                    Thread.currentThread().sleep(100);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
        }
        return p;
    } 
    
    /**
     * methode qui montre le deplacement general dans le plateau
     * les actions lors de deplacement est à redefinir dans les classes filles
     * @return 
     */
    public void seDeplacer(int time)
    {
        int lig=BurgerTime.pl.getLig();
        int col= BurgerTime.pl.getCol();
        
        while(cmd!=0)
        {
                Position p=getPositionSuivante();
                //si la position suivante est dans le plateau, on effectue le deplacement
                if(p!=null&&p.isInside(lig,col))
                {
                    deplacement(p);
                }
                else
                {
                    cmd=0;
                }
                try
                {
                    Thread.currentThread().sleep(time);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
        }
    }
    
    
    public String toString()
    {
        return this.pos.toString();
    }
    
    Structure memoire=null;
    public void setMemoire(Structure c){this.memoire=c;}
    /**
     * prendre la place de l'objet dans la position passé en parametre en l'écrasant
     * @param p 
     */
    public void swap(Position p)
    {
        Plateau pl=BurgerTime.pl;
        
        //if(memoire==null)
          //  memoire=new Plateforme();
        
        Structure c=pl.getStructure(p);
        pl.setStructure(p, this);
        pl.setStructure(pos, memoire);
        memoire=c;//mis à jour memoire
        pos=p;
    }
    
    
    /**
     * se deplacer dans la position p
     * @param p 
     */
    public abstract void deplacement(Position p);
    
    public abstract void attaque();
    
    public abstract String getImage();
    
    
}
