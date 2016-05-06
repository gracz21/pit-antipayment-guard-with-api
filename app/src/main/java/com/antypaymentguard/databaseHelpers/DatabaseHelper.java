package com.antypaymentguard.databaseHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Kamil Walkowiak
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "antiPaymentGuard.db";
    private static final int DATABASE_VERSION = 1;

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
        db.execSQL(BankDatabaseHelper.CREATE_TABLE_BANKS);
        db.execSQL(BankAccountDatabaseHelper.CREATE_TABLE);
        db.execSQL(TransactionDatabaseHelper.CREATE_TABLE);
        db.execSQL(ConditionDatabaseHelper.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(BankDatabaseHelper.DROP_TABLE);
            db.execSQL(BankAccountDatabaseHelper.DROP_TABLE);
            db.execSQL(TransactionDatabaseHelper.DROP_TABLE);
            db.execSQL(ConditionDatabaseHelper.DROP_TABLE);
        }
        onCreate(db);
    }
}
