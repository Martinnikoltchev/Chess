/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class Player implements Cloneable {
    ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    int location;
    Color col;
    boolean playing;
    int rectSize;
    int direction;
    int hitLocs[][] = new int[8][8];
    boolean kingMoved = false;
    boolean check = false;
    Player otherPlayer = null;
    Player pBackUp;
    Player oPBackUp;
    /*
    ArrayList pBack = new ArrayList<GamePiece>();
    ArrayList oBack = new ArrayList<GamePiece>();
    Originator or = new Originator();
    ArrayList<Originator.Memento> orList = new ArrayList<Originator.Memento>();
    Originator or2 = new Originator();
    ArrayList<Originator.Memento> or2List = new ArrayList<Originator.Memento>();
    */
    
    public Player(int l, int h, Player p){
        rectSize = h;
        playing = false;
        location = l;
        direction = (l*2)-1;
        otherPlayer = p;
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
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Rook(this, i*7, location*7));
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Knight(this, 1+ i*5, location*7));
            }
            for(int i = 0; i < 2; i++){
                pieces.add(new Bishop(this, 2+i*3, location*7));
            }
            pieces.add(new Queen(this, 4, location*7));
            pieces.add(new King(this, 3, location*7));
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

    public void setOtherPlayer(Player p){
        otherPlayer = p;
        oPBackUp = p;
    }
    public Player getOtherPlayer(){
        return otherPlayer;
    }
    
    public boolean pieceHere(int r, int c){
        if(findPiece(r,c)==null)
            if(otherPlayer.findPiece(r, c)==null)
                return false;
        return true;
    }
    public boolean check(){
        for(int i = 0; i < otherPlayer.pieces.size();i++){
            for(int r = 0; r < 8;r++){
                for(int c = 0; c < 8; c++){
                    if(otherPlayer.pieces.get(i).movableLocs[r][c]!=null&&findPiece(r,c).equals(getKing()))
                    {
                        otherPlayer.check = true;
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
    
    protected void undoMove(){
        if(oPBackUp==null)
            setBackUp();
        pieces = pBackUp.clone().pieces;
        otherPlayer.pieces = oPBackUp.clone().pieces;
        System.out.println(pieces.get(0).equals(pBackUp.clone().pieces.get(0)));
        /*
        pieces = new ArrayList<GamePiece>(orList.get(0).getSavedState().pieces);
        otherPlayer.pieces = new ArrayList<GamePiece>(or2List.get(0).getSavedState().pieces);
        */
    }
    
    protected void setBackUp(){
        pBackUp = this;
        oPBackUp = otherPlayer;
        /*orList.clear();
        or2List.clear();
        or.set(this.clone());
        or2.set(otherPlayer.clone());
        orList.add(or.saveToMemento());
        or2List.add(or2.saveToMemento());
        * */
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
            ArrayList<GamePiece> temp = pieces;
            for(int i = 0; i < pieces.size(); i++){
                clone.pieces.add(pieces.get(i).clone(clone));
            }
            
            clone.otherPlayer.otherPlayer = this;
            clone.otherPlayer = new Player(otherPlayer.location, otherPlayer.rectSize, clone);
            clone.otherPlayer.pieces = new ArrayList<GamePiece>();
            for(int i = 0; i < otherPlayer.pieces.size(); i++){
                clone.otherPlayer.pieces.add(otherPlayer.pieces.get(i).clone(clone.otherPlayer));
            }
        } catch (Exception ex) {System.out.println("ERROR");}
        return clone;
    }
}