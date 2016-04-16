package com.antypaymentguard.databaseHelper;

import android.content.Context;

/**
 * @author Kamil Walkowiak
 */
public class TransactionDatabaseHelper {
    private DatabaseHelper databaseHelper;

    static final String TABLE_NAME = "transactions";
    static final String COLUMN_ID = "_id";

    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PAY_CARD_ID = "payCard_id";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_DATE + " DATE NOT NULL, " +
            COLUMN_AMOUNT + " DOUBLE NOT NULL, " +
            COLUMN_PLACE + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_PAY_CARD_ID + " INTEGER NOT NULL, " + ")";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public TransactionDatabaseHelper(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }
}
