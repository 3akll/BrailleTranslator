package com.example.brailletranslator;

//import android.content.Context;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
//import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

        ImageButton clearButton = findViewById(R.id.clearInputButton);
        ImageButton copyButton = findViewById(R.id.copyResultButton);

        // Add a Button Click Listener that translates the input to the braille result when clicked
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Hide keyboard when click Translate button
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);*/

                String textToTranslate = inText.getText().toString(); // textToTranslate contain the input we want to translate
                String result = BrailleTranslator.translate(textToTranslate); // Send textToTranslate to translate() in BrailleTranslator.java to translate it
                outText.setText(result);

                // Show the copy button only if the result is not empty and doesn't contain only '?'
                if (!result.isEmpty() && !result.matches("\\?+")) {
                    copyButton.setVisibility(View.VISIBLE);
                }
                else {
                    copyButton.setVisibility(View.GONE);
                }
            }
        });

        // Add a Button Click Listener that clears the input and the result when clicked
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inText.setText("");
                outText.setText("");

                // Hide the copy button when the result is empty
                copyButton.setVisibility(View.GONE);
            }
        });

        // Add a Button Click Listener that copies the result to the clipboard when clicked
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager myClipboard;
                myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

                ClipData myClip;
                myClip = ClipData.newPlainText("result", outText.getText().toString());
                myClipboard.setPrimaryClip(myClip);
            }
        });
    }
}