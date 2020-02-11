package com.lotex.android.simplepasswordgenerator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private Button mGenerateButton;
    private TextView mTextPassword;
    private ClipboardManager mClipboard;
    private ClipData mClip;
    private Spinner numSpinner;

    // CheckBox variables
    private CheckBox mUpperLower;
    private CheckBox mSymbols;
    private CheckBox mNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning views to variables
        mGenerateButton = findViewById(R.id.button_generate);
        mTextPassword = findViewById(R.id.text_password);
        numSpinner = findViewById(R.id.spinner_passwordlength);

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

        // Set up spinner

        // Initialize spinner array
        ArrayList<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Select password length...");

        for (int i = 4; i <= 16; i++) {
            spinnerOptions.add(String.valueOf(i));
            //Log.d(LOG_TAG, "position " + (i-4) + " in spinnerOptions contains: " + spinnerOptions.get(i-4));
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerOptions) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        numSpinner.setAdapter(spinnerArrayAdapter);

        numSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
