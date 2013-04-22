/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin
 */
public class Queen extends GamePiece implements Cloneable{

    public Queen(Player p, int x, int y){
        super(p, x, y);
        if(p.location==1)
            try {
                iPiece = ImageIO.read(new File("BlackQueen.png"));
            } catch (IOException ex) {System.out.println("Fail");}
        else
            try {
                iPiece = ImageIO.read(new File("WhiteQueen.png"));
            }catch (IOException ex) {System.out.println("Fail");}
        icon = "Q";
    }
    
    public boolean movable(int r, int c){
        if(!collides(r,c)){
            int xDiff = Math.abs(x-r);
            int yDiff = Math.abs(y-c);
            if(xDiff>0&&yDiff==0)
                if(!collides(r,c))
                    return true;
            if(xDiff==0&&yDiff>0)
                if(!collides(r,c))
                    return true;
            if(xDiff==yDiff)
                if(!collides(r,c))
                    return true;
              
        }
        return false; 
    }
    
    private boolean collides(int r, int c){
        int xDiff = Math.abs(x-r);
        int yDiff = Math.abs(y-c);
        if(xDiff==yDiff)
            return dCollides(r,c);
        else
            return vCollides(r,c);
    
    }
    
    private boolean dCollides(int r, int c){
        
        int xDir = r-x;        
        int yDir = c-y;
        try{
            xDir/=Math.abs(xDir);
            yDir/=Math.abs(yDir);
        }catch(Exception e){}
        for(int i = 1; i < Math.abs(x-r); i++){
            if(p.pieceHere(x+xDir*i, y+yDir*i))
                return true;
        }
        return false;
    }
    
    private boolean vCollides(int r, int c){
        
        if(x!=r){
            int xDir = r-x;
            try{
                xDir/=Math.abs(xDir);
            }catch(Exception e){}
            for(int i = 1; i < Math.abs(x-r); i++){
                if(p.pieceHere(x+xDir*i, c))
                    return true;
            }
        }
        else if(y!=c){
            int yDir = c-y;
            try{
                yDir/=Math.abs(yDir);
            }catch(Exception e){}
                for(int i = 1; i < Math.abs(y-c); i++){
                    if(p.pieceHere(r, y+yDir*i))
                        return true;
            }
        }
        return false;
    }
    
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new Queen(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
    
    
}
