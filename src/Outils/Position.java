/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class Position implements Serializable {
    private int y;
    private int x;
    
    public Position()
    {
        x=0;
        y=0;
    }
    
    public Position(int i,int j)
    {
        this.x=i;
        this.y=j;
    }
    
    public Position(Position p)
    {
        x=p.getX();
        y=p.getY();
    }
    
    public int getX(){return x;}
    
    public void setX(int i)
    {
        x=i;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int j)
    {
        y=j;
    }
    
    /**
     * renvoie un booleen pour savoir si la postion est contenu dans 
     * le plateau de taille lig*col
     * @param lig
     * @param col
     * @return 
     */
    public boolean isInside(int lig,int col)
    {
        return x>=0&&x<lig&&y>=0&&y<col;
    }
    
    public String toString()
    {
        return "("+x+","+y+")";
    }
}
