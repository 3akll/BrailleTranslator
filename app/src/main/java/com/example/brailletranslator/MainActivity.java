package com.example.brailletranslator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Connect each Java variable to its XML element
        EditText inText = findViewById(R.id.inputText);
        Button tButton = findViewById(R.id.translateButton);
        TextView outText = findViewById(R.id.outputText);

        // Add a Button Click Listener that translates the input to the braille result when clicked
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToTranslate = inText.getText().toString(); // textToTranslate contain the input we want to translate
                StringBuilder result = BrailleTranslator.translate(textToTranslate); // Send textToTranslate to translate() in BrailleTranslator.java to translate it
                outText.setText(result);
            }
        });
    }
}