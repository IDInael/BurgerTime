/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Outils.Position;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import burgertime.BurgerTime;
/**
 *
 * @author 
 */
public class Plateau {
    
    private int lig;
    private int col;
    private int ilig;//intervalle sur les lignes
    private int icol;
    private Structure[][] pl;
    
    private ReadWriteLock verrou=new ReentrantReadWriteLock(); //verrou
    private Lock lecture=verrou.readLock();// verrou en lecture
    private Lock ecriture=verrou.writeLock();// verrou en ecriture
    
    public Plateau()
    {
        lig=30;
        col=15;
        ilig=(int)(lig/6);
        icol=(int)(col/3);
        pl=new Structure[lig][col];
    }
    
    /**
     * methode qui remplie dynamiquement le plateau
     */
    
    public int getLig(){return this.lig;}
    
    public int getCol(){return this.col;}
    
    private void reset()
    {
        for(int i=0;i<lig;i++)
        {
            for(int j=0;j<col;j++)
            {
                if(i%ilig==0)
                {
                    pl[i][j]= new Plateforme();   
                }
                else
                {
                    pl[i][j]=null;
                }
            }
        }
        
        int i=0;
        while(i<15)
        {
            int j=(int)(Math.random()*icol+i);// colonne de burger [0,5[ puis [5,10[ puis [10,15[
           // System.out.print(j+" ");
            this.addBurger(j);
            i+=icol;
        }
        
        addEchelle();
    }
    
    private void addBurger( int j)
    {       
        int v1=(int)(Math.random()*6);//etage vide 1
        int v2=v1;//etage vide 2
        do
        {
             v2=(int)(Math.random()*6);
        }while(v2==v1);
        
        int etape=0; 
      
        int i=0;
        do
        {
            if(i!=(v1*ilig)&&i!=(v2*ilig))
            {
                switch(etape)
                {
                    case 0: pl[i][j]=new BurgerHaut();break;
                    case 1: pl[i][j]=new Salade();break;
                    case 2: pl[i][j]=new Steack();break;
                }
                etape++;
                pl[i][j].setPosition(i, j);
            }
            i+=ilig;
        }while(etape!=3);
        
        pl[lig-1][j]=new BurgerBas();
        pl[lig-1][j].setPosition(lig-1, i);
    }
    
    /**
     * ajoute une echelle sur la colonne j, de la ligne deb à la ligne fin
     * @param deb
     * @param fin
     * @param j 
     */
    private void addEchelle()
    {
        int j=0;
        while(j<col)
        {
            for(int k=0;k<6;k++)
            {
                double pr=Math.random()*100;//probabilité d'avoir une echelle entre les etages
                if(pr<70)//60% de chance d'avoir une echelle
                {
                    for(int i=k*ilig;i<(k+1)*ilig;i++)
                    {
                        if(pl[i][j]==null)
                            pl[i][j]=new Echelle();
                    }
                }
            }
            j+=(int)(Math.random()*3+2);
        }
        
    }
    
    public void nouvellePartie()
    {
        this.reset();
        int c=1;//indice des cols ou il y a des burgers

        this.addEnnemie(3);
        this.addCuisinier();
    }
    
    @Override
    public String toString()
    {
        String res="";
        
            for(int i=0;i<lig;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(pl[i][j]!=null)
                        res+=pl[i][j].getImage()+" ";
                    else
                        res+="  ";
                }
                res+="\n";
            }
          
        return res;
    }
    
    public Structure getStructure(Position p)
    {
        //verrouillage en lecture
        //le verrou lecture est ouvert s'il n'y a pas d'écriture en cours
        lecture.lock();
        try
        {
            return pl[p.getX()][p.getY()];
        }
        finally
        {
            lecture.unlock();
            //deverouillage
        }
    }
    
    public void setStructure(Position p,Structure ob)
    {
        //fermeture du verrrou pour tout lecture et ecriture
        //accès interdit en lecture et en écriture pour tous les autres threads
        ecriture.lock();
        try
        {
            pl[p.getX()][p.getY()]=ob;
        }
        finally
        {
            ecriture.unlock();
            //ouverture du verrou
        }
    }
    
    public void addCuisinier()
    {
        //recherche d'une cage ou il y a d elechelle
        Random r=new Random();
        int i,j;
        do
        {
            i=r.nextInt(lig);
            j=r.nextInt(col);
        }while(!(pl[i][j] instanceof Echelle)&&!(pl[i][j] instanceof Plateforme));
        
        
        Cuisinier c= new Cuisinier();
        c.setMemoire(pl[i][j]);
        //verrouiller l'acces à la lecture et à l'ecriture
        ecriture.lock();
        try
        {
            c.setPosition(i,j);
            pl[i][j]=c;//on ajoute dans le plateau
            
            Thread th= new Thread(c);
            th.start();
        }
        finally
        {
            ecriture.unlock();//deverouillage
        }
        
        BurgerTime.j.setObjet(c);
    }
   
    public void addSaucisse()
    {
    //recherche d'une cage ou il y a d elechelle
        Random r=new Random();
        int i,j;
        do
        {
            i=r.nextInt(lig);
            j=r.nextInt(col);
        }while(!(pl[i][j] instanceof Echelle)&&!(pl[i][j] instanceof Plateforme)&&pl[i][j]!=null);
        
        
        Saucisse c= new Saucisse();
        c.setMemoire(pl[i][j]);
        //verrouiller l'acces à la lecture et à l'ecriture
        ecriture.lock();
        try
        {
            c.setPosition(i,j);
            pl[i][j]=c;//on ajoute dans le plateau
            
            Thread th= new Thread(c);
            th.start();
        }
        finally
        {
            ecriture.unlock();//deverouillage
        }
        
        BurgerTime.j.setObjet(c);
    }
    
    public void addOeuf()
    {
    //recherche d'une cage ou il y a d elechelle
        Random r=new Random();
        int i,j;
        do
        {
            i=r.nextInt(lig);
            j=r.nextInt(col);
        }while(!(pl[i][j] instanceof Echelle)&&!(pl[i][j] instanceof Plateforme)&&pl[i][j]!=null);
        
        
        Oeuf c= new Oeuf();
        c.setMemoire(pl[i][j]);
        //verrouiller l'acces à la lecture et à l'ecriture
        ecriture.lock();
        try
        {
            c.setPosition(i,j);
            pl[i][j]=c;//on ajoute dans le plateau
            
            Thread th= new Thread(c);
            th.start();
        }
        finally
        {
            ecriture.unlock();//deverouillage
        }
        
        BurgerTime.j.setObjet(c);
    }
    
    public void addEnnemie(int nb)
    {
        for(int i=0;i<nb;i++)
        {
            this.addOeuf();
            this.addSaucisse();
        }
    }
}
