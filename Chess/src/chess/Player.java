/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Player implements Cloneable {
    ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    int location;
    int otherLoc;
    Color col;
    boolean playing;
    int rectSize;
    int direction;
    int hitLocs[][] = new int[8][8];
    boolean kingMoved = false;
    boolean check = false;
    Player pBackUp;
    Chess main;
    /*
    ArrayList pBack = new ArrayList<GamePiece>();
    ArrayList oBack = new ArrayList<GamePiece>();
    Originator or = new Originator();
    ArrayList<Originator.Memento> orList = new ArrayList<Originator.Memento>();
    Originator or2 = new Originator();
    ArrayList<Originator.Memento> or2List = new ArrayList<Originator.Memento>();
    */
    
    public Player(int l, Chess c){
        main = c;
        rectSize = c.rectSize;
        playing = false;
        location = l;
        otherLoc = (location-1)*(-1);
        direction = (l*2)-1;
        if(l==0){
            col = Color.white;
        }
        else{
            col = Color.cyan;
        }
        makePieces();
        }

    public void makePieces(){
        if(pieces.size()<=0){
            for(int i = 0; i < 8; i++){
                pieces.add(new Pawn(this, i, 1 + location*5));
                pieces.get(pieces.size()-1).setIcon();
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Rook(this, i*7, location*7));
                pieces.get(pieces.size()-1).setIcon();
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Knight(this, 1+ i*5, location*7));
                pieces.get(pieces.size()-1).setIcon();
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Bishop(this, 2+i*3, location*7));
                pieces.get(pieces.size()-1).setIcon();
            }
            pieces.add(new Queen(this, 4, location*7));
            pieces.get(pieces.size()-1).setIcon();
            pieces.add(new King(this, 3, location*7));
            pieces.get(pieces.size()-1).setIcon();
        }
    }
    
    public void paintPieces(Graphics g){
        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).drawPiece(g);
        }
    }
    public void removePiece(GamePiece p){
        for(int i = 0; i < pieces.size(); i++){
            if(p==pieces.get(i))
                pieces.remove(i);
        }
    }
    
    public GamePiece findPiece(int r, int c) {
        GamePiece p = null;

        for(int i = 0; i < pieces.size(); i++){
            if(pieces.get(i).x == r && pieces.get(i).y == c)
                p = pieces.get(i);
        }
        return p;
    }
    public Player getOtherPlayer(){
        return main.players[otherLoc];
    }
    
    public boolean pieceHere(int r, int c){
        if(findPiece(r,c)==null)
            if(getOtherPlayer().findPiece(r, c)==null)
                return false;
        return true;
    }
    public boolean check(){
        for(int i = 0; i < getOtherPlayer().pieces.size();i++){
            for(int r = 0; r < 8;r++){
                for(int c = 0; c < 8; c++){
                    if(getOtherPlayer().pieces.get(i).movableLocs[r][c]!=null&&findPiece(r,c).equals(getKing()))
                    {
                        getOtherPlayer().check = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean checkMate(){
        GamePiece k = getKing();
        if(check()){
            boolean canMove = false;
            for(int r = 0; r < 8 && canMove; r++){
                for(int c = 0; c < 8 && canMove; c++){
                    if(k.movableLocs[r][c]!=null)
                        canMove = true;
                }
            }
            return !canMove;
        }
        return false;
    }
    private GamePiece getKing(){
        for(int i = 0; i < pieces.size(); i++){
            if(pieces.get(i).icon.equals("K"))
                return pieces.get(i);
        }
        return null;
    }
    
    protected void makeHits(){
        for(int i = 0; i < pieces.size(); i++){
            for(int r = 0; r < 8; r++){
                for(int c = 0; c < 8; c++){
                    if(pieces.get(i).movableLocs[r][c] != null)
                        hitLocs[r][c]++;
                }
            }
        }
    }
    
    protected Player clone(){
        Player clone = null;
        try {
            clone = (Player) super.clone();
            clone.pieces = new ArrayList<GamePiece>();
            for(int i = 0; i < pieces.size(); i++){
                clone.pieces.add(pieces.get(i).clone(clone));
                clone.pieces.get(i).iPiece = pieces.get(i).iPiece;
            }
        } catch (Exception ex) {System.out.println("ERROR");}
        return clone;
    }
}