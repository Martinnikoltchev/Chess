/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin
 */
public abstract class GamePiece implements Cloneable{
    
    int x;
    int y;
    String icon;
    Player p;
    int offSet;
    String iconName;
    Rectangle[][] movableLocs;
    BufferedImage iPiece = null;
    public GamePiece(Player pl, int x, int y){
        this.x = x;
        this.y = y;
        p = pl;
        offSet = p.rectSize/2-20;
        movableLocs = new Rectangle[8][8];
    }
    
    public void drawPiece(Graphics g){
        g.setColor(p.col);
        try{
            g.drawImage(iPiece, x*p.rectSize, y*p.rectSize, null);
        }catch(Exception e){
            g.setFont(new Font("Arial", 0, 40));
            g.drawString(icon, x*p.rectSize+offSet, (y+1)*p.rectSize-offSet);
        }
    }
    public void drawMovable(Graphics g){
        g.setColor(new Color(224,159,55));
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(movableLocs[r][c]!=null){
                    g.fillRect(movableLocs[r][c].x, movableLocs[r][c].y, p.rectSize, p.rectSize);
                }
            }
        }
    }
    public void makeMovables(GamePiece piece){
        if(piece == this){
            for(int r = 0; r < 8; r++){
                for(int c = 0; c < 8; c++){
                    if(movable(r,c)&&p.findPiece(r,c)==null){
                        movableLocs[r][c] = new Rectangle(r*p.rectSize, c*p.rectSize, p.rectSize, p.rectSize);
                    }
                    else
                        movableLocs[r][c] = null;
                }
            }
        }
    }
    public void removeOtherMovables(){
        for(int r = 0; r < 8; r++){
                for(int c = 0; c < 8; c++){
                        movableLocs[r][c] = null;
                }
            }
        movableLocs[x][y] = new Rectangle(x*p.rectSize, y*p.rectSize, p.rectSize, p.rectSize);
    }
    public void movePiece(int r,int c){
        GamePiece piece = p.getOtherPlayer().findPiece(r, c);
        if(piece!=null)
            p.getOtherPlayer().removePiece(piece);
        x = r;
        y = c;
        p.check = false;
    }
    
    protected void setIcon(){
        try {
                iPiece = ImageIO.read(new File(iconName));
            } catch (IOException ex) {System.out.println("Fail");}
    }
    
    public abstract GamePiece clone(Player p);
    
    public abstract boolean movable(int r, int c);
}
