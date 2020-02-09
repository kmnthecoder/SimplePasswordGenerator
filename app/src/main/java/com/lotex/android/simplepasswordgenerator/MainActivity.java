package com.lotex.android.simplepasswordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mGenerateButton;
    private TextView mTextPassword;
    private ClipboardManager mClipboard;
    private ClipData mClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning views to variables
        mGenerateButton = findViewById(R.id.button_generate);
        mTextPassword = findViewById(R.id.text_password);

        // Set on click listener for generate password button
        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder function
                // ***REPLACE FUNCTIONALITY HERE***
                Toast.makeText(getApplicationContext(), "Generate Password", Toast.LENGTH_SHORT).show();
            }
        });

        // Set on click listener for copy and pasting the generated password
        mTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Copy text to universal android clipboard
                mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text = mTextPassword.getText().toString();
                mClip = ClipData.newPlainText("text", text);
                mClipboard.setPrimaryClip(mClip);

                // Toast to say text copied
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
