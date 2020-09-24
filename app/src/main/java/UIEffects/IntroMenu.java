package UIEffects;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

public class IntroMenu {

    public IntroMenu(Context context, final TextView guess, final Button country, final Button capital, final Button flag1, final Button flag2){
        new WidthHeight(context,300,96,330,120,guess);

        guess.setTranslationY(-1000);
        country.setTranslationX(-2000);
        capital.setTranslationX(2000);
        flag1.setTranslationX(-2000);
        flag2.setTranslationX(2000);
        guess.animate().translationYBy(1000).setDuration(1500);
        country.animate().translationXBy(2000).setDuration(1500);
        capital.animate().translationXBy(-2000).setDuration(1500);
        flag1.animate().translationXBy(2000).setDuration(1500);
        flag2.animate().translationXBy(-2000).setDuration(1500);
    }
}
