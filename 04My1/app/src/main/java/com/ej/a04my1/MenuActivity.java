package com.ej.a04my1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        container = findViewById(R.id.container);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)  getSystemService(Cotext.LAOUT_INFLATER_SERVICE);
                CheckBox checkBox = container.findViewById(R.id.checkbox);
                checkBox.setText("로딩되었어요");
            }
        });
    }
}