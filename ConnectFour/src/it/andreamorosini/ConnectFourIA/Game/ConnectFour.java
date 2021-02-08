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
        Scanner in = new Scanner(System.in);
        newGame();
        System.out.println("Ricominciare la partita?[S/N]");
        String response = in.nextLine();
        if(response.equalsIgnoreCase("S")){
            newGame();
        }
        else{
            System.out.println("Fine Partita");
        }
    }

    //inizializzazione di un nuovo gioco
    public static void newGame(){
        boolean gameOver = false;
        IA ia = new IA();
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci le misure della griglia di gioco:\nNumero righe[min 4]: ");
        int rows = in.nextInt();
        System.out.println("Numero colonne[min 5]: ");
        int cols = in.nextInt();
        ia.setSize(rows,cols);
        String[][] board = ia.createBoard();
        printBoard(board);
        int turn = random.nextInt(2);
        while(!gameOver) {
            if (turn == PLAYER) {
                //turno del giocatore
                System.out.println(turn);
                System.out.println("Turno del giocatore\n Inserisci la colonna dove posizionare il pezzo [1-" + cols + "]:");
                int col = in.nextInt() - 1;
                if(col <= cols-1) {
                    if (ia.isValidLocation(board, col)) {
                        int row = ia.getNextOpenRow(board, col);
                        ia.dropPiece(board, row, col, PLAYER_PIECE);
                        if (ia.winningMove(board, PLAYER_PIECE)) {
                            System.out.println("Il giocatore vince!!");
                            gameOver = true;
                        }
                        turn = IA;
                        printBoard(board);
                    }
                }
                else{
                    System.out.println("Inserisci una colonna valida [1-" + cols + "]:");
                }
            }
            else if(turn == IA){
                //turno dell'IA
                System.out.println(turn);
                int col;
                System.out.println("Turno IA");
                String[][] tempBoard = ia.arrayCopy(board);
                Pair<Integer, Integer> minimaxValues = ia.Minimax(tempBoard, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                col = minimaxValues.getValue0();
                System.out.println("colonna IA" + col);
                if(ia.isValidLocation(board,col)){
                    int row = ia.getNextOpenRow(board,col);
                    ia.dropPiece(board, row, col, AI_PIECE);
                    if(ia.winningMove(board,AI_PIECE)){
                        System.out.println("L'IA vince!!!");
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
