/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Outils.Position;
import burgertime.BurgerTime;
import java.util.Random;

/**
 *
 * @author 
 */
public class Saucisse extends Structure implements Runnable {
 
    public Saucisse(){super();}
    public Saucisse(Position p){super(p);}
    
    @Override
     public void deplacement(Position p)
    {
        Structure sc=BurgerTime.pl.getStructure(p);
        
        if(sc==null||sc instanceof Plateforme||sc instanceof Echelle)
        {
            this.swap(p);
        }
        else
        {
            if(sc instanceof Cuisinier)
            {
                BurgerTime.pl.setStructure(p, this);
                BurgerTime.pl.setStructure(pos, memoire);
                pos=p;
                sc=null;
                BurgerTime.j.mort();
                
                Thread th=new Thread(
                
                new Runnable()
                {
                   public void run()
                   {
                       try
                       {
                           Thread.currentThread().sleep(500);
                           BurgerTime.pl.addCuisinier();
                       }
                       catch(InterruptedException e)
                       {
                           e.printStackTrace();
                       }
                   }
                });
                th.start();
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
        return "saucisse";
    }
    
    @Override
    public void attaque(){}

    @Override
    public void run() 
    {
        int lig=BurgerTime.pl.getLig();
        int col= BurgerTime.pl.getCol();
        while(this!=null)
        {
            Position p=null;
            Random r=new Random();
            cmd=r.nextInt(6);

            int nbPas=r.nextInt(col);
            int i=0;
            do
            {
                p=getPositionSuivante();
                if(p!=null&&p.isInside(lig,col))
                {
                    deplacement(p);
                }
                i++;
                
                try
                {
                    Thread.currentThread().sleep(500);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }while(i<nbPas&&cmd!=0); 
        }
    }
}
