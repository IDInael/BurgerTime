/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environnement;

import Outils.Position;

/**
 *
 * @author 
 */
public class Plateforme extends Structure{
    
    public Plateforme()
    {
        super();
    }
    
    public Plateforme(Position p)
    {
        super(p);
    }
     
    @Override
    public void deplacement(Position p) {
    }

    @Override
    public String getImage() {
        return "plateforme";
    }
    
    @Override
    public void attaque(){}
}
