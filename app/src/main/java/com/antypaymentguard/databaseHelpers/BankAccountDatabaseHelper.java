package com.antypaymentguard.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.antypaymentguard.models.Bank;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.AmountCondition;
import com.antypaymentguard.models.conditions.Condition;
import com.antypaymentguard.models.conditions.NumberCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountDatabaseHelper {
    private DatabaseHelper databaseHelper;

    static final String TABLE_NAME = "bank_accounts";
    static final String COLUMN_ID = "_id";

    static final String COLUMN_NAME = "name";
    static final String COLUMN_IBAN = "iban";
    static final String COLUMN_CURRENCY_NAME = "currency_name";
    static final String COLUMN_BALANCE = "balance";
    static final String COLUMN_OWNER = "owner";

    static final String COLUMN_BANK_ID = "bank_id";
    static final String COLUMN_CONDITION_ID = "condition_id";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_IBAN + " TEXT NOT NULL, " +
            COLUMN_CURRENCY_NAME + " TEXT NOT NULL, " +
            COLUMN_BALANCE + " DOUBLE NOT NULL, " +
            COLUMN_OWNER + " TEXT, " +
            COLUMN_BANK_ID + " INTEGER NOT NULL, " +
            COLUMN_CONDITION_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_BANK_ID + ") REFERENCES " +
            BankDatabaseHelper.TABLE_NAME + "(" + BankDatabaseHelper.COLUMN_ID + ")," +
            "FOREIGN KEY(" + COLUMN_CONDITION_ID + ") REFERENCES " +
            ConditionDatabaseHelper.TABLE_NAME + "(" + ConditionDatabaseHelper.COLUMN_ID + ")" + ")";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public BankAccountDatabaseHelper(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    public void createBankAccount(BankAccount bankAccount) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, bankAccount.getName());
        values.put(COLUMN_IBAN, bankAccount.getIban());
        values.put(COLUMN_CURRENCY_NAME, bankAccount.getCurrencyName());
        values.put(COLUMN_BALANCE, bankAccount.getBalance());
        values.put(COLUMN_OWNER, bankAccount.getOwner());
        values.put(COLUMN_BANK_ID, bankAccount.getBank().getId());
        values.put(COLUMN_CONDITION_ID, bankAccount.getCondition().getId());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<BankAccount> getBankAccountsByBank(Bank bank) {
        List<BankAccount> bankAccounts = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selectQuery = String.format("SELECT * FROM %s a JOIN %s b ON a.%s = b.%s WHERE %s = %s",
                TABLE_NAME, ConditionDatabaseHelper.TABLE_NAME, COLUMN_CONDITION_ID, ConditionDatabaseHelper.COLUMN_ID,
                COLUMN_BANK_ID, bank.getId());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Condition condition = fetchConditionFromCursor(cursor);
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String iban = cursor.getString(cursor.getColumnIndex(COLUMN_IBAN));
                String currency = cursor.getString(cursor.getColumnIndex(COLUMN_CURRENCY_NAME));
                double balance = cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE));
                String owner = cursor.getString(cursor.getColumnIndex(COLUMN_OWNER));

                bankAccounts.add(new BankAccount(name, iban, currency, balance, owner, bank, condition));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bankAccounts;
    }

    private Condition fetchConditionFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(COLUMN_CONDITION_ID));


        Condition condition;
        if(cursor.isNull(cursor.getColumnIndex(ConditionDatabaseHelper.COLUMN_TRANSACTIONS_NUMBER))) {
            double transactionsAmount = cursor.getDouble(cursor.getColumnIndex(ConditionDatabaseHelper.COLUMN_TRANSACTIONS_AMOUNT));
            condition = new AmountCondition(id, transactionsAmount);
        } else {
            int transactionsNumber = cursor.getInt(cursor.getColumnIndex(ConditionDatabaseHelper.COLUMN_TRANSACTIONS_NUMBER));
            condition = new NumberCondition(id, transactionsNumber);
        }

        return condition;
    }
}
