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
public class Pawn extends GamePiece implements Cloneable{
    
    
    public Pawn(Player p, int x, int y){
        super(p, x, y);
        if(p.location==1)
            try {
                iPiece = ImageIO.read(new File("BlackPawn.png"));
            } catch (IOException ex) {System.out.println("Fail");}
        else
            try {
                iPiece = ImageIO.read(new File("WhitePawn.png"));
            }catch (IOException ex) {System.out.println("Fail");}
        icon = "P";
    }
    
    public boolean movable(int r, int c) {
        int yDiff = Math.abs(y-c);
        int xDiff = Math.abs(x-r);
        int dir = y-c;
        if(yDiff>0)
            dir/=yDiff;
        if(yDiff==2 && xDiff==0 && y==1+p.location*5)
            if(dir==p.direction){
                if(p.otherPlayer.findPiece(r, c)!=null)
                    return false;
                return true;
            }
        if(yDiff==1 && xDiff==0)
            if(dir==p.direction){
                if(p.otherPlayer.findPiece(r, c)!=null)
                    return false;
                return true;
            }
        if((r==x-1||r==x+1)&&c==y-p.direction&&p.otherPlayer.findPiece(r,c)!=null)
            return true;
        return false;   
    }
    
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new Pawn(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
    
    
}
