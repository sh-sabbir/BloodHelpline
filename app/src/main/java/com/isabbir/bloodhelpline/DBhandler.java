package com.isabbir.bloodhelpline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "bloodBank";

    // Contacts table name
    public static final String TABLE_SHOPS = "donors";

    // Shops Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_SH_ADDR = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BG = "blood_group";
    private static final String KEY_DONATED = "last_donated";

    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Sqlite database
     */
    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHOPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_AGE + " TEXT,"
                + KEY_SH_ADDR + " TEXT," + KEY_PHONE + " TEXT," + KEY_BG + " TEXT," + KEY_DONATED + " Text" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName()); // Donor Name
        values.put(KEY_AGE, shop.getAge()); // Donor Age
        values.put(KEY_SH_ADDR, shop.getAddress()); // Donor Address
        values.put(KEY_PHONE, shop.getPhone()); // Donor Phone Number
        values.put(KEY_BG, shop.getBloodGroup()); // Donor Blood Group
        values.put(KEY_DONATED, shop.getLastDonate()); // Donor Last Donation date

        // Inserting Row
        db.insert(TABLE_SHOPS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one shop
    public Shop getShop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOPS, new String[]{KEY_ID,
                        KEY_NAME, KEY_AGE, KEY_SH_ADDR, KEY_PHONE, KEY_BG, KEY_DONATED}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Shop contact = new Shop(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return shop
        return contact;
    }

    // Getting All Shops
    public List<Shop> getAllShops() {
        List<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHOPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setName(cursor.getString(1));
                shop.setAge(cursor.getString(2));
                shop.setAddress(cursor.getString(3));
                shop.setPhone(cursor.getString(4));
                shop.setBloodGroup(cursor.getString(5));
                shop.setLastDonate(cursor.getString(6));
                // Adding contact to list
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

        // return contact list
        return shopList;
    }

    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_SH_ADDR, shop.getAddress());
        values.put(KEY_AGE, shop.getAge());
        values.put(KEY_PHONE, shop.getPhone());
        values.put(KEY_BG, shop.getBloodGroup());
        values.put(KEY_DONATED, shop.getLastDonate());

        // updating row
        return db.update(TABLE_SHOPS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(shop.getId())});
    }

    // Deleting a shop
    public void deleteShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPS, KEY_ID + " = ?",
                new String[] { String.valueOf(shop.getId()) });
        db.close();
    }

    public Cursor getDetails(String bGroup)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select rowid _id,* from " + TABLE_SHOPS + " WHERE blood_group='" + bGroup + "'", null);
    }

    public Cursor getDonorInfo(String dID)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_SHOPS + " WHERE _id='" + dID + "'", null);
    }

    public int getAllDonor() {
        String countQuery = "SELECT  * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int getAllDonorByGroup(String group) {
        String countQuery = "SELECT  * FROM " + TABLE_SHOPS + " WHERE blood_group='" + group + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    /**
     * open database
     */
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close database
     */
    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * insert contentvaluse to table
     *
     * @param values value of data want insert
     * @return index row insert
     */
    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    /**
     * delete id row of table
     */
    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }

    /**
     * insert note to table
     *
     * @param note note to insert
     * @return id of note
     */
    public long insertNote(Note note) {
        return insert(TABLE_SHOPS, noteToValues(note));
    }
    /**
     * delete id row of table
     */
    public boolean deleteDonors(String where) {
        return delete(TABLE_SHOPS, where);
    }

    /**
     * convert note to contentvalues
     * don't put id of note because
     * when insert id will auto create
     * when update we don't update id
     */
    private ContentValues noteToValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, note.getName());
        values.put(KEY_AGE, note.getAge());
        values.put(KEY_SH_ADDR, note.getAddress());
        values.put(KEY_PHONE, note.getPhone());
        values.put(KEY_BG, note.getBgroup());
        values.put(KEY_DONATED, note.getLastDonated());
        return values;
    }

    /**
     * convert cursor to note
     */
    public Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)))
                .setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)))
                .setAge(cursor.getString(cursor.getColumnIndex(KEY_AGE)))
                .setAddress(cursor.getString(cursor.getColumnIndex(KEY_SH_ADDR)))
                .setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)))
                .setBgroup(cursor.getString(cursor.getColumnIndex(KEY_BG)))
                .setLastDonated(cursor.getString(cursor.getColumnIndex(KEY_DONATED)));
        return note;
    }

}