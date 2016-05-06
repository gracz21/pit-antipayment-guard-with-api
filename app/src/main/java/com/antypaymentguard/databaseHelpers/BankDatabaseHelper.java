package com.antypaymentguard.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.antypaymentguard.models.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Walkowiak
 */
public class BankDatabaseHelper {
    private DatabaseHelper databaseHelper;

    static final String TABLE_NAME = "banks";
    static final String COLUMN_ID = "_id";

    static final String COLUMN_NAME = "name";
    static final String COLUMN_SESSION_ID = "session_id";
    static final String COLUMN_SESSION_ID_SIGNATURE = "session_id_signature";

    static final String CREATE_TABLE_BANKS = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_SESSION_ID + " TEXT NOT NULL, " +
            COLUMN_SESSION_ID_SIGNATURE + " TEXT NOT NULL)";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public BankDatabaseHelper(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public void createBank(Bank bank) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, bank.getName());
        values.put(COLUMN_SESSION_ID, bank.getSessionId());
        values.put(COLUMN_SESSION_ID_SIGNATURE, bank.getSessionIdSignature());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Bank> getAllBanks() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<Bank> banks = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String sessionId = cursor.getString(2);
                String sessionIdSignature = cursor.getString(3);

                Bank bank = new Bank(name, sessionId, sessionIdSignature);
                bank.setId(cursor.getLong(0));
                banks.add(bank);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return banks;
    }

}
