package QuestionHandling;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class DatabaseReader extends SQLiteAssetHelper {
    DatabaseReader(Context context) {
        super(context, "data.db", null, 1);
    }
}
