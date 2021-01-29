package it.andreamorosini.ConnectFourIA.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IA {

    int ROW_COUNT = 6;
    int COLUMN_COUNT = 7;

    int PLAYER = 0;
    int IA = 1;

    String EMPTY = " ";
    String PLAYER_PIECE = "R";
    String AI_PIECE = "G";

    int WINDOW_LENGTH = 4;

    String[][] board;

    public String[][] createBoard(){
        board = new String[ROW_COUNT][COLUMN_COUNT];
        for(int i = 0; i<ROW_COUNT; i++){
            for(int j = 0; j<COLUMN_COUNT; j++){
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    public void dropPiece(String[][] board, int row, int col, String piece){
        board[row][col] = piece;
    }

    public boolean isValidLocation(String[][] board, int col){
        if(board[ROW_COUNT-1][col].equals(EMPTY)){
            return true;
        }
        else
            return false;
    }

    public int getNextOpenRow(String[][] board, int col){
        for(int i = ROW_COUNT-1; i > 0; i++){
            if (board[i][col].equals(EMPTY)){
                return i;
            }
        }
        return -1;
    }

    public boolean winningMove(String[][] board, String piece){
        //controlla vittorie orizzontali
        for(int j = 0; j < COLUMN_COUNT - 3; j++){
            for(int i = 0; i < ROW_COUNT; i++){
                if(board[i][j].equals(piece) && board[i][j+1].equals(piece) && board[i][j+2].equals(piece) && board[i][j+3].equals(piece)){
                    return true;
                }
            }
        }
        //controlla vittorie verticali
        for(int j = 0 ; j < COLUMN_COUNT ; j++){
            for(int i = 0; i < ROW_COUNT - 3; i++){
                if(board[i][j].equals(piece) && board[i+1][j].equals(piece) && board[i+2][j].equals(piece) && board[i+3][j].equals(piece)){
                    return true;
                }
            }
        }
        //controlla vittorie sulla diagonale positiva
        for(int j = 0 ; j < COLUMN_COUNT-3; j++){
            for(int i = 0 ; i < ROW_COUNT-3; i++){
                if(board[i][j].equals(piece) && board[i+1][j+1].equals(piece) && board[i+2][j+2].equals(piece) && board[i+3][j+3].equals(piece)){
                    return true;
                }
            }
        }
        //controlla vittorie sulla diagonale negativa
        for(int j = 0 ; j < COLUMN_COUNT - 3 ; j++){
            for(int i = 3 ; i < ROW_COUNT ; i++){
                if(board[i][j].equals(piece) && board[i-1][j+1].equals(piece) && board[i-2][j+2].equals(piece) && board[i-3][j+3].equals(piece)){
                    return true;
                }
            }
        }
        return false;
    }

    public int evaluateWindow(List window, String piece){
        int score = 0;
        int nrPiece = 0;
        int nrEMPTY = 0;
        int nrOpponentPiece = 0;
        String opponentPiece = PLAYER_PIECE;
        if (piece.equals(PLAYER_PIECE))
            opponentPiece = AI_PIECE;

        //calcola il numero di piece nella window e assegna uno score corrispondente
        for(int i = 0; i < window.size(); i++){
            if(window.get(i).equals(piece)){
                nrPiece++;
            }
            else if(window.get(i).equals(EMPTY)){
                nrEMPTY++;
            }
            else if (window.get(i).equals(opponentPiece)){
                nrOpponentPiece++;
            }
        }
        if (nrPiece==4){
            score += 100;
        }
        else if (nrPiece==3 && nrEMPTY==1){
            score+=5;
        }
        else if (nrPiece==2 && nrEMPTY==2){
            score+=2;
        }

        if (nrOpponentPiece==3 && nrEMPTY == 1){
            score -= 4;
        }

        return score;
    }

    public int scorePosition(String[][] board, String piece){
        int score = 0;

        //score della colonna centrale
        int centerCount = 0;
        for(int i = 0 ; i < ROW_COUNT; i++ ){
            if(board[i][COLUMN_COUNT/2].equals(piece)){
                centerCount++;
            }
        }
        score+=centerCount*3;

        /*//score orizzontale
        ArrayList<String>
        for(int i = 0 ; i < ROW_COUNT; i++){

        }*/

        return score;

    }

    public boolean isTerminalNode(String[][] board){
        if(winningMove(board, PLAYER_PIECE) || winningMove(board, AI_PIECE) ||getValidLocations(board).size()==0 ){
            return true;
        }
        return false;
    }

    //public Map.Entry Minimax(String[][] board, int depth, int alpha, int beta, boolean isMaximizingPlayer){}

    public List getValidLocations(String[][] board){
        ArrayList<Integer> validLocations = new ArrayList<>();
        for(int j = 0 ; j < COLUMN_COUNT; j++){
            if(isValidLocation(board,j)){
                validLocations.add(j);
            }
        }
        return validLocations;
    }

    public int pickBestMove(String[][] board, String piece){
        ArrayList<Integer> validLocations = (ArrayList<Integer>) getValidLocations(board);
        int bestScore = -1000;
        Random random = new Random();
        int bestCol = validLocations.get(random.nextInt(validLocations.size()));
        for (int col:validLocations) {
            int row = getNextOpenRow(board, col);
            String[][] tempBoard = board.clone();
            dropPiece(tempBoard, row, col, piece);
            int score = scorePosition(tempBoard, piece);
            if (score > bestScore){
                bestScore = score;
                bestCol = col;
            }
        }
        return bestCol;
    }

}
