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
public class Knight extends GamePiece implements Cloneable{
    
    
    public Knight(Player p, int x, int y){
        super(p, x, y);
        if(p.location==1)
            try {
                iPiece = ImageIO.read(new File("BlackKnight.png"));
            } catch (IOException ex) {System.out.println("Fail");}
        else
            try {
                iPiece = ImageIO.read(new File("WhiteKnight.png"));
            }catch (IOException ex) {System.out.println("Fail");}
        icon = "Kn";
    }
    
    public boolean movable(int r, int c) {
        int xDiff = Math.abs(x-r);
        int yDiff = Math.abs(y-c);
        if(xDiff==2&&yDiff==1)
            return true;
        if(xDiff==1&&yDiff==2)
            return true;
        return false;   
    }
    
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new Knight(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
    
}
