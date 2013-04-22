/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin
 */
public class Bishop extends GamePiece implements Cloneable{
    
    
    public Bishop(Player p, int x, int y){
        super(p, x, y);
        if(p.location==1)
            try {
                iPiece = ImageIO.read(new File("BlackBishop.png"));
            } catch (IOException ex) {System.out.println("Fail");}
        else
            try {
                iPiece = ImageIO.read(new File("WhiteBishop.png"));
            }catch (IOException ex) {System.out.println("Fail");}
        icon = "B";
    }

    public boolean movable(int r, int c) {
        if(Math.abs(y-c) == Math.abs(x-r)){
            if(!collides(r,c))
                return true;
        }
        return false;   
    }
    
     
    private boolean collides(int r, int c){
        
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
    
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new Bishop(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
    
}
