package com.antypaymentguard.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.antypaymentguard.R;
import com.antypaymentguard.models.Bank;

public class SignInToBankActivity extends AppCompatActivity {
    TextInputEditText loginText;
    TextInputEditText passwordText;
    Button signInButton;
    Spinner selectBankSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_to_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginText = (TextInputEditText) findViewById(R.id.loginText);
        passwordText = (TextInputEditText) findViewById(R.id.passwordText);
        signInButton = (Button) findViewById(R.id.signInButton);
        selectBankSpinner = (Spinner) findViewById(R.id.selectBankSpinner);
        assert signInButton != null;
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Signed in successfully", Toast.LENGTH_SHORT).show();
                (new addBankToDatabaseTask()).execute(selectBankSpinner.getSelectedItem().toString());
                startActivity(new Intent(SignInToBankActivity.this, AddBankAccountActivity.class));
            }
        });


        assert selectBankSpinner != null;
        selectBankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), selectBankSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                (new checkIfIsLoggedInToBankTask()).execute(selectBankSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        (new checkIfIsLoggedInToBankTask()).execute(selectBankSpinner.getSelectedItem().toString());
    }

    private class checkIfIsLoggedInToBankTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... bankName) {
            return new Select().from(Bank.class).where("Name = ?", bankName[0]).executeSingle() != null;
        }

        @Override
        protected void onPostExecute(Boolean bankExist) {
            loginText.setFocusable(!bankExist);
            loginText.setFocusableInTouchMode(!bankExist);
            passwordText.setFocusable(!bankExist);
            passwordText.setFocusableInTouchMode(!bankExist);

            if(bankExist) {
                signInButton.setText(getString(R.string.next));
                //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            } else {
                signInButton.setText(getString(R.string.sing_in));
                //Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class addBankToDatabaseTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... bankName) {
            (new Bank(bankName[0], "Placeholder", "Placeholder")).save();
            return null;
        }
    }
}
