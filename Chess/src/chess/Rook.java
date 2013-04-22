/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author Martin
 */
public class Rook extends GamePiece implements Cloneable{
    boolean moved = false;
    public Rook(Player p, int x, int y){
        super(p, x ,y);
        if(p.location==1)
                iconName = "BlackRook.png";
        else
                iconName = "WhiteRook.png";
        icon = "R";
    }
    
    public boolean movable(int r, int c) {
        int xDiff = Math.abs(x-r);
        int yDiff = Math.abs(y-c);
        if(xDiff==0&&yDiff>0)
            if(!collides(r,c))
                return true;
        if(xDiff>0&&yDiff==0)
            if(!collides(r,c))
                return true;
        return false;   
    }
    
    public boolean collides(int r, int c){
        
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
    
    public void movePiece(int r, int c){
        super.movePiece(r, c);
        moved = true;
    }
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new Rook(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
}
