package com.antypaymentguard.api;

import android.support.annotation.Nullable;

import com.antypaymentguard.GuardApplication;
import com.antypaymentguard.api.models.BankAccountResponse;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.utils.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Maciej Kozłowski on 09.05.16.
 */
public class Loader {
    @Nullable
    public static List<BankAccount> getAccounts() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(GuardApplication.getInstance().getAssets().open("data_accounts.json")));
            final BankAccountResponse bankAccountResponse =
                    new JsonSerializer<BankAccountResponse>().deserialize(reader, BankAccountResponse.class);

            return bankAccountResponse.getAccounts();
        } catch (IOException e) {
            e.printStackTrace();
            // and simply ignore, just university project
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    // again
                }
            }
        }
        return null;
    }
}
