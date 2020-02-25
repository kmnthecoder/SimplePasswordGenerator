package com.lotex.android.simplepasswordgenerator.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lotex.android.simplepasswordgenerator.R;

import java.util.ArrayList;
import java.util.Random;

public class PasswordGenerator extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = PasswordGenerator.class.getSimpleName();

    private TextView mTextPassword;
    private ClipboardManager mClipboard;
    private ClipData mClip;
    private Spinner numSpinner;
    private CheckBox mUpperLower, mSymbols, mNumbers;
    private EditText mExclude;
    private Button mGenerateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);
        // Set views to variables
        viewAttach();
        // Set up spinner and populate options
        spinnerSetup();
    }

    // Copies password when password text itself is tapped
    public void passwordTextCopy() {
        // Copy text to universal android clipboard
        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = mTextPassword.getText().toString();
        mClip = ClipData.newPlainText("text", text);
        mClipboard.setPrimaryClip(mClip);
        Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
    }

    // Set up spinner and populate with options
    public void spinnerSetup() {
        ArrayList<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Select password length...");

        // Populate spinner with options
        for (int i = 4; i <= 16; i++) {
            spinnerOptions.add(String.valueOf(i));
        }

        // Disable first options for placeholder text, and gray it out
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerOptions) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Set layout and attach adapter
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        numSpinner.setAdapter(spinnerArrayAdapter);

        // Set listener to item selected in spinner
        numSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disabled and it is used for hint
                if (position > 0) {
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

    public void generatePassword() {
        // Check if there is a number selection
        if (numSpinner.getSelectedItemPosition() != 0) {

            // Initialize variables and default random password
            StringBuilder randomChars = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
            StringBuilder generatedPassword = new StringBuilder();
            Random random = new Random();
            int passwordLength = Integer.parseInt(numSpinner.getSelectedItem().toString());

            // Check options, change selection of random characters if selected
            if (mUpperLower.isChecked()) {
                randomChars.append(randomChars.toString().toUpperCase());
            }
            if (mSymbols.isChecked()) {
                randomChars.append("!@#$%^&*");
            }
            if (mNumbers.isChecked()) {
                randomChars.append("0123456789");
            }

            mExclude.setText(mExclude.getText().toString().replaceAll("\\[\\]\\\\", ""));
            if (!mExclude.getText().toString().matches("")) {
                String rChars = randomChars.toString();
                rChars = rChars.replaceAll("[" + mExclude.getText().toString() + "]", "");
                randomChars = new StringBuilder(rChars);
            }

            // generate random password
            for (int i = 0; i < passwordLength; i++) {
                generatedPassword.append(randomChars.charAt(random.nextInt(randomChars.length())));
            }
            mTextPassword.setText(generatedPassword);
        } else {
            Toast.makeText(getApplicationContext(), "Please select password length", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_generate:
                generatePassword();
                break;
            case R.id.text_password:
                passwordTextCopy();
                break;
            default:
                break;
        }
    }

    public void viewAttach() {
        mGenerateButton = findViewById(R.id.button_generate);
        mGenerateButton.setOnClickListener(this);

        mTextPassword = findViewById(R.id.text_password);
        mTextPassword.setOnClickListener(this);

        numSpinner = findViewById(R.id.spinner_passwordlength);
        mUpperLower = findViewById(R.id.checkBox_upperlower);
        mSymbols = findViewById(R.id.checkBox_symbols);
        mNumbers = findViewById(R.id.checkBox_numbers);
        mExclude = findViewById(R.id.editText_exclude);
    }
} // end of class