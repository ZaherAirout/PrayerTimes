package com.example.zaher.prayertimes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import prayertimesdamascus.HijriCalendar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class main extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mContentView = findViewById(R.id.tv_time);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        t = new Timer();
        this.timeTask = new TimerTask() {
            public void run() {
                main.this.handler.post(new Runnable() {
                    public void run() {
                        main.this.calc();
                        main.this.update();
                    }
                });
            }
        };
        this.t.scheduleAtFixedRate(this.timeTask, 0, 20000);


    }

    private void update() {
        TextView date = (TextView) findViewById(R.id.tv_date);
        TextView hijriDate = (TextView) findViewById(R.id.tv_hijri);
        TextView remaining = (TextView) findViewById(R.id.tv_remaining);
        TextView time = (TextView) findViewById(R.id.tv_time);
        TextView alfajer = (TextView) findViewById(R.id.tv_alfajer);
        TextView alzuhr = (TextView) findViewById(R.id.tv_alzuhr);
        TextView alasr = (TextView) findViewById(R.id.tv_alasr);
        TextView almughreb = (TextView) findViewById(R.id.tv_almughreb);
        TextView aleshaa = (TextView) findViewById(R.id.tv_aleshaa);
        date.setText(this.date1);
        hijriDate.setText(this.date2);
        remaining.setText(this.remaining);
        time.setText(this.time);
        alfajer.setText(this.time1);
        alzuhr.setText(this.time2);
        alasr.setText(this.time3);
        almughreb.setText(this.time4);
        aleshaa.setText(this.time5);
        almughreb.setTextColor(-3932160);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    //    my modifications
    private String alltimes = "508,643,1143,222,443,604|509,643,1143,222,444,604|509,643,1143,223,445,605|509,643,1144,223,445,605|509,643,1144,223,446,606|509,643,1145,224,447,607|509,643,1145,225,447,607|509,643,1145,225,448,608|509,643,1146,226,449,609|510,643,1146,227,450,610|510,643,1146,228,451,611|510,643,1147,229,452,611|510,642,1147,230,453,612|509,642,1147,230,453,612|509,642,1148,232,454,613|509,642,1148,232,454,613|509,641,1148,232,457,615|509,641,1148,233,457,615|509,641,1149,234,458,617|509,640,1149,235,459,617|509,639,1149,236,500,618|508,639,1149,237,500,618|508,639,1150,238,502,620|507,638,1150,238,502,620|507,638,1150,238,503,621|506,637,1150,239,504,622|505,637,1151,241,504,622|505,637,1151,241,505,623|504,636,1151,242,506,623|504,636,1152,243,506,624|504,636,1152,243,508,625|503,635,1152,244,509,626|503,635,1153,246,511,628|503,634,1153,247,512,628|502,634,1153,247,512,628|501,633,1153,247,513,629|500,632,1153,248,514,630|500,631,1153,249,515,631|459,630,1153,250,516,632|459,629,1153,251,517,633|458,628,1153,252,518,634|458,627,1153,253,519,635|456,626,1153,253,520,636|456,625,1153,254,521,636|455,624,1153,255,522,637|454,623,1153,256,523,638|453,622,1153,257,524,639|452,621,1153,257,525,640|452,620,1153,258,526,641|451,619,1153,258,527,642|450,618,1153,259,528,643|449,617,1153,300,529,643|448,616,1153,300,530,644|447,615,1153,301,531,645|446,614,1153,302,532,646|444,612,1152,302,532,646|444,611,1152,302,533,647|442,610,1152,303,534,648|441,609,1152,303,535,649|440,608,1152,304,536,650|440,607,1152,304,537,651|438,605,1151,305,537,651|436,604,1151,305,538,652|435,603,1151,306,539,653|434,602,1151,306,540,654|432,600,1150,306,540,654|431,559,1150,306,541,655|430,558,1150,307,542,656|428,556,1149,307,542,656|427,555,1149,307,543,657|426,554,1149,308,544,658|425,553,1149,308,545,659|423,551,1148,308,545,659|422,550,1148,309,546,700|421,549,1148,309,547,701|419,548,1148,311,548,702|418,547,1148,311,549,703|417,546,1148,311,550,704|416,544,1146,311,551,704|414,542,1146,311,551,705|412,540,1146,312,552,706|411,539,1146,312,553,707|410,538,1145,312,554,708|408,536,1145,312,554,708|406,535,1145,312,555,709|405,534,1145,313,556,711|403,532,1144,313,556,711|403,530,1144,314,558,713|402,529,1144,314,559,714|359,527,1143,314,559,714|358,526,1143,314,600,715|457,625,1243,415,701,816|455,624,1243,415,702,817|453,622,1242,415,702,818|452,621,1242,415,703,818|450,619,1241,415,704,820|448,618,1241,415,704,820|447,617,1241,415,705,822|446,615,1240,415,706,823|443,614,1240,415,706,823|442,613,1240,415,707,824|441,612,1240,415,708,825|440,610,1239,415,709,826|437,609,1239,415,709,826|436,608,1239,415,710,827|435,607,1239,415,711,828|433,605,1238,415,712,830|432,604,1238,415,713,831|430,603,1238,416,713,831|429,602,1238,416,714,833|428,601,1238,416,715,834|426,559,1237,416,715,834|426,559,1237,416,716,835|424,558,1237,416,716,836|423,557,1237,416,717,837|421,556,1237,416,718,838|420,554,1236,416,719,839|418,553,1236,416,719,840|417,552,1236,416,720,841|415,551,1236,416,721,843|414,550,1236,416,722,844|413,549,1236,416,723,845|412,548,1236,416,724,846|410,548,1236,416,724,847|409,547,1236,416,725,848|407,545,1235,416,725,848|405,544,1235,416,726,849|404,543,1235,416,727,851|402,543,1235,416,727,851|401,543,1235,416,728,852|400,541,1235,416,729,853|359,540,1235,416,730,855|358,539,1235,416,731,856|356,539,1235,416,731,856|355,538,1235,416,732,859|354,537,1235,416,733,900|353,536,1235,417,734,901|352,535,1235,417,735,902|351,535,1235,417,735,902|350,534,1235,417,736,904|350,533,1235,417,737,905|349,533,1235,417,737,905|349,533,1236,418,739,907|348,533,1236,418,739,907|347,532,1236,418,740,909|346,532,1236,418,740,909|345,531,1236,418,741,910|343,531,1236,418,741,911|343,530,1236,418,742,912|343,529,1236,418,743,913|342,529,1237,419,744,914|342,529,1237,419,744,916|340,528,1237,419,745,916|340,528,1237,419,746,917|340,528,1237,419,746,917|339,528,1237,419,746,918|339,528,1238,420,748,920|338,528,1238,420,748,920|337,528,1238,420,748,920|337,528,1238,420,749,921|338,528,1239,421,750,922|337,528,1239,421,750,923|337,527,1239,421,751,924|337,527,1239,421,751,924|336,527,1239,421,751,924|336,528,1240,422,752,925|336,528,1240,422,752,925|336,528,1240,422,753,926|336,528,1240,422,753,926|337,528,1241,423,754,928|337,528,1241,423,754,928|337,528,1241,423,754,928|337,528,1241,423,754,928|337,528,1241,423,754,928|338,529,1242,424,755,929|338,529,1242,424,755,929|338,529,1242,424,755,929|338,529,1242,424,755,929|338,529,1242,424,755,929|338,529,1242,424,755,929|338,529,1242,424,755,928|339,531,1243,425,755,928|340,531,1243,425,755,928|340,531,1243,425,755,928|341,531,1243,425,755,928|341,531,1243,425,755,928|342,532,1243,425,755,927|343,532,1243,425,755,927|343,532,1243,425,754,926|343,534,1244,426,754,926|344,534,1244,426,754,926|345,534,1244,426,754,926|346,536,1245,427,754,926|347,536,1245,427,754,925|347,536,1245,427,754,924|348,537,1245,427,753,924|350,537,1245,427,753,924|351,538,1245,427,752,922|352,538,1245,427,752,921|352,539,1245,427,751,920|352,540,1245,427,750,919|354,540,1245,427,750,919|355,541,1245,427,749,918|356,541,1245,427,749,918|357,541,1245,427,748,916|358,542,1245,427,748,916|358,542,1245,427,747,915|400,543,1245,427,747,915|400,543,1245,427,747,915|401,545,1245,427,745,913|401,546,1245,427,745,912|403,546,1245,427,744,911|404,547,1245,427,743,910|405,548,1245,426,742,909|406,549,1245,426,741,906|408,549,1245,426,741,906|409,550,1245,426,740,905|410,551,1245,426,739,903|411,552,1245,426,738,902|412,553,1245,426,737,901|414,553,1245,426,737,901|415,554,1245,426,736,859|417,555,1245,425,735,858|418,556,1245,425,734,857|419,557,1245,425,733,856|420,557,1244,425,732,854|421,557,1244,424,731,853|422,558,1244,424,730,852|423,559,1244,424,729,851|424,600,1243,423,727,848|425,600,1243,423,726,847|426,601,1243,422,725,845|427,602,1243,422,724,844|428,602,1242,421,722,842|429,603,1242,420,721,841|430,604,1242,420,720,839|431,604,1242,420,720,839|431,604,1241,419,718,838|432,605,1241,419,717,837|433,606,1241,419,716,834|434,607,1241,418,715,833|434,607,1240,417,713,831|436,608,1240,416,712,829|437,609,1240,416,711,828|437,609,1239,415,709,826|439,610,1239,415,708,825|440,611,1239,414,707,824|440,611,1238,413,705,823|441,612,1238,412,704,821|443,613,1238,412,703,820|443,613,1237,411,701,818|444,614,1237,410,700,816|444,614,1236,409,658,814|446,615,1236,409,657,813|447,616,1236,408,656,812|447,616,1235,407,654,809|449,617,1235,407,653,808|450,618,1235,406,652,807|450,618,1234,405,650,805|452,619,1234,404,649,804|452,619,1233,403,647,802|452,621,1233,401,645,800|453,622,1233,401,644,759|454,623,1233,400,643,758|455,623,1232,359,641,756|456,624,1232,359,640,755|457,625,1232,358,639,754|458,626,1232,358,638,753|500,626,1231,356,637,752|501,627,1231,355,636,751|502,628,1231,355,635,750|502,629,1231,354,633,748|502,629,1230,353,631,746|503,631,1230,351,629,744|404,532,1130,251,528,642|405,533,1130,250,527,641|405,533,1129,248,525,639|406,534,1129,248,524,638|407,535,1129,247,523,637|407,535,1128,245,521,635|408,536,1128,245,520,634|409,537,1128,244,519,633|409,537,1127,243,517,631|410,538,1127,242,516,630|411,539,1127,242,515,629|412,540,1127,241,514,628|413,540,1126,239,512,626|414,541,1126,239,511,625|415,542,1126,238,510,624|415,543,1126,237,509,623|415,543,1125,236,507,621|416,544,1125,235,506,620|417,545,1125,234,505,619|418,546,1125,234,504,618|419,547,1125,233,503,617|419,547,1124,231,501,615|420,548,1124,231,500,614|421,549,1124,230,459,614|422,550,1124,229,458,613|423,551,1124,228,457,612|424,552,1124,227,456,611|424,553,1124,226,455,610|424,553,1123,225,453,608|425,554,1123,225,452,607|426,555,1123,224,451,606|426,556,1123,223,450,606|428,557,1123,223,449,605|428,558,1123,222,448,604|429,559,1123,221,447,603|429,600,1123,220,446,602|430,601,1123,219,445,601|430,602,1123,218,444,600|431,603,1123,217,443,559|433,604,1123,217,443,559|433,604,1123,217,442,559|435,606,1124,217,442,559|435,607,1124,216,441,558|436,608,1124,215,441,557|437,608,1124,215,440,557|437,609,1124,215,439,556|438,610,1124,214,438,556|439,610,1124,214,438,556|440,612,1125,214,438,556|442,613,1125,213,437,555|442,614,1125,213,436,554|442,614,1125,213,436,554|444,616,1126,213,436,554|445,616,1126,213,436,554|445,618,1127,213,435,554|446,619,1127,212,435,554|446,620,1127,212,434,553|447,620,1127,212,434,553|448,622,1128,212,434,553|448,622,1128,212,433,552|449,623,1128,211,433,552|450,623,1128,211,433,552|451,625,1129,211,433,552|452,626,1129,211,433,552|452,626,1129,210,432,552|452,626,1129,210,432,552|453,628,1130,210,432,552|454,628,1130,210,432,552|454,628,1130,210,432,552|455,630,1131,210,432,552|456,630,1131,211,432,553|457,631,1132,212,433,553|458,633,1133,212,433,553|458,633,1133,212,433,554|459,633,1133,212,433,554|500,634,1134,212,434,555|500,634,1134,213,434,555|501,636,1135,213,434,555|501,636,1135,213,434,555|501,636,1135,213,434,555|502,637,1136,214,435,556|502,637,1136,214,435,556|503,637,1137,215,436,557|503,638,1137,215,436,557|504,639,1138,216,437,558|505,640,1139,217,438,559|506,641,1140,218,439,600|506,641,1140,218,439,600|507,641,1140,218,440,601|507,641,1141,220,440,601|507,641,1141,220,441,602|508,642,1142,221,442,603";
    private int asrOffset;
    protected Context context = this;
    private int currMin;
    private String date1;
    private String date2;
    public String[] days;
    private int dhuhrOffset;
    private int fajrOffset;
    private Handler handler = new Handler();
    private int hijriOffset;
    private int ishaOffset;
    private int maghribOffset;
    private String remaining;
    private TextView remainingTitle;
    private int s1;
    private int s2;
    private int s3;
    private int s4;
    private int s5;
    private int s6;
    private SharedPreferences sp;
    private int sunriseOffset;
    private Timer t;
    private String time;
    private String time1;
    private String time1Title;
    private String time2;
    private String time2Title;
    private String time3;
    private String time3Title;
    private String time4;
    private String time4Title;
    private String time5;
    private String time5Title;
    private String time6;
    private String time6Title;
    private TimerTask timeTask;
    private TextView timeTitle;

    public void calc() {
        String[] times;
        String currentTime;
        String str;
//        this.hijriOffset = Integer.valueOf(this.sp.getString("hijriOffset", "0")).intValue();
        this.hijriOffset = 0;
        Date now = new Date();
        String fullDate = "";
        switch (now.getDay()) {
            case 0:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الأحد ").toString();
                break;
            case 1:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الاثنين ").toString();
                break;
            case 2:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الثلاثاء ").toString();
                break;
            case 3:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الأربعاء ").toString();
                break;
            case 4:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الخميس ").toString();
                break;
            case 5:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("الجمعة ").toString();
                break;
            case 6:
                fullDate = new StringBuilder(String.valueOf(fullDate)).append("السبت ").toString();
                break;
        }
        this.date2 = new StringBuilder(String.valueOf(fullDate)).append(now.getDate()).append("-").append(now.getMonth() + 1).append("-").append(now.getYear() + 1900).toString();
//        Toast.makeText(context,new StringBuilder(String.valueOf(fullDate)).append(now.getDate()).append("-").append(now.getMonth() + 1).append("-").append(now.getYear() + 1900).toString(), Toast.LENGTH_SHORT).show();
        Date hCurrDate = new Date();
        hCurrDate.setTime(hCurrDate.getTime() + ((long) (this.hijriOffset * 86400000)));
        HijriCalendar hd = new HijriCalendar(hCurrDate.getYear() + 1900, hCurrDate.getMonth() + 1, hCurrDate.getDate());
        this.date1 = hd.getHijriMonthName() + " " + hd.getHijriDay() + "-" + hd.getHijriMonth() + "-" + hd.getHijriYear();
//        Toast.makeText(context, hd.getHijriMonthName() + " " + hd.getHijriDay() + "-" + hd.getHijriMonth() + "-" + hd.getHijriYear(), Toast.LENGTH_SHORT).show();

        int currDayIndex = ((now.getMonth() * 30) + now.getDate()) - 1;
        if (now.getMonth() >= 0) {
        }
        if (now.getMonth() >= 1) {
            currDayIndex++;
        }
        if (now.getMonth() >= 2) {
            currDayIndex--;
        }
        if (now.getMonth() >= 3) {
            currDayIndex++;
        }
        if (now.getMonth() >= 4) {
        }
        if (now.getMonth() >= 5) {
            currDayIndex++;
        }
        if (now.getMonth() >= 6) {
        }
        if (now.getMonth() >= 7) {
            currDayIndex++;
        }
        if (now.getMonth() >= 8) {
            currDayIndex++;
        }
        if (now.getMonth() >= 9) {
        }
        if (now.getMonth() >= 10) {
            currDayIndex++;
        }
        if (now.getMonth() >= 11) {
            times = getDays()[currDayIndex].split("[,]");
            this.time1 = formatTime(times[0]);
            this.time2 = formatTime(times[1]);
            this.time3 = formatTime(times[2]);
            this.time4 = formatTime(times[3]);
            this.time5 = formatTime(times[4]);
            this.time6 = formatTime(times[5]);
        } else {
            times = getDays()[currDayIndex].split("[,]");
            this.time1 = formatTime(times[0]);
            this.time2 = formatTime(times[1]);
            this.time3 = formatTime(times[2]);
            this.time4 = formatTime(times[3]);
            this.time5 = formatTime(times[4]);
            this.time6 = formatTime(times[5]);
            currentTime = this.time;
        }
        int abs = (now.getHours() == 0 || now.getHours() > 12) ? Math.abs(now.getHours() - 12) : now.getHours();
        StringBuilder append = new StringBuilder(String.valueOf(abs)).append(":");
        if (now.getMinutes() < 10) {
            str = "0" + now.getMinutes();
        } else {
            str = String.valueOf(Integer.valueOf(now.getMinutes()));
        }
        time = append.append(str).toString();

        Toast.makeText(context, append.append(str).toString(), Toast.LENGTH_SHORT).show();
        String[] s1arr = formatTime(times[0]).split(":");
        String[] s2arr = formatTime(times[1]).split(":");
        String[] s3arr = formatTime(times[2]).split(":");
        String[] s4arr = formatTime(times[3]).split(":");
        String[] s5arr = formatTime(times[4]).split(":");
        String[] s6arr = formatTime(times[5]).split(":");
        int s1 = (Integer.parseInt(s1arr[0]) * 60) + Integer.parseInt(s1arr[1]);
        int s2 = (Integer.parseInt(s2arr[0]) * 60) + Integer.parseInt(s2arr[1]);
        int s3 = (Integer.parseInt(s3arr[0]) * 60) + Integer.parseInt(s3arr[1]);
        int s4 = ((Integer.parseInt(s4arr[0]) + 12) * 60) + Integer.parseInt(s4arr[1]);
        int s5 = ((Integer.parseInt(s5arr[0]) + 12) * 60) + Integer.parseInt(s5arr[1]);
        int s6 = ((Integer.parseInt(s6arr[0]) + 12) * 60) + Integer.parseInt(s6arr[1]);
        this.currMin = (now.getHours() * 60) + now.getMinutes();
        if (this.currMin < s6) {
            if (this.currMin < s1) {
                this.remaining = timeStr(s1 - this.currMin);
            } else if (this.currMin > s1 && this.currMin < s2) {
                this.remaining = timeStr(s2 - this.currMin);
            } else if (this.currMin > s2 && this.currMin < s3) {
                this.remaining = timeStr(s3 - this.currMin);
            } else if (this.currMin > s3 && this.currMin < s4) {
                this.remaining = timeStr(s4 - this.currMin);
            } else if (this.currMin > s4 && this.currMin < s5) {
                this.remaining = timeStr(s5 - this.currMin);
            } else if (this.currMin <= s5 || this.currMin >= s6) {
                this.remaining = "";
            } else {
                this.remaining = timeStr(s6 - this.currMin);
            }
        } else {
            this.remaining = "";
        }
//        Toast.makeText(context, remaining, Toast.LENGTH_SHORT).show();

//        this.time1Title.setTextColor(this.currMin <= s1 ? -3932160 : -6710887);
//        textView = this.time2Title;
//        abs = (this.currMin <= s1 || this.currMin > s2) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time3Title;
//        abs = (this.currMin <= s2 || this.currMin > s3) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time4Title;
//        abs = (this.currMin <= s3 || this.currMin > s4) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time5Title;
//        abs = (this.currMin <= s4 || this.currMin > s5) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time6Title;
//        abs = (this.currMin <= s5 || this.currMin > s6) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        this.time1.setTextColor(this.currMin <= s1 ? -3932160 : -6710887);
//        textView = this.time2;
//        abs = (this.currMin <= s1 || this.currMin > s2) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time3;
//        abs = (this.currMin <= s2 || this.currMin > s3) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time4;
//        abs = (this.currMin <= s3 || this.currMin > s4) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time5;
//        abs = (this.currMin <= s4 || this.currMin > s5) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
//        textView = this.time6;
//        abs = (this.currMin <= s5 || this.currMin > s6) ? -6710887 : -3932160;
//        textView.setTextColor(abs);
    }

    public String[] getDays() {
        this.days = this.alltimes.split("[|]");
        return this.days;
    }

    public String formatTime(String time) {
        if (time.length() == 3) {
            return time.substring(0, 1) + ":" + time.substring(1, 3);
        }
        return time.substring(0, 2) + ":" + time.substring(2, 4);
    }

    public String timeStr(int time) {
        return new StringBuilder(String.valueOf((int) Math.floor((double) (time / 60)))).append(":").append(time % 60 < 10 ? "0" + (time % 60) : Integer.valueOf(time % 60)).toString();
    }


}
