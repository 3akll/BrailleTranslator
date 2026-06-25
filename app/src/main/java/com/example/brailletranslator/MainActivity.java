package com.example.brailletranslator;

import android.content.Context;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Main screen for the Braille Translator app
 * Handles user input, mode switching, and UI actions
 */
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

        // Action buttons
        Button tButton = findViewById(R.id.translateButton);
        ImageButton clearButton = findViewById(R.id.clearInputButton);
        copyButton = findViewById(R.id.copyResultButton);

        // Mode labels
        ImageButton switchModeButton = findViewById(R.id.switchModeButton);
        leftModeLabel = findViewById(R.id.leftModeLabel);
        rightModeLabel = findViewById(R.id.rightModeLabel);

        // Input and output text fields
        inText = findViewById(R.id.inputText);
        outText = findViewById(R.id.outputText);

        // Keyboard containers
        LinearLayout keyboardContainerLetters = findViewById(R.id.keyboardContainerLetters);
        LinearLayout keyboardContainerNumbers = findViewById(R.id.keyboardContainerNumbers);
        LinearLayout keyboardContainerPunctuations = findViewById(R.id.keyboardContainerPunctuations);

        // Keyboard category selectors
        RadioButton radioButtonLetters = findViewById(R.id.radioButtonLetters);
        RadioButton radioButtonNumbers = findViewById(R.id.radioButtonNumbers);
        RadioButton radioButtonPunctuations = findViewById(R.id.radioButtonPunctuations);

        // 1st row of letters
        setupKey(R.id.key_q, "⠟");
        setupKey(R.id.key_w, "⠺");
        setupKey(R.id.key_e, "⠑");
        setupKey(R.id.key_r, "⠗");
        setupKey(R.id.key_t, "⠞");
        setupKey(R.id.key_y, "⠽");
        setupKey(R.id.key_u, "⠥");

        // 2nd row of letters
        setupKey(R.id.key_i, "⠊");
        setupKey(R.id.key_o, "⠕");
        setupKey(R.id.key_p, "⠏");
        setupKey(R.id.key_a, "⠁");
        setupKey(R.id.key_s, "⠎");
        setupKey(R.id.key_d, "⠙");
        setupKey(R.id.key_f, "⠋");

        // 3rd row of letters
        setupKey(R.id.key_g, "⠛");
        setupKey(R.id.key_h, "⠓");
        setupKey(R.id.key_j, "⠚");
        setupKey(R.id.key_k, "⠅");
        setupKey(R.id.key_l, "⠇");
        setupKey(R.id.key_z, "⠵");
        setupKey(R.id.key_x, "⠭");

        // 4th row of letters
        setupKey(R.id.key_c, "⠉");
        setupKey(R.id.key_v, "⠧");
        setupKey(R.id.key_b, "⠃");
        setupKey(R.id.key_n, "⠝");
        setupKey(R.id.key_m, "⠍");

        // 5th row of letters for space key only
        setupKey(R.id.key_space, "⠀");

        // 1st row of numbers
        setupKey(R.id.key_1, "⠼⠁");
        setupKey(R.id.key_2, "⠼⠃");
        setupKey(R.id.key_3, "⠼⠉");
        setupKey(R.id.key_4, "⠼⠙");
        setupKey(R.id.key_5, "⠼⠑");

        // 2nd row of numbers
        setupKey(R.id.key_6, "⠼⠋");
        setupKey(R.id.key_7, "⠼⠛");
        setupKey(R.id.key_8, "⠼⠓");
        setupKey(R.id.key_9, "⠼⠊");
        setupKey(R.id.key_0, "⠼⠚");

        // 1st row of punctuations
        setupKey(R.id.key_comma, "⠂");
        setupKey(R.id.key_semicolon, "⠆");
        setupKey(R.id.key_colon, "⠒");
        setupKey(R.id.key_dot, "⠲");
        setupKey(R.id.key_exclamation, "⠦");
        setupKey(R.id.key_interrogation, "⠖");
        setupKey(R.id.key_open_parenthesis, "⠐⠣");

        // 2nd row of punctuations
        setupKey(R.id.key_close_parenthesis, "⠐⠜");
        setupKey(R.id.key_forward_slash, "⠸⠌");
        setupKey(R.id.key_back_slash, "⠸⠡");
        setupKey(R.id.key_hyphen, "⠤");

        // Add a TextWatcher to the input field to show/hide the clear button depending on the input
        inText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    clearButton.setVisibility(View.GONE);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        /*
         * Add a Button Click Listener that translates the input using the selected mode when clicked
         *      Mode 1: Text to Braille
         *      Mode 2: Braille to Text
         */
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Hide the keyboard when the button is clicked
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

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

                /*
                 * Show the copy button only:
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

                // Flip the mode and update the labels
                isTextToBraille = !isTextToBraille;
                updateModeLabels();
            }
        });

        // Add a Button Click Listener that shows the Letters keyboard when clicked
        radioButtonLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardContainerLetters.setVisibility(View.VISIBLE);
                keyboardContainerNumbers.setVisibility(View.GONE);
                keyboardContainerPunctuations.setVisibility(View.GONE);
            }
        });

        // Add a Button Click Listener that shows the Numbers keyboard when clicked
        radioButtonNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardContainerNumbers.setVisibility(View.VISIBLE);
                keyboardContainerLetters.setVisibility(View.GONE);
                keyboardContainerPunctuations.setVisibility(View.GONE);
            }
        });

        // Add a Button Click Listener that shows the Punctuations keyboard when clicked
        radioButtonPunctuations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardContainerPunctuations.setVisibility(View.VISIBLE);
                keyboardContainerLetters.setVisibility(View.GONE);
                keyboardContainerNumbers.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Updates the text labels on the screen to reflect whether the app is in
     * "Text to Braille" or "Braille to Text" mode
     */
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

    /**
     * Initializes a key in the keyboard with a specific button and braille character
     * Add a Button Click Listener that adds the braille character to the input field when clicked
     * @param buttonId id of the button to be set up
     * @param brailleChar the braille character to be entered in the input field
     */
    private void setupKey(int buttonId, String brailleChar) {
        Button button = findViewById(buttonId);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inText.append(brailleChar);
            }
        });
    }
}
