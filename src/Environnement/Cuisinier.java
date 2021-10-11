/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Outils.Position;
import burgertime.BurgerTime;

/**
 *
 * @author 
 */
public class Cuisinier extends Structure implements Runnable{
    private int nbVie; //nombre de vie
    
    public Cuisinier()
    {
        super();
        this.nbVie=3;
    }
    
    public Cuisinier(Position p)
    {
        super(p);
        this.nbVie=3;
    }
    
    public int getNbVie(){return this.nbVie;}
    public void setNbVie(int i){this.nbVie=i;}
    public void mort(){this.nbVie--;}
    
    @Override
    public void deplacement(Position p)
    {
        Structure sc=BurgerTime.pl.getStructure(p);
        
        if(sc==null)
        {
            cmd=0;//on arrete d'avancer
        }
        else
        {
            if(sc instanceof Echelle|| sc instanceof Plateforme)
            {
                swap(p);
            }
            else
            {
                    cmd=0;
            }
        }
    }
    
    @Override
    public String getImage()
    {
        return "chef";
    }
    
    @Override
    public void attaque()
    {
        Position p;
       // System.out.println("attaque");
            switch(dir)
            {
                case 1:
                    p=new Position(pos.getX(),pos.getY()+1);
                    break;
                case 2:
                    p=new Position(pos.getX()+1,pos.getY());
                    break;  
                case 3:
                    p=new Position(pos.getX(),pos.getY()-1);
                    break;
                default:
                    p=null;
                    break;
            }
            int lig=BurgerTime.pl.getLig();
            int col=BurgerTime.pl.getCol();
            if(p!=null&&p.isInside(lig,col))
            {
                Structure sc=BurgerTime.pl.getStructure(p);
                if(sc instanceof BurgerHaut)
                {
                    BurgerHaut b=(BurgerHaut)sc;
                    b.setCommande(2);
                    b.setMemoire(new Plateforme());
                    
                    Thread th=new Thread(b);
                    th.start();//lancement du déplacement du
                }
                else
                {
                    if(sc instanceof Salade)
                    {
                        Salade b=(Salade)sc;
                        b.setCommande(2);
                        b.setMemoire(new Plateforme());
                        Thread th=new Thread(b);
                        th.start();//lancement du déplacement du
                    }
                    else
                    {
                        if(sc instanceof Steack)
                        {
                            Steack b=(Steack)sc;
                            b.setCommande(2);
                            b.setMemoire(new Plateforme());
                            
                            Thread th=new Thread(b);
                            th.start();//lancement du déplacement du
                        }
                        else
                        {
                            if(sc instanceof Oeuf|| sc instanceof Saucisse)
                            {
                                Plateau pl=BurgerTime.pl;
                                sleep(200);
                                sc=null;
                                pl.setStructure(p, memoire);
                            }
                        }
                    }
                }
            }
    }
    
    
        
    private void sleep(int time)
    {
        try
        {
            Thread.currentThread().sleep(time);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() 
    {
        int lig=BurgerTime.pl.getLig();
        int col= BurgerTime.pl.getCol();
        
        while(true)
        {
                if(cmd!=0)
                {
                    dir=cmd;//mise a jour de la direction des attaques suivant la direction de deplacement
                }
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
                    Thread.currentThread().sleep(300);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
        }
    }
}
