package it.andreamorosini.ConnectFourIA.Game;

import it.andreamorosini.ConnectFourIA.IA.IA;
import org.javatuples.Pair;

import java.util.Random;
import java.util.Scanner;

public class ConnectFour {

    static int PLAYER = 0;
    static int IA = 1;

    static String PLAYER_PIECE = "R";
    static String AI_PIECE = "G";

    public static void main(String[] args) {
       newGame();
       Scanner in = new Scanner(System.in);
        System.out.println("Ricominciare la partita?[S/N]");
        String response = in.nextLine();
        if(response.equals("S")){
            newGame();
        }
        else{
            System.out.println("Fine Partita");
        }
    }

    public static void newGame(){
        boolean gameOver = false;
        IA ia = new IA();
        String[][] board = ia.createBoard();
        printBoard(board);
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        int turn = random.nextInt(2);
        while(!gameOver) {
            if (turn == PLAYER) {
                //turno del giocatore
                System.out.println(turn);
                System.out.println("Turno del giocatore\n Inserisci la colonna dove posizionare il pezzo [1-7]:");
                int col = in.nextInt() - 1;
                if (ia.isValidLocation(board, col)) {
                    int row = ia.getNextOpenRow(board, col);
                    ia.dropPiece(board, row, col, PLAYER_PIECE);
                    if (ia.winningMove(board, PLAYER_PIECE)) {
                        System.out.println("Il giocatore 1 vince!!");
                        gameOver = true;
                    }
                    turn=IA;
                    printBoard(board);
                }
            }
            else if(turn == IA){
                //turno dell'IA
                System.out.println(turn);
                int col, minimaxScore;
                System.out.println("Turno IA");
                //col = ia.pickBestMove(board,AI_PIECE);
                String[][] tempBoard = ia.arrayCopy(board);
                Pair<Integer, Integer> minimaxValues = ia.Minimax(tempBoard, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                col = minimaxValues.getValue0();
                System.out.println("colonna IA" + col);
                minimaxScore = minimaxValues.getValue1();
                if(ia.isValidLocation(board,col)){
                    int row = ia.getNextOpenRow(board,col);
                    ia.dropPiece(board, row, col, AI_PIECE);
                    if(ia.winningMove(board,AI_PIECE)){
                        System.out.println("Player 2 wins!!!");
                        gameOver=true;
                    }
                    turn=PLAYER;
                    printBoard(board);
                }

            }
        }

    }

    //stampa la griglia di gioco
    public static void printBoard(String[][] board){
        StringBuilder sb = new StringBuilder();
        sb.append("------\n");
        for (String[] row : board) {
            sb.append("|");
            for(String cell : row){
                sb.append(cell);
                sb.append("|");
            }
            sb.append("\n");
            sb.append("------\n");
        }
        String draw = sb.toString();
        System.out.println(draw);
    }

}
