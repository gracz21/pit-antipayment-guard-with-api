package com.antypaymentguard.databaseHelper;

import android.content.Context;

/**
 * @author Kamil Walkowiak
 */
public class BankAccountDatabaseHelper {
    private DatabaseHelper databaseHelper;

    static final String TABLE_NAME = "bank_accounts";
    static final String COLUMN_ID = "_id";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IBAN = "iban";
    private static final String COLUMN_CURRENCY_NAME = "currency_name";
    private static final String COLUMN_BALANCE = "balance";
    private static final String COLUMN_OWNER = "owner";
    private static final String COLUMN_BANK_ID = "bank_id";
    private static final String COLUMN_CONDITION_ID = "condition_id";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
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
}
