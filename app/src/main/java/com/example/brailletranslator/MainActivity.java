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

    private boolean isTextToBraille = true;

    // Views used by multiple methods and listeners
    private EditText inText;
    private TextView outText;
    private ImageButton copyButton;
    private TextView leftModeLabel;
    private TextView rightModeLabel;

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

        // Initialize views from the XML layout
        Button tButton = findViewById(R.id.translateButton);
        ImageButton clearButton = findViewById(R.id.clearInputButton);
        ImageButton switchModeButton = findViewById(R.id.switchModeButton);

        inText = findViewById(R.id.inputText);
        outText = findViewById(R.id.outputText);
        copyButton = findViewById(R.id.copyResultButton);
        leftModeLabel = findViewById(R.id.leftModeLabel);
        rightModeLabel = findViewById(R.id.rightModeLabel);

        /* Add a Button Click Listener that translates the input using the selected mode when clicked
         *      Mode 1: Text to Braille
         *      Mode 2: Braille to Text
         */
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Hide keyboard when click Translate button
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);*/

                // inputToTranslate contains the input we want to translate
                String inputToTranslate = inText.getText().toString();
                String result;

                /*
                 * Check if the input is braille
                 *
                 *      ^              start of string
                 *      [ ... ]        allowed characters
                 *      \\s            whitespace
                 *      \\u2800-\\u28FF Braille Unicode range
                 *      +              one or more
                 *      $              end of string
                 *
                 * ==> The whole input contains only whitespace or Braille symbols
                */

                if (inputToTranslate.matches("^[\\s\\u2800-\\u28FF]+$")) {
                    isTextToBraille = false;
                    updateModeLabels();

                    // Send inputToTranslate to translateBrailleToText() in BrailleTranslator.java to translate it
                    result = BrailleTranslator.translateBrailleToText(inputToTranslate);
                    outText.setText(result);
                }
                else {
                    isTextToBraille = true;
                    updateModeLabels();

                    // Send inputToTranslate to translateTextToBraille() in BrailleTranslator.java to translate it
                    result = BrailleTranslator.translateTextToBraille(inputToTranslate);
                    outText.setText(result);
                }

                /* Show the copy button only:
                 *      1- if the result is not empty
                 *      2- and doesn't contain only '?' characters
                 */
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

        // Add a Button Click Listener that switches the mode when clicked
        switchModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Flip th mode and update the labels
                isTextToBraille = !isTextToBraille;
                updateModeLabels();
            }
        });
    }

    // Updates the labels of the left and right modes
    private void updateModeLabels() {
        if (isTextToBraille) {
            leftModeLabel.setText(R.string.mode_text);
            rightModeLabel.setText(R.string.mode_braille);
        }
        else {
            leftModeLabel.setText(R.string.mode_braille);
            rightModeLabel.setText(R.string.mode_text);
        }
    }
}