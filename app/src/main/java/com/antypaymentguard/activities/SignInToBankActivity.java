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

import com.antypaymentguard.R;
import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class SignInToBankActivity extends AppCompatActivity {
    TextInputEditText loginText;
    TextInputEditText passwordText;
    Button signInButton;
    Spinner selectBankSpinner;
    boolean isAlreadyLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isAlreadyLoggedIn = false;

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
                String bankName = selectBankSpinner.getSelectedItem().toString();

                if(!isAlreadyLoggedIn) {
                    Toast.makeText(getApplicationContext(), R.string.sign_in_succes, Toast.LENGTH_SHORT).show();
                    (new addBankToDatabaseTask()).execute(selectBankSpinner.getSelectedItem().toString());
                }

                Intent intent = new Intent(SignInToBankActivity.this, AddBankAccountActivity.class);
                intent.putExtra("bankName", bankName);
                startActivity(intent);
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
            return Select.from(Bank.class).where(Condition.prop("name ").eq(bankName[0])).first() != null;
        }

        @Override
        protected void onPostExecute(Boolean bankExist) {
            loginText.setFocusable(!bankExist);
            loginText.setFocusableInTouchMode(!bankExist);
            passwordText.setFocusable(!bankExist);
            passwordText.setFocusableInTouchMode(!bankExist);

            if(bankExist) {
                signInButton.setText(getString(R.string.next));
                Toast.makeText(getApplicationContext(), R.string.logged, Toast.LENGTH_SHORT).show();
            } else {
                signInButton.setText(getString(R.string.sing_in));
            }

            isAlreadyLoggedIn = bankExist;
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
