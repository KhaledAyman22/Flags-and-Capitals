package UIEffects;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class WidthHeight {
    private Context context;

    public WidthHeight(Context context, int width, int height, int maxWidth, int maxHeight, Button c1, Button c2, Button c3, Button c4){
        this.context=context;
        int w = getW(width,maxWidth);
        int h = getH(height,maxHeight);
        c1.setWidth(w);
        c2.setWidth(w);
        c3.setWidth(w);
        c4.setWidth(w);
        c1.setHeight(h);
        c2.setHeight(h);
        c3.setHeight(h);
        c4.setHeight(h);
    }

    public WidthHeight(Context context, int width, int height, int maxWidth, int maxHeight, ImageButton c1, ImageButton c2, ImageButton c3, ImageButton c4){
        this.context=context;

    }

    public WidthHeight(Context context, int width, int height, int maxWidth, int maxHeight, TextView textView){
        this.context=context;
        textView.setWidth(getW(width,maxWidth));
        textView.setHeight(getH(height,maxHeight));
    }

    public WidthHeight(Context context, int width, int height, int maxWidth, int maxHeight, EditText email, EditText pass){
        this.context=context;
        int w = getW(width,maxWidth);
        int h = getH(height,maxHeight);
        email.setWidth(w);
        email.setHeight(h);
        pass.setWidth(w);
        pass.setHeight(h);
    }

    private int getW(int width,int maxWidth){
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int buttonWidth = (int) (dpToPx(width) * (metrics.widthPixels / 328.80));

        buttonWidth =pxToDp(buttonWidth);
        if(buttonWidth >maxWidth)
            buttonWidth =dpToPx(maxWidth);

        return buttonWidth;
    }

    private int getH(int height,int maxHeight){
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int buttonHeight = (int) (dpToPx(height) * (metrics.heightPixels / 688.80));

        buttonHeight =pxToDp(buttonHeight);
        if(buttonHeight >maxHeight)
            buttonHeight =dpToPx(maxHeight);

        return buttonHeight;
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
