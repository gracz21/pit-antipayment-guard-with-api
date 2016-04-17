package com.antypaymentguard.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.antypaymentguard.model.condition.AmountCondition;
import com.antypaymentguard.model.condition.Condition;
import com.antypaymentguard.model.condition.NumberCondition;

/**
 * @author Kamil Walkowiak
 */
public class ConditionDatabaseHelper {
    private DatabaseHelper databaseHelper;

    static final String TABLE_NAME = "conditions";
    static final String COLUMN_ID = "_id";

    static final String COLUMN_TRANSACTIONS_AMOUNT = "transactions_amount";
    static final String COLUMN_TRANSACTIONS_NUMBER = "transactions_number";
    static final String COLUMN_TYPE = "type";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_TRANSACTIONS_AMOUNT + " DOUBLE, " +
            COLUMN_TRANSACTIONS_NUMBER + " INTEGER, " +
            COLUMN_TYPE + " TEXT NOT NULL " +
            "CHECK (" + COLUMN_TYPE +
            " IN ('" + NumberCondition.class.getSimpleName() + "', '" + AmountCondition.class.getSimpleName() + "'))," +
            "CHECK (" + COLUMN_TRANSACTIONS_AMOUNT + " IS NOT NULL OR " + COLUMN_TRANSACTIONS_NUMBER + " IS NOT NULL))";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ConditionDatabaseHelper(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context.getApplicationContext());
    }

    public AmountCondition getOrCreateAmountCondition(double transactionsAmount) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTIONS_AMOUNT + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{Double.toString(transactionsAmount)});
        if(cursor.moveToFirst()) {
            AmountCondition amountCondition = new AmountCondition(cursor.getInt(0), transactionsAmount);
            cursor.close();
            db.close();
            return amountCondition;
        } else {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTIONS_AMOUNT, transactionsAmount);
            values.put(COLUMN_TYPE, AmountCondition.class.getSimpleName());
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return new AmountCondition(id, transactionsAmount);
        }
    }

    public NumberCondition getOrCreateNumberCondition(int transactionsNumber) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTIONS_NUMBER + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{Integer.toString(transactionsNumber)});
        if(cursor.moveToFirst()) {
            NumberCondition numberCondition = new NumberCondition(cursor.getInt(0), transactionsNumber);
            cursor.close();
            db.close();
            return numberCondition;
        } else {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTIONS_NUMBER, transactionsNumber);
            values.put(COLUMN_TYPE, NumberCondition.class.getSimpleName());
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return new NumberCondition(id, transactionsNumber);
        }
    }

    public Condition getConditionById(int id) {
        return null;
    }
}
