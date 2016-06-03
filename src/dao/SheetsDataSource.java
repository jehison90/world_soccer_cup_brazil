package dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.psl.mundial.persistence.DatabaseOpenHelper;

import dto.Sheet;

public class SheetsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private DatabaseOpenHelper dbHelper;
  private String[] allColumns = { DatabaseOpenHelper.COLUMN_ID,
		  DatabaseOpenHelper.COLUMN_COLOR };

  public SheetsDataSource(Context context) {
    dbHelper = new DatabaseOpenHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

//  public Sheet createComment(String comment) {
//    ContentValues values = new ContentValues();
//    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
//    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
//        values);
//    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//        null, null, null);
//    cursor.moveToFirst();
//    Comment newComment = cursorToComment(cursor);
//    cursor.close();
//    return newComment;
//  }

//  public void deleteComment(Comment comment) {
//    long id = comment.getId();
//    System.out.println("Comment deleted with id: " + id);
//    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//        + " = " + id, null);
//  }

  public List<Sheet> getAllSheets() {
    List<Sheet> sheets = new ArrayList<Sheet>();

    Cursor cursor = database.query(DatabaseOpenHelper.TABLE_SHEET,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Sheet sheet = cursorToSheet(cursor);
      sheets.add(sheet);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return sheets;
  }

  private Sheet cursorToSheet(Cursor cursor) {
    Sheet sheet = new Sheet();
    sheet.setId(cursor.getInt(0));
    sheet.setColor(cursor.getString(1));
    return sheet;
  }
  
  public boolean updateSheet(int sheetId, String color){
		String sqlUpdate = "UPDATE sheet SET color = '"+color+"' WHERE id = "+sheetId;
		try {			
			database.compileStatement(sqlUpdate);
			database.execSQL(sqlUpdate);
			Log.d("update", sqlUpdate);
			return true;
		} catch (Exception e) {
			Log.e("error updating sheet", "error updating sheet "+sheetId+" :"+e.getMessage());
			return false;
		}
	}
  
} 