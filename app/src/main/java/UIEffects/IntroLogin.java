package UIEffects;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroLogin {
    private final TextView flags, and, capitals, title;
    private final Button  g, e;

    public IntroLogin(Context context, final TextView flags, final TextView and, final TextView capitals, final TextView title, final Button g, final Button e){
        new WidthHeight(context,176,176,220,220,flags);
        new WidthHeight(context,187,144,231,188,capitals);
        new WidthHeight(context,85,110,129,154,and);
        new WidthHeight(context,298,131,500,160,title);
        this.flags=flags;
        this.and=and;
        this.capitals=capitals;
        this.title=title;
        this.g=g;
        this.e=e;
    }

    public void Start(){
        flags.setTranslationY(-2000);
        and.setTranslationX(-2000);
        capitals.setTranslationY(2000);

        //IN
        flags.animate().translationYBy(2000).setDuration(700);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() { and.animate().translationXBy(2000).setDuration(700); }},500);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() { capitals.animate().translationYBy(-2000).setDuration(700); }},1000);

        //Out
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flags.animate().translationXBy(2000).setDuration(1500);
                and.animate().alpha(0).setDuration(1000);
                capitals.animate().translationXBy(-2000).setDuration(1500);
            }},2100);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginButtonsIntro();
            }},2700);
    }

    public void LoginButtonsIntro(){

        g.setTranslationY(2000);
        e.setTranslationY(2000);
        title.setAlpha(0);

        title.setVisibility(View.VISIBLE);
        g.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);

        title.animate().alpha(1).setDuration(500);

        g.animate().translationYBy(-2000).setDuration(500);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() { e.animate().translationYBy(-2000).setDuration(500); }},100);
    }

}
