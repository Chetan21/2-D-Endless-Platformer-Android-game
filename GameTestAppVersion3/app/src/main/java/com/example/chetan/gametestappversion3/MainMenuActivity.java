package com.example.chetan.gametestappversion3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Button startGameButton;
    private Button instrButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        startGameButton = (Button) findViewById(R.id.start_game);
        startGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,StartGameActivity.class);
                startActivity(intent);
                finish();

            }
        });
        instrButton = (Button) findViewById(R.id.instructions);
        instrButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,InstructionsActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
