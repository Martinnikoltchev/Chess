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
public class King extends GamePiece implements Cloneable{
    boolean moved = false;
    public King(Player p, int x, int y){
        super(p, x, y);
        if(p.location==1)
                iconName = "BlackKing.png";
        else
                iconName = "WhiteKing.png";
        icon = "K";
    }
    
    public boolean movable(int r, int c){
        int xDiff = Math.abs(x-r);
        int yDiff = Math.abs(y-c);
        if(xDiff<=1)
            if(yDiff<=1)
                if(xDiff+yDiff>0)
                    return true;
        if(castleable(r,c))
            return true;
        return false;
    }
    
    public boolean castleable(int r, int c){
        try{
            Rook piece = getCastleRook(r, c);
            if(piece!=null){
                if(piece.icon.equals("R")){
                    if(!moved&&!(piece).moved){
                        if(!(piece).collides(x, y))
                            return true;
                    }
                }
            }
        }catch(Exception e){}
        return false;
    }
    
    public Rook getCastleRook(int r, int c){
        if(r == 1)
            r = 0;
        else if(r==5)
            r=7;
        if(p.findPiece(r, c) != null){
            GamePiece piece = p.findPiece(r, c);
            if(piece.icon.equals("R"))
                return (Rook)piece;
        }
        return null;
    }
    
    public void castle(int r, int c){
        
        Rook p = getCastleRook(r, c);
        if(r==1){
            x = 1;
            p.x =2;
        }
        else if(r ==5){
            x = 5;
            p.x = 4;
        }
    }
    
    public void movePiece(int r, int c){
        if(Math.abs(r-x)>1||Math.abs(c-y)>1){
            castle(r, c);
        }
        else
            super.movePiece(r, c);
        moved = true;
    }
    
    public GamePiece clone(Player pl){
        GamePiece clone = null;
        try {
            clone = new King(pl, x, y);
        } catch (Exception ex) {System.out.println("ERROR2");}
        return clone;
    }
}
