package com.example.slidepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private BoardView boardView;
    private int boardSize = 4;
    private ViewGroup homeView;
    private TextView moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeView = (ViewGroup) findViewById(R.id.main_layout);
        moves = (TextView) findViewById(R.id.moves);
        moves.setTextColor(Color.WHITE);
        moves.setTextSize(18);
        this.newGame();
    }

    @Override
    protected boolean onCreateOptionsMenu (Menu main) {
        getMenuInflater().inflate(R.menu.menu, main);
        return true;
    }

    private void newGame() {
        this.board = new Board(this.boardSize);
        this.board.addBoardChangeListener(boardChangeListener);

    }
}