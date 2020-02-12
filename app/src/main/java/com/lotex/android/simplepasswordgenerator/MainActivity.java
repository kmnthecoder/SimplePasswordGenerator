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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private TextView mTextPassword;
    private ClipboardManager mClipboard;
    private ClipData mClip;
    private Spinner numSpinner;
    private CheckBox mUpperLower, mSymbols, mNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set on click listener for generate password button
        addListenerGenerate();
        // Set on click listener for copy and pasting the generated password
        passwordTextCopy();
        // Set up spinner and populate options
        spinnerSetup();
        // Set views to variables
        checkBoxAttach();

    } // onCreate end

    // Add listener to generate password button
    public void addListenerGenerate() {

        // Set view to variable
        Button mGenerateButton = findViewById(R.id.button_generate);
        // Set on click listener for generate password button
        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if there is a number selection
                if (numSpinner.getSelectedItemPosition() != 0) {

                    //Toast.makeText(getApplicationContext(), "Generating Password", Toast.LENGTH_SHORT).show();

                    Log.d(LOG_TAG, "mUpperLower is checked: " + mUpperLower.isChecked() +
                            "\nmSymbols is checked: " + mSymbols.isChecked() +
                            "\nmNumbers is checked: " + mNumbers.isChecked() +
                            "\nPassword length is: " + numSpinner.getSelectedItem().toString());
                            //+ "\nPassword generated is: " + generatePassword());

                    mTextPassword.setText(generatePassword());


                } else {
                    Toast.makeText(getApplicationContext(), "Please select password length", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } // addListenerGenerate end

    // Copies password when password text itself is tapped
    public void passwordTextCopy() {

        // Set view to variable
        mTextPassword = findViewById(R.id.text_password);

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
    } // passwordTextCopy end

    // Set up spinner and populate with options
    public void spinnerSetup() {

        // Set view to variable
        numSpinner = findViewById(R.id.spinner_passwordlength);

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
                // First item is disable and it is used for hint
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
    } // spinnerSetup end

    public void checkBoxAttach() {
        mUpperLower = findViewById(R.id.checkBox_upperlower);
        mSymbols = findViewById(R.id.checkBox_symbols);
        mNumbers = findViewById(R.id.checkBox_numbers);
    } // checkBoxAttach end

    public String generatePassword() {
        //Toast.makeText(getApplicationContext(), "Generating Password", Toast.LENGTH_SHORT).show();

        StringBuilder randomChars = new StringBuilder("qwertyuiopasdfghjklzxcvbnm");
        StringBuilder generatedPassword = new StringBuilder();
        Random random = new Random();
        int passwordLength = Integer.parseInt(numSpinner.getSelectedItem().toString());

        /*
        ** TO DO **
        * Check for upper/lower case
        * Check for symbols
        * Check for numbers
        * Check for exclude any characters
         */

        // generate random password
        for (int i = 0; i < passwordLength; i++) {
            generatedPassword.append(randomChars.charAt(random.nextInt(randomChars.length())));
        }

        return generatedPassword.toString();

    } // generatePassword end


} // class end
