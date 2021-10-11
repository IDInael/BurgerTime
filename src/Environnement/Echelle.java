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
public class Echelle extends Structure{
    
    public Echelle(){super();}
    
    public Echelle(Position p){super(p);}
    
    @Override
    public void deplacement(Position p){}
    
    @Override
    public String getImage()
    {
        return "ech";
    }
    
    @Override
    public void attaque(){}
}
