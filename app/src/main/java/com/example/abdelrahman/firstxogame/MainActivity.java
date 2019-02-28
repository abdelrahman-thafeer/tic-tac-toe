package com.example.abdelrahman.firstxogame;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout resultLayout;
    ImageView imageView;
    TextView resultText;
    ConstraintLayout father;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultLayout = findViewById(R.id.resultLayout);
        resultLayout.setVisibility(View.INVISIBLE);

        resultText = findViewById(R.id.textView);
        father = findViewById(R.id.FatherLayout);
        gameOnGoing = true;
        idArray = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0));

        resultLayout.setScaleX(0f);
        resultLayout.setScaleY(0f);

    }

    public void playAgain(View v){
        for(int i=0;i<idArray.size();i++){
            idArray.set(i,0);
        }
        resultLayout.setVisibility(View.INVISIBLE);

        player = 1;

        for(int i =0;i<father.getChildCount();i++){
            if(father.getChildAt(i) instanceof ImageView){
               imageView = (ImageView) father.getChildAt(i);
               imageView.setImageResource(0);
            }
        }
        imageView = findViewById(R.id.imageView9);
        imageView.setImageResource(R.drawable.board);
        gameOnGoing = true;
        resultLayout.setScaleX(0f);
        resultLayout.setScaleY(0f);
        winner = 0;
    }

    int winner;
    // 0 = non, 1 = red , 2 = yellow.
    int player = 1;
    //store the player at each tag of slots taken
    ArrayList<Integer> idArray;
    int[][] winningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameOnGoing;

    public void makeMove(View v) {
        if (gameOnGoing) {
            ImageView imageView = (ImageView) v;
            int tag = Integer.parseInt((String) imageView.getTag());
            if (idArray.get(tag) == 0) {

                if (player == 1) {
                    imageView.setImageResource(R.drawable.red);
                    idArray.set(tag,player);
                    player = 2;
                    imageView.setTranslationY(-1000f);
                    imageView.animate().translationYBy(1000f).setDuration(500);

                } else {
                    imageView.setImageResource(R.drawable.yellow);
                    idArray.set(tag,player);
                    player = 1;
                    imageView.setTranslationX(-1000f);
                    imageView.animate().translationXBy(1000f).setDuration(500);
                }


                for (int[] fraction : winningPosition) {

                    for (int i = 0; i < fraction.length; i++) {
                        if (idArray.get(fraction[0]) == idArray.get(fraction[1]) && idArray.get(fraction[1]) == idArray.get(fraction[2]) && idArray.get(fraction[0]) != 0) {
                            winner = idArray.get(fraction[0]);
                            if (winner == 1) {
                                resultText.setText("RED HAS WON!!!");
                                resultLayout.setBackgroundColor(Color.RED);

                            } else if(winner ==2 ) {
                                resultText.setText("YELLOW HAS WON!!!");
                                resultLayout.setBackgroundColor(Color.YELLOW);
                            }
                            resultLayout.setVisibility(View.VISIBLE);
                            resultLayout.animate().scaleX(1f).scaleY(1f).rotationXBy(720f).setDuration(1500);
                            gameOnGoing = false;
                        }

                    }

                }

                //checks if there is any unplayed position, and calls for draw if so and no1 has won yet.
              if(!idArray.contains(0) && winner == 0){
                  resultText.setText("DRAW!!!");
                  resultLayout.setBackgroundColor(Color.BLUE);
                  resultLayout.setVisibility(View.VISIBLE);
                  resultLayout.animate().scaleX(1f).scaleY(1f).rotationXBy(720f).setDuration(2000);
                  gameOnGoing = false;
              }



            }
        }
    }
}
