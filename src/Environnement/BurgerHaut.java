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
public class BurgerHaut extends Structure implements Runnable{
    
    private boolean cuit;
    
    public BurgerHaut()
    {
        super();
        cuit=false;
    }
    
    public BurgerHaut(Position p)
    {
        super(p);
        cuit=false;
    }
    
    public boolean isCuit()
    {
        return this.cuit;
    }

    
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
            if(sc instanceof Salade)
            {
                Salade b=(Salade)sc;
                
                if(b.isCuit())
                {
                    this.cuit=true;
                }
                else
                {
                    b.setCommande(2);
                    Thread th=new Thread(b);
                    th.start();
                    sleep(300);
                    swap(p);
                }
                cmd=0;
            }
            else
            {
                if( sc instanceof Oeuf || sc instanceof Saucisse)
                {
                    sleep(500);
                    swap(p);
                    cmd=0;
                }
                else
                {
                    cmd=0;
                }
            }
        }
    }
    
    @Override
    public String getImage()
    {
        return "bh";
    }
    
    @Override
    public void attaque(){}

    @Override
    public void run() {
        while(cmd!=0)
        {
            seDeplacer(300);
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
}
