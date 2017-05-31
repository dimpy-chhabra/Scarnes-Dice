package com.example.dimpy.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int rollNum=0;

    TextView textView1, textView2;
    ImageView imageView;
    Button buttonRoll, buttonReset, buttonHold;
    int countScoreSelf=0, countScoreComp=0;
    int countTurnSelf=0, countTurnComp=0;
    int images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6 };

    //flagTurn is described to define the turn.
    //if flagTurn=0, => user's turn and if flagturn=1 => Computer's turn
    int flagTurn=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        imageView = (ImageView)findViewById(R.id.diceImage);
        buttonHold = (Button)findViewById(R.id.buttonHold);
        buttonReset = (Button)findViewById(R.id.buttonReset);
        buttonRoll = (Button)findViewById(R.id.buttonRoll);

        buttonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if flagTurn == 0

                int i = rollDie();
                if(i==1){
                    Toast.makeText(MainActivity.this, "You scored a 1! Boo-Hoo! You lose your points", Toast.LENGTH_SHORT).show();
                    countTurnSelf=0;
                    //textView1.setText("Your score : "+countScoreSelf);
                    //countScoreSelf doesn't change
                    flagTurn = 1;
                    Log.e("*******TURN BIT*******", "" + flagTurn);
                    computerTurn();
                }else{
                    countTurnSelf += i;
                    Log.e("in Roll ", "Current Turn Played "+i );
                    Log.e("in Roll ", "Current Score is "+countTurnSelf );
                    Toast.makeText(MainActivity.this, "Your score so far is "+countTurnSelf+" to test your luck again... Roll Else Hold", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.dice1);
                reset();
            }
        });

        buttonHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagTurn==0 && countTurnSelf!=0){
                    Toast.makeText(MainActivity.this, "You held your score. Your newly added score is "+countTurnSelf, Toast.LENGTH_SHORT).show();
                    countScoreSelf += countTurnSelf;
                    Log.e("In Hold ","newly added score is "+countTurnSelf);
                    textView1.setText("Your score : "+countScoreSelf);
                    Log.e("In Hold ","***FINAL SCORE SO FAR***"+countScoreSelf);
                    countTurnSelf = 0;
                    countTurnComp = 0;
                    //check for victory of self
                    checkOfVictory();
                    flagTurn = 1;
                    Log.e("*******TURN BIT*******", "" + flagTurn);
                    computerTurn();
                }else {
                    Toast.makeText(MainActivity.this, "INVALID MOVE", Toast.LENGTH_SHORT).show();
                    Log.e("In Hold "," INVALID MOVE FLAGTURN"+flagTurn+" countSelfTurn ="+countTurnSelf);
                }
            }
        });

    }

    public void computerTurn(){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int rollNum = new Random().nextInt(6);
                if(rollNum!=0 && (countScoreComp+countTurnComp)<countScoreSelf) {
                    Log.e("In Comp's turn's if "," rolled"+rollNum);
                    imageView.setImageResource(images[rollNum]);
                    countTurnComp += (rollNum+1);
                    computerTurn();
                }else{
                    countScoreComp += countTurnComp;
                    Log.e("in computer Turn  ","***FINAL SCORE SO FAR*** = "+countScoreComp);
                    textView2.setText("Computer's score : "+countScoreComp);
                    checkOfVictory();
                    countTurnComp=0;
                    flagTurn=0;
                    Log.e("*******TURN BIT*******",""+flagTurn);

                }
            }
        },1000);

/*
        while(num!=0 && countTurnComp<20){
            //Log.e("in computer Turn  "," turn = "+(i+1));
            Log.e("in computer Turn  "," Turn's total so far score => "+countTurnComp);
            num = rollDie();
        }
  */
        //check for victory of computer
        }

    public int rollDie() {
            /*new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            rollNum = new Random().nextInt(6);
            imageView.setImageResource(images[rollNum]);
                }
            },1000);
*/
            rollNum = new Random().nextInt(6);
            imageView.setImageResource(images[rollNum]);
            return rollNum+1;
   }
   public void checkOfVictory(){
           if(countScoreComp>=100) VictoryComp();
           if(countScoreSelf>=100) VictorySelf();
   }

    public void VictoryComp(){
        imageView.setImageResource(R.drawable.youlost);
        reset();
    }
    public void VictorySelf(){
        imageView.setImageResource(R.drawable.youwon);
        reset();
    }
    public void reset(){
        countScoreComp = 0;
        countScoreSelf = 0;
        countTurnSelf = 0;
        countTurnComp = 0;
        textView1.setText("Your score : 0");
        textView2.setText("Computer's score : 0");
    }
}