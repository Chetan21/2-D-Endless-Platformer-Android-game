package com.example.chetan.gametestappversion3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Chetan on 3/9/2016.
 */
public class InstructionsActivity extends Activity{
    private Button backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_activity);
        backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionsActivity.this,MainMenuActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
