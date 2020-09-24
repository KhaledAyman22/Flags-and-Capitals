package QuestionHandling;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private SQLiteOpenHelper openHelper;
    private String mode;

    public DataRetriever(Context context,String mode) {
        this.openHelper = new DatabaseReader(context);
        this.mode = mode;
    }


    public List<Country> getData() {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        List<Country> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if((mode.equals("FtoCa") || mode.equals("CatoF")) && cursor.getString(1).equals(""))
                {cursor.moveToNext(); continue;}
            data.add(new Country(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return data;
    }

}
