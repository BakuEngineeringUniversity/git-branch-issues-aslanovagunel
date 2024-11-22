package gunel.eslanova.chess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {


    int[][][] BOARD=new int[8][8][2];

    boolean is_selected=false;
    int selected_row=0,selected_column=0;
    int getSelected_row=0;
    boolean turn=true;
    int movetwo_r=0,movetwo_c=0;
    boolean possible_move2=false;
    boolean king_castle_white=true,king_castle_black=true;
    boolean  rook_white_left=true,rook_white_right=true;
    boolean  rook_black_left=true,rook_black_right=true;

    List<Integer> possible_rows=new ArrayList<Integer>();
    List<Integer> possible_columns=new ArrayList<Integer>();
    List<Boolean> possible_type=new ArrayList<Boolean>();
    //boolean[][] move2=new boolean[2][8];
    boolean move2[][]={{true,true,true,true,true,true,true,true},{true,true,true,true,true,true,true,true}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle = intent.getExtras();

        String player1 = bundle.getString("name1", "Player1");
        String player2 = bundle.getString("name2", "Player2");

        TextView player1name = findViewById(R.id.player1name);
        TextView player2name = findViewById(R.id.player2name);

        player1name.setText(player1);
        player2name.setText(player2);
        start();
      //  maketoast(player1 + player2);
    }


    public  void  tap(View view){
        //maketoast("tapped");
        if (!finished){
            int row=Integer.parseInt(view.getTag().toString())/10;
            int column=Integer.parseInt(view.getTag().toString())%10;
            maketoast(row+""+column);
            if (is_selected){
                if (BOARD[row][column][0]==BOARD[selected_row][selected_column][0]){
                    deselect();
                    select(row,column);
                }
                else {

                    moveifpossible(row,column);
                }
            }
            else {
                if(BOARD[row][column][0]!=0){
                    // maketoast("going to select");
                    select(row,column);
                }
            }
        }

    }

    boolean finished=false;

    boolean king_status_white=true,king_status_black=true;
    public void checkkingstatus(){
        Log.d("schecking",king_status_white+""+king_status_black);
        boolean white=false,black=true;
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                if (BOARD[a][b][0]==1 && BOARD[a][b][1]==5){
                    white=true;
                }
                if (BOARD[a][b][0]==-1 && BOARD[a][b][1]==5){
                    black=true;
                }
            }
        }
        if (white){
            king_status_white=false;
        } else if (black) {
            king_castle_black=false;
        }
    }

    public void checkwin(){

        checkkingstatus();
        TextView status1=findViewById(R.id.status1);
        TextView status2=findViewById(R.id.status2);
        if (king_castle_black){
            status1.setText(R.string.win);
            status2.setText(R.string.lose);
            finished=true;
        }
        else if (!king_castle_black){
            status1.setText(R.string.lose);
            status2.setText(R.string.win);
            finished=true;
        }
    }

    boolean is_check=false;
    int check_at_row=0,check_at_column;
    //int check_from_row=0,check_from_column;
    List<Integer> check_from_row=new ArrayList<Integer>();
    List<Integer> check_from_column=new ArrayList<Integer>();

    public  boolean fn(int a,int b){

        if (a>=0 && a<8 && b>=0 && b<8){
            if (BOARD[a][b][0]!=0){
                if (BOARD[a][b][0]==BOARD[a][b][0]){

                    is_check=true;
                    check_from_row.add(a);
                    check_from_column.add(b);
                    return true;
                }
                else {

                    return true;
                }
            }
            else {

                return false;
            }
        }
        else {
            return false;
        }

    }

    public void check_for_check(int r,int c){
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
        if (is_check){
            displaycheck(r,c);
        }
    }
    public void displaycheck(int r,int c){
        for (int i = 0; i < check_from_row.size(); i++) {
            set_backround_image(4,check_from_row.get(i),check_from_column.get(i));
        }
        set_backround_image(2,r,c);
    }


    public void moveifpossible(int r,int c){
        for(int a=0;a<possible_type.size();a++){
            if (possible_rows.get(a)==r && possible_columns.get(a)==c){
                set_image(BOARD[selected_row][selected_column][1],r,c,get_color(BOARD[selected_row][selected_column][0]));
                set_image(0,selected_row,selected_column,true);
                set_backround_image(0,selected_row,selected_column);

                set_backround_image(0,r,c);

                if (possible_move2){
                    set_backround_image(0,movetwo_r,movetwo_c);
                }

               /* if (BOARD[selected_row][selected_column][1]==5){
                    if (BOARD[selected_row][selected_column][0]==1){
                        king_castle_white=false;
                    }
                    else if(BOARD[selected_row][selected_column][0]==-1) {
                        king_castle_black=false;
                    }
                }
                if (selected_row==0 && selected_column==0){
                    rook_white_left=false;
                }
                if (selected_row==0 && selected_column==7){
                    rook_white_right=false;
                }

                if (selected_row==7 && selected_column==0){
                    rook_black_left=false;
                }
                if (selected_row==7 && selected_column==7){
                    rook_black_left=false;
                }
                if (selected_row==7 && selected_column==0){
                    rook_black_right=false;
                }

                */
                BOARD[r][c][0]= BOARD[selected_row][selected_column][0];
                BOARD[r][c][1]= BOARD[selected_row][selected_column][1];

                BOARD[selected_row][selected_column][0]=0;
                BOARD[selected_row][selected_column][1]=0;

                for(int b=0;b<possible_type.size();b++){
                    set_backround_image(0,possible_rows.get(b),possible_columns.get(b));
                }

                is_selected=false;
                turn=!turn;
                changestatus();
               // maketoast(turn+"");
                break;
            }
        }
        if(BOARD[selected_row][selected_column][1]==1 && possible_move2 && r==movetwo_r && c==movetwo_c){
            set_image(BOARD[selected_row][selected_column][1],r,c,get_color(BOARD[selected_row][selected_column][0]));
            set_image(0,selected_row,selected_column,true);
            set_backround_image(0,selected_row,selected_column);

            set_backround_image(0,r,c);

            is_selected=false;
            possible_move2=false;
            if (turn){
                move2[0][selected_column]=false;
            }
            else{
                move2[1][selected_column]=false;
            }

            BOARD[r][c][0]= BOARD[selected_row][selected_column][0];
            BOARD[r][c][1]= BOARD[selected_row][selected_column][1];

            BOARD[selected_row][selected_column][0]=0;
            BOARD[selected_row][selected_column][1]=0;

            for(int b=0;b<possible_type.size();b++){
                set_backround_image(0,possible_rows.get(b),possible_columns.get(b));
            }



            turn=!turn;
            changestatus();

        }
        Log.d("fdata",king_status_white+""+king_status_black);
        checkwin();
    }


    public void select(int r,int c){
        if(turn==get_color(BOARD[r][c][0])){
            set_backround_image(1,r,c);
            is_selected=true;
            selected_row=r;
            selected_column=c;
            shomoves(r,c);
        }
    }

    public void shomoves(int r,int c){
        Moves move=new Moves(BOARD,r,c);
        move.calc_moves();
        possible_columns.clear();
        possible_rows.clear();
        possible_rows=move.possible_rows;
        possible_columns=move.possible_columns;
        possible_type=move.possible_type;

        Log.d("moves",possible_rows.toString());
        if(possible_type.isEmpty()){
           // maketoast("movement is possible");
            for(int a=0;a<possible_type.size();a++){

                if(possible_type.get(a)){
                    set_backround_image(2,possible_rows.get(a),possible_columns.get(a));

                }
                else {
                    set_backround_image(3,possible_rows.get(a),possible_columns.get(a));

                }
            }
        }
        if (BOARD[r][c][1]==1 && move.movetwo){
          //  maketoast("pawn");
            movetwo_r=move.movetwo_row;
            movetwo_c=move.movetwo_column;
            possible_move2=true;
            set_backround_image(3,movetwo_r,movetwo_c);
        }

        if (BOARD[r][c][1]==5){
            if (r==0){
                if (c==1){

                }
                else if(c==5){

                }
            }
        }
    }

    public void deselect(){
        set_backround_image(0,selected_row,selected_column);
        //Log.d("deselected",selected_row+""+selected_column)
        is_selected=false;
        for(int a=0;a<possible_type.size();a++){

            set_backround_image(0,possible_rows.get(a),possible_columns.get(a));
        }
        if (BOARD[selected_row][selected_column][1]==1 && possible_move2){
            set_backround_image(0,movetwo_r,movetwo_c);
        }
    }


    public void start() {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                BOARD[a][b][0]=0;
                BOARD[a][b][1]=0;
                set_image(0,a,b,true);

                if (a==1){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=1;
                    set_image(1,a,b,true);
                }

                if(a==6){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=1;
                    set_image(1,a,b,false);
                }

                if(a==0 && (b==0 || b==7)){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=2;
                    set_image(2,a,b,true);
                }

                if(a==7 && (b==0 || b==7)){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=1;
                    set_image(2,a,b,false);
                }

                if(a==0 && (b==1 || b==6)){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=3;
                    set_image(3,a,b,true);
                }

                if(a==7 && (b==1 || b==6)){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=3;
                    set_image(3,a,b,false);
                }
                if(a==0 && (b==2 || b==5)){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=4;
                    set_image(4,a,b,true);
                }

                if(a==7 && (b==2 || b==5)){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=4;
                    set_image(4,a,b,false);
                }

                if(a==0 && b==4){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=5;
                    set_image(6,a,b,true);
                }

                if(a==7 && b==4){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=5;
                    set_image(6,a,b,false);
                }

                if(a==0 && b==3){
                    BOARD[a][b][0]=1;
                    BOARD[a][b][1]=6;
                    set_image(5,a,b,true);
                }

                if(a==7 && b==3){
                    BOARD[a][b][0]=-1;
                    BOARD[a][b][1]=6;
                    set_image(5,a,b,false );
                }
            }
        }

        TextView status1=findViewById(R.id.status1);
        status1.setText(R.string.turnmessage);
    }


    public void set_image(int image, int r, int c, boolean color) {

        ImageView im = findViewById(R.id.board).findViewWithTag(r + "" + c);
        if (image == 0) {
            im.setImageResource(R.drawable.baseline_lock_24);
        }
        if (image==1){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24);
            }
            else {
                im.setImageResource(R.drawable.baseline_lock_24 /*pawn_black*/);
            }
        }
        if (image==2){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24 /*rook_white*/);
            }
            else {
                im.setImageResource(R.drawable.baseline_lock_24 /*rook_black*/);
            }
        }
        if (image==3){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24 /*bishop_white*/);
            }
            else {
                im.setImageResource(R.drawable.baseline_lock_24 /*bishop_black*/);
            }
        }
        if (image==4){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24 /*knight_white*/);
            }
            else {
                im.setImageResource(R.drawable.knight_black);
            }
        }
        if (image==5){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24 /*king_white*/);
            }
            else {
                im.setImageResource(R.drawable.baseline_lock_24 /*king_black*/);
            }
        }
        if (image==6){
            if (color){
                im.setImageResource(R.drawable.baseline_lock_24 /*queen_white*/);
            }
            else {
                im.setImageResource(R.drawable.baseline_lock_24 /*queen_black*/);
            }
        }

    }

    public void set_backround_image(int image,int r,int c) {
        ImageView im = findViewById(R.id.board).findViewWithTag(r + "" + c);
        if ((r + c) % 2 == 0) {
            switch (image) {
                case 0:
                    im.setBackgroundResource(R.drawable.white11);break;
                case 1:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*selected_white*/);break;
                case 2:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*killable_white*/);break;
                case 3:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*movable_white*/);break;
                case 4:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*check_white*/);break;

            }
        } else {
            switch (image) {
                case 0:
                    im.setBackgroundResource(R.drawable.blacks);break;
                case 1:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*selected_black*/);break;
                case 2:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*killable_black*/);break;
                case 3:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*movable_black*/);break;
                case 4:
                    im.setBackgroundResource(R.drawable.baseline_lock_24 /*check_black*/);break;
            }
        }
    }

    public boolean get_color(int a){
        if(a==1){
            return true;
        }
        return false;
    }


    public void changestatus(){
        TextView status1=findViewById(R.id.status1);
        TextView status2=findViewById(R.id.status2);
        if (turn){
            status1.setText(R.string.turnmessage);
            status2.setText("");
        }
        else {
            status1.setText("");
            status2.setText(R.string.turnmessage);
        }
    }

    public void maketoast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}