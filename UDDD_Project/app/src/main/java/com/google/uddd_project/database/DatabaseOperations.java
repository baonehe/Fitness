package com.google.uddd_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.uddd_project.R;
import com.google.uddd_project.adapters.WorkoutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {


    public Context mContext;
    public int[] arr1;
    public int[] arr2 = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public DatabaseHelper dbHelper;
    public SQLiteDatabase sqLite;

    public DatabaseOperations(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.mContext = context;
    }

    public int CheckDBEmpty() {
        this.sqLite = this.dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM ");
        sb.append(DatabaseHelper.table);
        Cursor rawQuery = this.sqLite.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        return rawQuery.getInt(0) > 0 ? 1 : 0;
    }

    public int deleteTable() {
        this.sqLite = this.dbHelper.getWritableDatabase();
        int delete = this.sqLite.delete(DatabaseHelper.table, null, null);
        this.sqLite.close();
        return delete;
    }

    public List<WorkoutData> getAllDaysProgress() {
        ArrayList arrayList = new ArrayList();
        this.sqLite = this.dbHelper.getReadableDatabase();
        try {
            if (this.sqLite != null) {
                SQLiteDatabase sQLiteDatabase = this.sqLite;
                StringBuilder sb = new StringBuilder();
                sb.append("select * from ");
                sb.append(DatabaseHelper.table);
                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
                if (rawQuery.moveToFirst()) {
                    while (!rawQuery.isAfterLast()) {
                        WorkoutData workoutData = new WorkoutData();
                        workoutData.setDay(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.day)));
                        workoutData.setProgress(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.progress)));
                        rawQuery.moveToNext();
                        arrayList.add(workoutData);
                    }
                }
                rawQuery.close();
                this.sqLite.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public int getDayExcCounter(String str) {
        this.sqLite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqLite;
        int i = 0;
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.table);
            sb.append(" where ");
            sb.append(DatabaseHelper.day);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.counter));
            }
            rawQuery.close();
            this.sqLite.close();
        }
        return i;
    }

    public String getDayExcCycles(String str) {
        this.sqLite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqLite;
        String str2 = "";
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.table);
            sb.append(" where ");
            sb.append(DatabaseHelper.day);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                str2 = rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.cycles));
            }
            this.sqLite.close();
        }
        return str2;
    }

    public float getExcDayProgress(String str) {
        this.sqLite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqLite;
        float f = 0.0f;
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.table);
            sb.append(" where ");
            sb.append(DatabaseHelper.day);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                f = rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.progress));
            }
            rawQuery.close();
            this.sqLite.close();
        }
        return f;
    }

    public long insertExcALLDayData() {
        long j = 0;
        try {
            this.sqLite = this.dbHelper.getWritableDatabase();
            for (int i = 1; i <= 30; i++) {
                JSONObject jSONObject = new JSONObject();
                this.arr1 = this.mContext.getResources().getIntArray(this.arr2[i - 1]);
                int i2 = 0;
                for (int put : this.arr1) {
                    try {
                        jSONObject.put(String.valueOf(i2), put);
                        i2++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("json str databs: ");
                sb.append(jSONObject.toString());
                Log.e("TAG", sb.toString());
                ContentValues contentValues = new ContentValues();
                String str = DatabaseHelper.day;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Day ");
                sb2.append(i);
                contentValues.put(str, sb2.toString());
                contentValues.put(DatabaseHelper.progress, Double.valueOf(0.0d));
                contentValues.put(DatabaseHelper.counter, Integer.valueOf(0));
                contentValues.put(DatabaseHelper.cycles, jSONObject.toString());
                if (this.sqLite != null) {
                    try {
                        j = this.sqLite.insert(DatabaseHelper.table, null, contentValues);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            this.sqLite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqLite.close();
        }
        return j;
    }

    public int insertExcCounter(String str, int i) {
        String str2 = "'";
        int i2 = 0;
        try {
            this.sqLite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.counter, Integer.valueOf(i));
            if (this.sqLite != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE ");
                    sb.append(DatabaseHelper.table);
                    sb.append(" SET ");
                    sb.append(DatabaseHelper.counter);
                    sb.append(" = ");
                    sb.append(i);
                    sb.append(" WHERE ");
                    sb.append(DatabaseHelper.day);
                    sb.append(" = '");
                    sb.append(str);
                    sb.append(str2);
                    sb.toString();
                    SQLiteDatabase sQLiteDatabase = this.sqLite;
                    String str3 = DatabaseHelper.table;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DatabaseHelper.day);
                    sb2.append("='");
                    sb2.append(str);
                    sb2.append(str2);
                    i2 = sQLiteDatabase.update(str3, contentValues, sb2.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqLite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLite.close();
        }
        return i2;
    }

    public int insertExcCycles(String str, String str2) {
        String str3 = "'";
        int i = 0;
        try {
            this.sqLite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.cycles, str2);
            if (this.sqLite != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE ");
                    sb.append(DatabaseHelper.table);
                    sb.append(" SET ");
                    sb.append(DatabaseHelper.cycles);
                    sb.append(" = ");
                    sb.append(str2);
                    sb.append(" WHERE ");
                    sb.append(DatabaseHelper.day);
                    sb.append(" = '");
                    sb.append(str);
                    sb.append(str3);
                    sb.toString();
                    SQLiteDatabase sQLiteDatabase = this.sqLite;
                    String str4 = DatabaseHelper.table;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DatabaseHelper.day);
                    sb2.append("='");
                    sb2.append(str);
                    sb2.append(str3);
                    i = sQLiteDatabase.update(str4, contentValues, sb2.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqLite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLite.close();
        }
        return i;
    }

    public int insertExcDayData(String str, float f) {
        int i = 0;
        try {
            this.sqLite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.progress, Float.valueOf(f));
            if (this.sqLite != null) {
                try {
                    SQLiteDatabase sQLiteDatabase = this.sqLite;
                    String str2 = DatabaseHelper.table;
                    StringBuilder sb = new StringBuilder();
                    sb.append(DatabaseHelper.day);
                    sb.append("='");
                    sb.append(str);
                    sb.append("'");
                    i = sQLiteDatabase.update(str2, contentValues, sb.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqLite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLite.close();
        }
        return i;
    }

    public boolean tableExists(String str) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        boolean z = false;
        if (!(str == null || writableDatabase == null || !writableDatabase.isOpen())) {
            Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", str});
            if (!rawQuery.moveToFirst()) {
                rawQuery.close();
                return false;
            }
            int i = rawQuery.getInt(0);
            rawQuery.close();
            if (i > 0) {
                z = true;
            }
        }
        return z;
    }
}
