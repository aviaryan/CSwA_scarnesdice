package in.aviaryan.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView txtUserScore, txtComputerScore;
    ImageView imgDice;
    Button btnRoll, btnHold, btnReset;
    private Random random = new Random();
    private int currentUserScore;
    private final int MAX_SCORE = 100;
    private int diceIcons [] = {
            R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(in.aviaryan.scarnesdice.R.layout.activity_main);
        // instantiate views
        txtUserScore = (TextView) findViewById(R.id.userScore);
        txtComputerScore = (TextView) findViewById(R.id.computerScore);
        imgDice = (ImageView) findViewById(R.id.diceImage);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        btnHold = (Button) findViewById(R.id.btnHold);
        btnReset = (Button) findViewById(R.id.btnReset);
        // start game
        // onStart()
        // event listeners
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerTurn();
            }
        });
    }

    private void computerTurn(){
        int currentScore = 0, num;
        Random newRandom = new Random(); // using the same random affects seed
        int currentComputerScore = Integer.parseInt(txtComputerScore.getText().toString());
        do {
            num = random.nextInt(6) + 1;
            imgDice.setImageResource(diceIcons[num-1]);
            if (num == 1){
                currentScore = 0;
                break;
            } else {
                currentScore += num;
                if ((currentComputerScore + currentScore) >= MAX_SCORE){
                    endGame("Computer");
                    return;
                }
            }
        } while (newRandom.nextInt(8) < 6); // 75 % chances to play
        currentComputerScore += currentScore;
        txtComputerScore.setText("" + currentComputerScore);
        // set current turn user score back to 0
        currentUserScore = 0;
    }

    private void rollDice(){
        int num = random.nextInt(6) + 1;
        int currentScore = Integer.parseInt(txtUserScore.getText().toString());
        imgDice.setImageResource(diceIcons[num-1]);
        if (num == 1){
            currentScore -= currentUserScore;
            txtUserScore.setText(currentScore + "");
            computerTurn();
        } else {
            currentScore += num;
            if (currentScore >= MAX_SCORE){
                endGame("User");
                return;
            }
            currentUserScore += num;
            txtUserScore.setText(currentScore + "");
        }
    }

    private void endGame(String winner){
        (Toast.makeText(this, "Game over. " + winner + " won", Toast.LENGTH_LONG)).show();
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUserScore = 0;
        txtUserScore.setText("0");
        txtComputerScore.setText("0");
    }
}
