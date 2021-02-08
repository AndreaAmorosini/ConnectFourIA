package it.andreamorosini.ConnectFourIA.IA;

import org.javatuples.Pair;

import java.util.*;

public class IA {

    int ROW_COUNT;
    int COLUMN_COUNT;

    String EMPTY = " ";
    String PLAYER_PIECE = "R";
    String AI_PIECE = "G";

    int WINDOW_LENGTH = 4;

    String[][] board;

    //imposta la taglia della griglia di gioco
    public void setSize(int row, int col){
        this.ROW_COUNT = row;
        this.COLUMN_COUNT = col;
    }


    //crea la griglia di gioco e inizializza tutti i campi ad EMPTY
    public String[][] createBoard(){
        board = new String[ROW_COUNT][COLUMN_COUNT];
        for(int i = 0; i<ROW_COUNT; i++){
            for(int j = 0; j<COLUMN_COUNT; j++){
                board[i][j] = EMPTY;
            }
        }
        return board;
    }


    //mette il pezzo selezionato nella posizione in board identificata da row e col
    public void dropPiece(String[][] board, int row, int col, String piece){
        board[row][col] = piece;
    }

    //controlla se la colonna selezionata ha spazi validi dove mettere una pedina
    public boolean isValidLocation(String[][] board, int col){
        for(int i = ROW_COUNT - 1 ; i > 0; i--){
            if(board[i][col].equals(EMPTY)){
                return true;
            }
        }
        return false;
    }

    //restituisce la prossima riga utilizzabile della colonna selezionata
    public int getNextOpenRow(String[][] board, int col){
        //System.out.println("GETNEXTOPENROW\n STOPGETNEXTOPENROW");
        for(int i = ROW_COUNT-1; i > 0; i--){
            if (board[i][col].equals(EMPTY)){
                return i;
            }
        }
        return -1;
    }

