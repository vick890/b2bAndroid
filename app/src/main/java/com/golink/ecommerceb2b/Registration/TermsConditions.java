package com.golink.ecommerceb2b.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.golink.ecommerceb2b.MainActivity;
import com.golink.ecommerceb2b.R;

public class TermsConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        Button registrationUpdateButton = findViewById(R.id.logButton);

        registrationUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TermsConditions.this, MainActivity.class);
                intent.putExtra("page", "main");
                startActivity(intent);
            }
        });
    }
}
