/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Martin
 */
public class Chess extends Applet implements MouseListener{

    /**
     * @param args the command line arguments
     */
    int width;
    int height;
    int rectSize;
    GamePiece currentP = null;
    Player curPlayer;
    Rectangle[][] board = new Rectangle[8][8];
    Player[] players;
    int vsAI;
    public void init(){
        width = 600;
        height = 600;
        setSize(height, height);
        players = new Player[2];
        startGame();
        addMouseListener(this);
    }
    public void paint(Graphics g) {
        paintBoard(g);
        players[0].paintPieces(g);
        players[1].paintPieces(g);
    }
    public void paintBoard(Graphics g){
        g.setColor(new Color(191,147,71));
        g.fillRect(0,0,height,height);
        g.setColor(new Color(104, 77, 36));
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if((r%2+c%2)==1)
                    g.fillRect(r*rectSize,c*rectSize,rectSize, rectSize);
            }
        }
        if(currentP!=null)
            currentP.drawMovable(g);
    }

    private void startGame() {
        rectSize = height/8;
        vsAI = new JOptionPane().showConfirmDialog(null, "Would you like to play against an AI?");

        players[0] = new Player(0, rectSize, players[1]);
        if(vsAI==1)
            players[1] = new Player(1, rectSize, players[0]);
        else
            players[1] = new AI(1, rectSize, players[0]);
        players[0].otherPlayer = players[1];
        backUpPlayers();
        nextTurn();
        for(int r = 0; r <8; r++){
            for(int c = 0; c<8; c++){
                board[r][c] = new Rectangle(r*rectSize, c*rectSize, rectSize, rectSize);
            }
        }
    }

    private void nextTurn() {
        boolean changed = false;
        for(int i = 0; i < 2 && !changed; i++)
            if(players[i]!=curPlayer){
                changed = true;
                curPlayer=players[i];
            }
    }
    
    private void pieceClicked(int r, int c) {
        try{
            GamePiece p = findPiece(r,c);
            if(currentP == null){
                if(p.p == curPlayer){
                    currentP = p;
                    movables();
                }
            }
            else if(currentP!=null){
                if(currentP.movable(r, c)){
                    if(currentP.p!=p.p){
                        currentP.movePiece(r, c);
                        endTurn();
                    }
                }
                if(p.p==currentP.p){
                    currentP = p;
                    movables();
                }
            }
            
        }catch(Exception e){
            if(currentP!=null){
                if(currentP.movable(r, c)){
                    currentP.movePiece(r,c);
                    endTurn();
                }
            }
        }
    }
    
    
    private void endTurn(){
            currentP.removeOtherMovables();
            currentP = null;
            nextTurn();
            if(vsAI!=1){
                ((AI)players[1]).takeTurn();
                nextTurn();
            }
            backUpPlayers();
    }
    private void backUpPlayers(){
        players[0].setBackUp();
        players[1].setBackUp();
    }
    private void movables(){
        for(int i = 0; i < 2; i++){
            for(int k = 0; k < players[i].pieces.size();k++){
                players[i].pieces.get(k).makeMovables(currentP);
            }
        }
    }
    
    private GamePiece findPiece(int r, int c) {
        GamePiece p = players[0].findPiece(r, c);
        if(p==null){
            p = players[1].findPiece(r, c);
        }
        return p;
    }
   
    private void status(){
        for(int k = 0; k <2; k++){
            boolean end = true;
            for(int i = 0; i < players[k].pieces.size(); i++)
                if(players[k].pieces.get(i).icon.equals("K"))
                    end = false;
            if(end){
                JOptionPane pane = new JOptionPane();
                this.removeMouseListener(this);
                String winner;
                if(players[k].location==1)
                    winner = "Black";
                else
                    winner = "White";
                pane.showMessageDialog(null, winner + " Looses!");
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(board[r][c].contains(e.getPoint()))
                    pieceClicked(r,c);
            }
        }
        repaint();
        status();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
