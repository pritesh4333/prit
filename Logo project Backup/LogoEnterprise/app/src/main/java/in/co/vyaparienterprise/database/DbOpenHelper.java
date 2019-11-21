package in.co.vyaparienterprise.database;

/**
 * Created by bekirdursun on 6.02.2018.
 */

import android.content.Context;

import in.co.vyaparienterprise.database.sqliteasset.SQLiteAssetHelper;

public class DbOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 3;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade(DATABASE_VERSION);
    }
}