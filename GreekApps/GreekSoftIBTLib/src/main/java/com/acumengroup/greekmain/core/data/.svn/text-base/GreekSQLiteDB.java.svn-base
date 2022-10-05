package com.acumengroup.greekmain.core.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.util.logger.GreekLog;

import java.util.ArrayList;

/**
 * Created by Arcadia
 */
public class GreekSQLiteDB {

    private String DATABASE_NAME = "GREEK_DB";
    private int DATABASE_VERSION = 1;
    private String TABLE_NAME = "table1";

    private SQLiteDatabase db;

    private String KEY_ROWID = "id";

    private String TABLE_CREATE = "create table if not exists " + TABLE_NAME + " (" + KEY_ROWID + " integer primary key autoincrement); ";

    private MSFSQLiteHelper openHelper;

    private Cursor cursor;
    private Context context;

    // private String[] columnName;

    public GreekSQLiteDB(Context context) {
        this.context = context;
        openHelper = new MSFSQLiteHelper(context);
        db = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.OPEN_READWRITE, null);
    }

    /**
     * Open DB
     *
     * @return
     * @throws SQLException
     */
    public GreekSQLiteDB open() throws SQLException {
        db = openHelper.getWritableDatabase();
        if (cursor != null) ((AppCompatActivity) context).startManagingCursor(cursor);
        return this;
    }

    /**
     * Close DB.
     */
    public void close() {
        if (db == null) {
            open();
        }
        if (cursor != null) ((AppCompatActivity) context).startManagingCursor(cursor);
        db.close();
    }

    /**
     * @return - Table Size
     * <p/>
     * If table not exists return value is -1.
     */
    public int getTableSize(String tableName) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return -1;
        return (int) DatabaseUtils.longForQuery(db, "select count(*) from " + tableName, null);
    }

    /**
     * Delete the table values.
     * <p/>
     * No acknowledgement will be returned. If table exists it will delete else
     * nothing will happen.
     */
    public void deleteTable(String tableName) {
        if (!db.isOpen()) {
            open();
        }

        if (!isTableExists(tableName)) return;

        db.delete(tableName, null, null);
        db.execSQL("delete from sqlite_sequence where name='" + tableName + "';");
        db.close();
    }

    /**
     * Drop the whole table values and structure.
     *
     * @param tableName
     */
    public void dropTable(String tableName) {
        if (!db.isOpen()) {
            open();
        }

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        close();
    }

    /**
     * Check Whether the table is exists or not
     *
     * @param tableName
     * @return
     */
    public boolean isTableExists(String tableName) {

        boolean isExists = false;

        if (!db.isOpen()) {
            open();
        }
        isExists = DatabaseUtils.longForQuery(db, "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "';", null) > 0;

        close();
        return isExists;
    }

    /**
     * Create Table. Before that Set the table name using - setTableName(String
     * tableName) & column name using - setColumnName(String[] columnName)
     */
    public void createTable(String tableName, String[] columnName) {

        createTable(tableName, columnName, null);
    }

    public void createTable(String tableName, String[] columnName, String columnType[]) {

        this.TABLE_NAME = tableName;

        this.TABLE_CREATE = "create table if not exists " + tableName + " (" + KEY_ROWID + " integer primary key autoincrement";

        // Adding columns before creating table
        // Note: All the column type is text.
        if (columnName != null) {
            for (int i = 0; i < columnName.length; i++) {
                if (columnType == null) {
                    TABLE_CREATE += ", " + columnName[i] + " text not null";
                } else {
                    TABLE_CREATE += ", " + columnName[i] + " " + columnType[i];
                }
            }
        }

        TABLE_CREATE += "); ";

        // openHelper = new MSFSQLiteHelper(context);

        if (!db.isOpen()) {
            open();
        }

        db.execSQL(TABLE_CREATE);

        close();
    }

    public void createTable(String createTblCmd) {

        this.TABLE_CREATE = createTblCmd;

        if (!db.isOpen()) {
            open();
        }
        db.execSQL(TABLE_CREATE);
        close();
    }

    public void executeQuery(String query) {
        if (!db.isOpen()) {
            open();
        }
        db.execSQL(query);
        close();
    }

    /**
     * @param tableName   - Set table name
     * @param columenName - set column name to which values to be inserted
     * @param value       - set value for that columns
     * @return
     */
    public void insertRow(String tableName, String columnName[], String value[]) {
        if ((columnName == null) || (value == null)) {
            GreekLog.error("ColumnName or value is null");
            return;
        }
        if (columnName.length != value.length) {
            GreekLog.error("ColumnName length & value length should be same");
            return;
        }

        if (!db.isOpen()) {
            open();
        }

        ContentValues initialValues = new ContentValues();
        for (int i = 0; i < columnName.length; i++) {
            initialValues.put(columnName[i], value[i]);
        }
        db.insert(tableName, null, initialValues);
    }

    public void insertRows(String tableName, String columnName[], String value[][]) {
        if ((columnName == null) || (value == null)) {
            GreekLog.error("ColumnName or value is null");
            return;
        }

        if (!db.isOpen()) {
            open();
        }

        InsertHelper insertHelper = new InsertHelper(db, tableName);

        for (int i = 0; i < value.length; i++) {
            insertHelper.prepareForInsert();
            for (int j = 0; j < value[i].length; j++) {
                insertHelper.bind(insertHelper.getColumnIndex(columnName[j]), value[i][j]);
            }
            insertHelper.execute();
        }
        close();
    }

    public void insertRows(String tableName, String columnName[], ArrayList<String[]> value) {
        if ((columnName == null) || (value == null)) {
            GreekLog.error("ColumnName or value is null");
            return;
        }

        if (!db.isOpen()) {
            open();
        }

        InsertHelper insertHelper = new InsertHelper(db, tableName);

        for (int i = 0; i < value.size(); i++) {
            insertHelper.prepareForInsert();
            for (int j = 0; j < value.get(i).length; j++) {
                insertHelper.bind(insertHelper.getColumnIndex(columnName[j]), value.get(i)[j]);
            }
            insertHelper.execute();
        }
        close();
    }

    /**
     * @param tableName - Set table name
     * @param rowId     - Set row id to delete a particular row
     * @return false if table or row not exists
     */
    public boolean deleteRow(String tableName, int rowId) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return false;
        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + KEY_ROWID + "= " + rowId + ";", null);
        return count != 0 && db.delete(tableName, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteRow(String tableName, String selectedColumnName, String selectedValue) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return false;
        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + selectedColumnName + "= '" + selectedValue + "';", null);
        if (count == 0) return false;
        boolean returnValue;
        returnValue = db.delete(tableName, selectedColumnName + "='" + selectedValue + "'", null) > 0;
        db.close();
        return returnValue;
    }

    public boolean deleteRow(String tableName, String selectedColumnName, String selectedValue, String operator) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return false;
        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + selectedColumnName + operator + " '" + selectedValue + "';", null);
        if (count == 0) return false;
        boolean returnValue;
        returnValue = db.delete(tableName, selectedColumnName + operator + " '" + selectedValue + "'", null) > 0;
        db.close();
        return returnValue;
    }

    public boolean deleteRow(String tableName, String selectedColumnName, String selectedValue[], String operator) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return false;

        String condition = "";

        for (int i = 0; i < selectedValue.length; i++) {
            if (i > 0) condition += " " + operator + " ";
            condition += selectedColumnName + "= '" + selectedValue[i] + "'";
        }

        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + condition + ";", null);
        if (count == 0) return false;
        boolean returnValue;
        returnValue = db.delete(tableName, condition, null) > 0;
        db.close();
        return returnValue;
    }

    public boolean deleteRow(String tableName, String selectedColumnName, String selectedValue[]) {
        if (!db.isOpen()) {
            open();
        }
        if (!isTableExists(tableName)) return false;

        String condition = "";

        for (int i = 0; i < selectedValue.length; i++) {
            if (i > 0) condition += " or ";
            condition += selectedColumnName + "= '" + selectedValue[i] + "'";
        }

        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + condition + ";", null);
        if (count == 0) return false;
        boolean returnValue;
        returnValue = db.delete(tableName, condition, null) > 0;
        db.close();
        return returnValue;
    }

    /**
     * @param tableName   - Set table name
     * @param columnNames - Set column names to retrieve
     * @returns all the specified columns value in Cursor.
     */
    public Cursor getAllRows(String tableName, String columnNames[]) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, columnNames, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        if (!db.isOpen()) {
            open();
        }
        return db;
    }

    public Cursor rawQuery(String query) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * @param tableName - Set table name
     * @returns all the specified columns value in Cursor.
     */
    public Cursor getAllRows(String tableName) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getAllRow(String tableName, String columnNames[], String selectionColumn, String selectionValue) {
        if (!db.isOpen()) {
            open();
        }
        try {
            cursor = db.query(tableName, columnNames, selectionColumn + "='" + selectionValue + "';", null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getAllRow(String tableName, String selectionColumn, String selectionValue) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, selectionColumn + "='" + selectionValue + "';", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getAllRow(boolean distinct, String tableName, String columnNames[], String selectionColumn, String selectionValue) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(distinct, tableName, columnNames, selectionColumn + "='" + selectionValue + "';", null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getAllRow(String tableName, String columnNames[], String selectionCondition) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, columnNames, selectionCondition, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllRowWithOrderBy(String tableName, String columnNames[], String selectionCondition, String orderByColumnName) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, columnNames, selectionCondition, null, null, null, orderByColumnName);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllRowWithOrderBy(boolean distinct, String tableName, String columnNames[], String selectionCondition, String orderByColumnName) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(distinct, tableName, columnNames, selectionCondition, null, null, null, orderByColumnName, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllRow(boolean distinct, String tableName, String columns[]) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(distinct, tableName, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    /**
     * @param tableName   - Set table name
     * @param columnNames - Set column names to retrieve
     * @param rowId       - id to retrieve a particular row
     * @return - the specified row values
     */
    public Cursor getRow(String tableName, String columnNames[], int rowId) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, columnNames, KEY_ROWID + "=" + rowId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getRow(String tableName, String selectionColumn, String selectionValue) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, selectionColumn + "='" + selectionValue + "';", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        // db.close();
        return cursor;
    }

    /**
     * @param tableName - Set table name
     * @param rowId     - id to retrieve a particular row
     * @return - the specified row values
     */
    public Cursor getRow(String tableName, int rowId) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, KEY_ROWID + "=" + rowId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * @param tableName     - Set table name
     * @param columnNames   - Set column names to retrieve
     * @param columnName    - Column name to set like command
     * @param likeCharacter - like character
     * @return row(s) with the character starts like
     */
    public Cursor getNameLike(String tableName, String columnNames[], String columnName, String likeCharacter) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, columnNames, columnName + " like '" + likeCharacter + "%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * @param tableName     - Set table name
     * @param columnName    - Column name to set like command
     * @param likeCharacter - like character
     * @return row(s) with the character starts like
     */
    public Cursor getNameLike(String tableName, String columnName, String likeCharacter) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, columnName + " like '" + likeCharacter + "%';", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * @param tableName     - Set table name
     * @param columnName    - Column name to set like command
     * @param likeCharacter - like character
     * @return row(s) with the character starts like
     */
    public Cursor getNameContains(String tableName, String columnName, String containsCharacter) {
        if (!db.isOpen()) {
            open();
        }
        cursor = db.query(tableName, null, columnName + " like '%" + containsCharacter + "%';", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public boolean isValueExists(String tableName, String columnName, String value) {
        if (!db.isOpen()) {
            open();
        }
        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + columnName + "= '" + value + "';", null);
        boolean isExists = false;
        isExists = count > 0;
        db.close();
        return isExists;
    }

    public long getRowCount(String tableName, String columnName, String value) {
        if (!db.isOpen()) {
            open();
        }
        long count = DatabaseUtils.longForQuery(db, "select count(*) from " + tableName + " where " + columnName + "= '" + value + "';", null);
        db.close();
        return count;
    }

    public boolean updateRow(String tableName, String columnName, String value, int rowId) {
        if (!db.isOpen()) {
            open();
        }
        ContentValues args = new ContentValues();
        args.put(columnName, value);
        return db.update(tableName, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void updateColumn(String tableName, String fromColumnName, String toColumnName) {
        if (!db.isOpen()) {
            open();
        }
        db.execSQL("update " + tableName + " set " + toColumnName + " = " + fromColumnName);
    }

    public boolean customUpdateRow(String tableName, String columnName, String value, String whereColumnName, String whereValue) {
        if (!db.isOpen()) {
            open();
        }
        ContentValues args = new ContentValues();
        args.put(columnName, value);
        boolean returnValue = db.update(tableName, args, whereColumnName + " = '" + whereValue + "';", null) > 0;
        db.close();
        return returnValue;
    }

    public boolean customUpdateRow(String tableName, String columnName, String value, String whereCondition) {
        if (!db.isOpen()) {
            open();
        }
        ContentValues args = new ContentValues();
        args.put(columnName, value);
        boolean returnValue = db.update(tableName, args, whereCondition, null) > 0;
        db.close();
        return returnValue;
    }

    public boolean customUpdateRow(String tableName, String columnNames[], String values[], String whereCondition) {

        if (columnNames.length != values.length) {
            GreekLog.error("ColumnName length & value length should be same");
            return false;
        }

        if (!db.isOpen()) {
            open();
        }
        ContentValues args = new ContentValues();
        for (int i = 0; i < columnNames.length; i++) {
            args.put(columnNames[i], values[i]);
        }
        boolean returnValue = db.update(tableName, args, whereCondition, null) > 0;
        db.close();
        return returnValue;
    }

    private class MSFSQLiteHelper extends SQLiteOpenHelper {

        MSFSQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
         * .sqlite.SQLiteDatabase)
         *
         * Create a new table
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // db.execSQL(TABLE_CREATE);
        }

        /*
         * @see
         * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
         * .sqlite.SQLiteDatabase, int, int)
         *
         * Upgrading Database, this will drop tables and recreate.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
