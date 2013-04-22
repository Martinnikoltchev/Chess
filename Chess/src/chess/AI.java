/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.Random;

/**
 *
 * @author Martin
 */
public class AI extends Player{
    
    int HITSCALER = 1;
    int DEFSCALER = 1;
    
    Random rand = new Random(System.currentTimeMillis());
    public AI(int l, Chess c){
    super(l,c);
    }
    
    public void takeTurn(){  //(0,7) = bottom left corner
        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).makeMovables(pieces.get(i));
        }
        getMove();
        main.backUpMove();
        main.paintAll(main.getGraphics());
    }
    
    private void getMove(){
        int hI = 0;
        int hR = 0;
        int hC = 0;
        int highestState = 0;
        int currentState = 0;
        for(int i = 0; i < pieces.size()-1; i++){
            for(int r = 0; r < 8; r++){
                for(int c = 0; c<8; c++){
                    if(pieces.get(i).movable(r, c)){
                        currentState = testState(pieces.get(i), r, c);
                        if(currentState>highestState){
                            highestState = currentState;
                            hI = i;
                            hC = c;
                            hR = r;
                        }
                    }
                }
            }
        }
        
        pieces.get(hI).movePiece(hR, hC);
    }
    
    private int quantifyGameState(){
        int val = 1;
        val += getHitValue()*HITSCALER;
        return val;
    }
    
    public int testState(GamePiece piece, int r, int c){
        piece.movePiece(r, c);
        int val = quantifyGameState();
        main.undoMove();
        return val;
    }
    
    public void getBestMove(){
        int highest = 0;
        int r = 0;
        int c = 0;
        int mR = 0;
        int mC = 0;
    }
    
    private int getHitValue(){
        makeHits();
        int val = 0;
        for(int r = 0; r < 8; r++){
            for(int c = 0; c< 8; c++){
                val+=hitLocs[r][c];
            }
        }
        return val;
    }
    
    
}
