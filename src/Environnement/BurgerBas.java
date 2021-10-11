/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Outils.Position;

/**
 *
 * @author dinael
 */
public class BurgerBas extends Structure implements Runnable {
    
    public BurgerBas(){super();}
    
    public BurgerBas(Position p){super(p);}
    
    @Override
    public void deplacement(Position p){}
    
    @Override
    public String getImage()
    {
        return "bb";
    }
    
    @Override
    public void attaque(){}

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
