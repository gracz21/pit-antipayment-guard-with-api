package com.antypaymentguard.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.antypaymentguard.model.condition.AmountCondition;
import com.antypaymentguard.model.condition.NumberCondition;

/**
 * @author Kamil Walkowiak
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "antiPaymentGuard.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TransactionDatabaseHelper.TABLE_NAME + "(" +
            TransactionDatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            TransactionDatabaseHelper.COLUMN_DATE + " DATE NOT NULL, " +
            TransactionDatabaseHelper.COLUMN_AMOUNT + " DOUBLE NOT NULL, " +
            TransactionDatabaseHelper.COLUMN_PLACE + " TEXT NOT NULL, " +
            TransactionDatabaseHelper.COLUMN_DESCRIPTION + " TEXT, " +
            TransactionDatabaseHelper.COLUMN_PAY_CARD_ID + " INTEGER NOT NULL, " + ")";
    private static final String CREATE_TABLE_CONDITIONS = "CREATE TABLE " + ConditionDatabaseHelper.TABLE_NAME + "(" +
            ConditionDatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            ConditionDatabaseHelper.COLUMN_TRANSACTIONS_AMOUNT + " DOUBLE, " +
            ConditionDatabaseHelper.COLUMN_TRANSACTIONS_NUMBER + " INTEGER, " +
            ConditionDatabaseHelper.COLUMN_TYPE + " TEXT NOT NULL " +
            "CHECK (" + ConditionDatabaseHelper.COLUMN_TYPE +
            " IN ('" + NumberCondition.class.getSimpleName() + "', '" + AmountCondition.class.getSimpleName() + "'))," +
            "CHECK (" + ConditionDatabaseHelper.COLUMN_TRANSACTIONS_AMOUNT + " IS NOT NULL OR " +
            ConditionDatabaseHelper.COLUMN_TRANSACTIONS_NUMBER + " IS NOT NULL))";

    private static final String DROP_TABLE_TRANSACTIONS = "DROP TABLE IF EXISTS " + TransactionDatabaseHelper.TABLE_NAME;
    private static final String DROP_TABLE_CONDITIONS = "DROP TABLE IF EXISTS " + ConditionDatabaseHelper.TABLE_NAME;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_CONDITIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DROP_TABLE_TRANSACTIONS);
            db.execSQL(DROP_TABLE_CONDITIONS);
        }
        onCreate(db);
    }
}