    //Controlla se sono presenti mosse che risultano nella vittoria di piece
    public boolean winningMove(String[][] board, String piece){
        //System.out.println("WINNINGMOVE\nSTOPWINNINGMOVE");
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

    //valuta lo score nella window selezionata
    public int evaluateWindow(ArrayList<String> window, String piece){
        int score = 0;
        int nrPiece = 0;
        int nrEMPTY = 0;
        int nrOpponentPiece = 0;
        String opponentPiece = PLAYER_PIECE;
        if (piece.equals(PLAYER_PIECE))
            opponentPiece = AI_PIECE;

        //calcola il numero di piece nella window e assegna uno score corrispondente
        for (Object o : window) {
            if (o.equals(piece)) {
                nrPiece++;
            } else if (o.equals(EMPTY)) {
                nrEMPTY++;
            } else if (o.equals(opponentPiece)) {
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

    //FUNZIONE DI VALUTAZIONE
    //valuta lo score di tutte le possibili mosse di piece
    public int scorePosition(String[][] board, String piece){
        int score = 0;
        ArrayList<String> window = new ArrayList<>();

        //score della colonna centrale
        int centerCount = 0;
        for(int i = 0 ; i < ROW_COUNT; i++ ){
            if(board[i][COLUMN_COUNT/2].equals(piece)){
                centerCount++;
            }
        }
        score+=centerCount*3;

        //score orizzontale
        ArrayList<String> rowArray = new ArrayList<>();

        for(int i = 0 ; i < ROW_COUNT; i++){
            for(int j = 0 ; j < COLUMN_COUNT; j++){
                rowArray.add(board[i][j]);
            }
            for(int j = 0; j < COLUMN_COUNT - 3; j++){
                int col = j;
                int length = col + WINDOW_LENGTH;
                for(col = j;  col < length; col++){
                    window.add(rowArray.get(col));
                }
                score+=evaluateWindow(window, piece);
            }
            rowArray.clear();
        }

        //score verticale
        ArrayList<String> colArray = new ArrayList<>();

        for(int j = 0; j < COLUMN_COUNT; j++){
            for(int i = 0; i < ROW_COUNT; i++){
                colArray.add(board[i][j]);
            }
            for(int i = 0 ; i < ROW_COUNT - 3; i++){
                int row = i;
                int length = row + WINDOW_LENGTH;
                for(row = i ; row < length; row++){
                    window.add(colArray.get(row));
                }
                score+=evaluateWindow(window,piece);
            }
            colArray.clear();
        }

        //score diagonale
        for(int i = 0 ; i < ROW_COUNT - 3; i++){
            for(int j = 0; j < COLUMN_COUNT - 3; j++){
                for(int range = 0; range < WINDOW_LENGTH; range++){
                    window.add(board[i+range][j+range]);
                }
                score+=evaluateWindow(window, piece);
            }
        }

        for(int i = 0 ; i < ROW_COUNT - 3; i++){
            for(int j = 0 ; j < COLUMN_COUNT - 3 ; j++){
                for (int range = 0 ; range < WINDOW_LENGTH; range++){
                    window.add(board[i+3-range][j+range]);
                }
                score+=evaluateWindow(window,piece);
            }
        }


        return score;

    }

    //verifica se si è in uno stato terminale del gioco
    public boolean isTerminalNode(String[][] board){
        return winningMove(board, PLAYER_PIECE) || winningMove(board, AI_PIECE) || getValidLocations(board).size() == 0;
    }

    //algoritmo MINIMAX con potatura Afa e Beta
    public Pair<Integer, Integer> Minimax(String[][] board, int depth, int alpha, int beta, boolean isMaximizingPlayer){
        //System.out.println("MINIMAX");
        ArrayList<Integer> validLocations = getValidLocations(board);
        boolean isTerminal = isTerminalNode(board);
        //System.out.println("è terminale?" + isTerminal);
        //funzione di utilità
        if (depth==0 || isTerminal){
            if (isTerminal){
                if(winningMove(board, AI_PIECE)){
                    //System.out.println("winning move AI? " + winningMove(board,AI_PIECE));
                    return Pair.with(0,100000000);
                }
                else if(winningMove(board, PLAYER_PIECE)){
                    //System.out.println("winnin move player? " + winningMove(board, PLAYER_PIECE));
                    return Pair.with(0,-100000000);
                }
                else{
                    //System.out.println("no winning move");
                    return Pair.with(0,0);
                }
            }
            else{
                //System.out.println("socrePosition" + scorePosition(board,AI_PIECE));
                return Pair.with(0, scorePosition(board,AI_PIECE));
            }
        }
        int value;
        Random random = new Random();
        int column = validLocations.get(random.nextInt(validLocations.size()));
        //System.out.println("random column: " + column);
        if(isMaximizingPlayer){
            //System.out.println("MINIMAX MAXIMIZING PLAYER");
            value = Integer.MIN_VALUE;
            for (int col:validLocations) {
                //System.out.println("colonna provata: " + col);
                int row = getNextOpenRow(board, col);
                //System.out.println("riga selezionata: " + row);
                String[][] tempBoard = arrayCopy(board);
                dropPiece(tempBoard, row, col, AI_PIECE);
                int newScore = Minimax(tempBoard, depth-1, alpha, beta,false).getValue1();
                //System.out.println("nuovo score: " + newScore);
                if(newScore>value){
                    value = newScore;
                    column = col;
                }
                if(value > alpha){
                    alpha = value;
                }
                if(alpha >= beta){
                    break;
                }
            }
        }
        else{
            //System.out.println("MINIMAX not MAXIMIZING PLAYER");
            value = Integer.MAX_VALUE;
            for (int col:validLocations) {
                //System.out.println("colonna provata: " + col);
                int row = getNextOpenRow(board,col);
                //System.out.println("riga selezionata: " + row);
                String[][] tempBoard = arrayCopy(board);
                dropPiece(tempBoard, row, col,PLAYER_PIECE);
                int newScore = Minimax(tempBoard, depth-1, alpha, beta, true).getValue1();
                //System.out.println("nuovo score: " + newScore);
                if(newScore<value){
                    value = newScore;
                    column = col;
                }
                if(value < beta){
                    beta = value;
                }
                if(alpha >= beta){
                    break;
                }
            }
        }
        //System.out.println("column:" + column + "value: " + value);
        return Pair.with(column,value);
    }

    //restituisce le posizioni valide dove poter mettere una pedina
    public ArrayList<Integer> getValidLocations(String[][] board){
        //System.out.println("GETVALIDLOCATIONS");
        ArrayList<Integer> validLocations = new ArrayList<>();
        for(int j = 0 ; j < COLUMN_COUNT; j++){
            if(isValidLocation(board,j)){
                //System.out.println("location " + j + isValidLocation(board,j));
                validLocations.add(j);
            }
        }
        //System.out.println("STOP GETVALIDLOCATIONS");
        return validLocations;
    }


    //metodo di servizio per clonare la board di gioco per farla utilizzare liberamente all'algoritmo minimax
    public String[][] arrayCopy(String[][] from){
        String[][] destination = new String[from.length][from[0].length];
        for(int i = 0 ; i < from.length; i++){
            System.arraycopy(from[i], 0,destination[i], 0, from[i].length );
        }
        return destination;
    }

}
