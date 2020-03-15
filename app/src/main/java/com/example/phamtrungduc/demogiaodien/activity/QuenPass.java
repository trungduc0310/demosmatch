package com.example.phamtrungduc.demogiaodien.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.phamtrungduc.demogiaodien.R;

import androidx.appcompat.app.AppCompatActivity;

public class QuenPass extends AppCompatActivity {

    EditText et_quen;
    Button btn_quen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fogotpass);

        View();

        btn_quen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    //region View
    private void View() {
        et_quen = findViewById(R.id.edt_quenpass);
        btn_quen = findViewById(R.id.btn_quenpass);
    }
    //endregion
}
