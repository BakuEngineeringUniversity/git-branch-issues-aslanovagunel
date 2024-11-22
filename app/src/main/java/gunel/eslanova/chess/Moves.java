package gunel.eslanova.chess;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Moves extends GameActivity{

    List<Integer> possible_rows=new ArrayList<>();
    List<Integer> possible_columns=new ArrayList<Integer>();

    List<Boolean> possible_move_type=new ArrayList<Boolean>();
    int[][][] BOARD=new int[8][8][2];

    int movetwo_row=0,movetwo_column=0;
    boolean movetwo=false;

    int r,c;

    Moves(int[][][] board, int row, int column){
        BOARD=board;
        r=row;
        c=column;
        if(BOARD[r][c][0]==1)
        {color=true;}
        else
        {color=false;}
    }
    boolean color;



    public void calc_moves(){
       // Log.d("tapped",BOARD[r][c][1]+"");

        if (BOARD[r][c][1]==1){
            pawn();
        }
         else if (BOARD[r][c][1]==2) {
            rook();
        }
        else if (BOARD[r][c][1]==3) {
            bishop();
        }
        else if (BOARD[r][c][1]==4) {
            knight();
        }

        else if (BOARD[r][c][1]==6) {
            Log.d("","queen tapped");
            queen();
        }
        else if (BOARD[r][c][1]==5) {
            Log.d("toast",r+""+c);
            //super.maketoast(r+""+c);
            king();
        }

    }

    public  void  queen(){
        boolean toptobottom=false,bottomtotop=false,lefttoright=false,righttoleft=false;
        boolean pf=false,pb=false,of=false,ob=false;
        for (int a = 1; a < 8; a++) {
            if (!pf){
                ob=fn(r+a,c+a);
            }
            if (!pb){
                ob=fn(r-a,c-a);
            }
            if (!of){
                ob=fn(r+a,c-a);
            }
            if (!ob){
                ob=fn(r-a,c+a);
            }
            if (!toptobottom){
                toptobottom=fn(r+a,c);
            }
            if (!bottomtotop){
                bottomtotop=fn(r-a,c);
            }
            if (!righttoleft){
                righttoleft=fn(r,c+a);
            }
            if (!lefttoright){
                lefttoright=fn(r,c-a);
            }
        }
    }

    public void  rook(){

        boolean toptobottom=false,bottomtotop=false,lefttoright=false,righttoleft=false;
        for (int a=1;a<8;a++){
            if (!toptobottom){
                toptobottom=fn(r+a,c);
            }
            if (!bottomtotop){
                bottomtotop=fn(r-a,c);
            }
            if (!righttoleft){
                righttoleft=fn(r,c+a);
            }
            if (!lefttoright){
                lefttoright=fn(r,c-a);
            }
        }

    }

    public void knight(){
        int a,b;
        for (a = -2; a <= 2; a++) {
            for (b = -2; b <=2 ; b++) {
                if(Math.abs(a)+Math.abs(b)==3){
                    boolean t=fn(r+a,c+b);
                }
            }
        }
    }

    public void bishop() {
        boolean pf = false, pb = false, of = false, ob = false;
        for (int a = 1; a < 8; a++) {
            if (!pf) {
                pf = fn(r + a, c + a);
            }
            if (!pb) {
                pb = fn(r - a, c - a);
            }
            if (!of) {
                of = fn(r + a, c - a);
            }
            if (!ob) {
                ob = fn(r - a, c + a);
            }
        }
    }

    public  boolean fn(int a,int b){

        if (a>=0 && a<8 && b>=0 && b<8){
            if (BOARD[a][b][0]!=0){
                if (BOARD[a][b][0]==BOARD[r][c][0]){
                    possible_rows.add(a);
                    possible_columns.add(b);
                    possible_move_type.add(true);
                    return true;
                }
                else {
                    return true;
                }
            }
            else {
                possible_rows.add(a);
                possible_columns.add(b);
                possible_move_type.add(false);
                return false;
            }
        }
        else {
            return false;
        }

    }

    public void pawn(){
        if (color){
            if(r==1 && BOARD[r+2][c][0]==0 && super.move2[0][c]){

                movetwo=true;
                movetwo_row=r+2;
                movetwo_column=c;
            }
            if (BOARD[r+1][c][0]==0){
                possible_rows.add(r+1);
                possible_columns.add(c);
                possible_move_type.add(false);
            }
            if (c>0 && BOARD[r+1][c-1][0]==-1){
                possible_rows.add(r+1);
                possible_columns.add(c-1);
                possible_move_type.add(true);
            }

            if (c<7 && BOARD[r+1][c+1][0]==-1){
                possible_rows.add(r+1);
                possible_columns.add(c+1);
                possible_move_type.add(true);
            }
        }
        else {
            if(r==6 && BOARD[r-2][c][0]==0 && super.move2[0][c]){

                movetwo=true;
                movetwo_row=r-2;
                movetwo_column=c;
            }
            if (BOARD[r-1][c][0]==0) {
                possible_rows.add(r - 1);
                possible_columns.add(c);
                possible_move_type.add(false);
            }
            if (c>0 && BOARD[r-1][c-1][0]==1){
                possible_rows.add(r-1);
                possible_columns.add(c-1);
                possible_move_type.add(true);
            }

            if (c<7 && BOARD[r-1][c+1][0]==1){
                possible_rows.add(r-1);
                possible_columns.add(c+1);
                possible_move_type.add(true);
            }
        }
    }
    public void king(){
        int a,b;
        for (a = r-1; a <r+1 ; a++) {
            for (b = c-1; b < c+1; b++) {
                if (a>=0 && a<=7 && b>=0 && b<=7){
                    //Log.d("king",a+""+b);
                    if(BOARD[a][b][0]==0){
                        Log.d("empty","this is empty"+a+""+b);
                        possible_move_type.add(false);
                        possible_rows.add(a);
                        possible_columns.add(b);
                    }
                    if (BOARD[a][b][0]!=BOARD[r][c][0] && BOARD[a][b][0]!=0){
                        //Log.d("present","opposite here"+a+""+b);
                        possible_move_type.add(true);
                        possible_rows.add(a);
                        possible_columns.add(b);
                    }
                }
            }
        }
    }
}
