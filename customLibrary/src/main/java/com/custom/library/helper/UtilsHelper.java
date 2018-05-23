package com.custom.library.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.library.BuildConfig;
import com.custom.library.R;
import com.custom.library.app.UtilsConstant;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Nalpot on 05/05/2018.
 * Email: rifqim035@gmail.com
 */
@SuppressWarnings("deprecation")
public class UtilsHelper {
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    /**
     * method to check if android version is lollipop
     *
     * @return this return value
     */
    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * method to check if android version is Marsh
     *
     * @return this return value
     */
    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * method to check if android version is lollipop
     *
     * @return this return value
     */
    public static boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * method to get color
     *
     * @param context this is the first parameter for getColor  method
     * @param id      this is the second parameter for getColor  method
     * @return return value
     */
    public static int getColor(Context context, int id) {
        if (isAndroid5()) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static Typeface setTypeFace(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), "fonts/" + name + "");
                    cache.put(name, t);
                } catch (Exception e) {
                    LogCatAsset(null, e.getMessage());
                    return null;
                }
            }
            return cache.get(name);
        }
    }

    @SuppressLint("ServiceCast")
    public static void hideKeyboard(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * method for get a custom CustomToast
     *
     * @param Message this is the second parameter for CustomToast  method
     */
    public static void CustomToast(Context mContext, String Message) {
        LinearLayout CustomToastLayout = new LinearLayout(mContext.getApplicationContext());
        CustomToastLayout.setBackgroundResource(R.drawable.bg_custom_toast);
        CustomToastLayout.setGravity(Gravity.TOP);
        TextView message = new TextView(mContext.getApplicationContext());
        message.setTextColor(Color.WHITE);
        message.setTextSize(13);
        message.setPadding(20, 20, 20, 20);
        message.setGravity(Gravity.CENTER);
        message.setText(Message);
        CustomToastLayout.addView(message);
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(CustomToastLayout);
        toast.setGravity(Gravity.CENTER, 0, 50);
        toast.show();
    }

    public static void LogCat(String Message) {
        if (UtilsConstant.DEBUG_MODE) {
            if (Message != null) {
                if (!BuildConfig.DEBUG) {
                    return;
                }
                Log.e(UtilsConstant.TAG, Message);
            }
        }
    }

    public static void LogCatAsset(String tag, String Message) {
        if (UtilsConstant.DEBUG_MODE) {
            if (Message != null) {
                if (tag == null) tag = UtilsConstant.TAG;
                Log.println(Log.ASSERT, tag, Message);
            }else{
                Log.i(tag,"Error");
            }
        }
    }

    /**
     * method to get drawable
     *
     * @param context this is the first parameter for getDrawable  method
     * @param id      this is the second parameter for getDrawable  method
     * @return return value
     */
    public static Drawable getDrawable(Context context, int id) {
        if (isAndroid5()) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * method to loadCircleImage json files from asset directory
     *
     * @param mContext this is  parameter for loadJSONFromAsset  method
     * @return return value
     */
    public static String loadJSONFromAsset(Context mContext) {
        String json;
        try {
            InputStream is = mContext.getAssets().open("country_phones.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * shake EditText error
     *
     * @param mContext this is the first parameter for showErrorEditText  method
     * @param view     this is the second parameter for showErrorEditText  method
     */
    public static void showErrorEditText(Context mContext, View view) {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
        view.startAnimation(shake);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = formatter.format(new Date(timestamp * 1000));

        return ConvertDate(dateString, "dd/MM/yyyy HH:mm:ss", "dd MMM yyyy HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static String ConvertDate(String date, String formatAsal, String formatTujuan) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatAsal);
        SimpleDateFormat sdf1 = new SimpleDateFormat(formatTujuan);

        Date tgl = null;
        try {
            tgl = sdf.parse(date);
        } catch (ParseException e) {
            LogCat(e.getMessage());
        }

        return sdf1.format(tgl);
    }

    public static String getAppVersion(Context mContext) {
        PackageInfo packageinfo;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogCat(" getAppVersion NameNotFoundException " + e.getMessage());
            return null;
        }
    }

    public static int getAppVersionCode(Context mContext) {
        PackageInfo packageinfo;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogCat(" getAppVersion NameNotFoundException " + e.getMessage());
            return 0;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateCurrent(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String DecimalFormat(String nominal) {
        DecimalFormat formatter = new DecimalFormat("###");
        formatter.setMinimumFractionDigits(0);
        return formatter.format(nominal);
    }

    public static String DecimalFormatForPercent(double nominal) {
        DecimalFormat formatter = new DecimalFormat("###.##");
        return formatter.format(nominal);
    }

    public static void CopyText(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("data", text.trim());
        clipboard.setPrimaryClip(clip);
    }

    public static String convertCurrency(long number) {
        DecimalFormat currency = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        currency.setMinimumFractionDigits(0);
        currency.setDecimalFormatSymbols(formatRp);
        return currency.format(number);
    }

    @SuppressWarnings("RestrictedApi")
    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    public static boolean checkEditText(HashMap<View, TypeEdit> viewes, @Nullable View views) {
        EditText editText;
        boolean isSuccess = true;
        for (Map.Entry<View, TypeEdit> entry : viewes.entrySet()) {
            if (entry.getKey() instanceof EditText) {
                editText = (EditText) entry.getKey();
                if (TextUtils.isEmpty(editText.getText())) {
                    editText.setError("Tidak boleh kosong");
                    isSuccess = false;
                } else {
                    String s = ((EditText) entry.getKey()).getText().toString();
                    switch (entry.getValue()) {
                        case PHONE:
                            continue;
                        case REPEAT_PASSWORD:
                            if (views instanceof EditText) {
                                EditText text = (EditText) views;
                                if (s.equals(text.getText().toString())) {
                                    continue;
                                } else {
                                    text.setError("Password tidak sama");
                                    isSuccess = false;
                                    continue;
                                }
                            } else {
                                isSuccess = false;
                                continue;
                            }
                        case TEXT:
                            if (TextUtils.isEmpty(editText.getText())) {
                                editText.setError("Tidak boleh kosong");
                                isSuccess = false;
                                continue;
                            } else
                                continue;
                        case EMAIL:
                            if (!(!s.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(s).matches())) {
                                editText.setError("Email tidak valid");
                                isSuccess = false;
                                continue;
                            } else {
                                continue;
                            }
                        case URL:
                            if (!(!s.isEmpty() && Patterns.WEB_URL.matcher(s).matches())) {
                                editText.setError("URL tidak valid");
                                isSuccess = false;
                                continue;
                            } else
                                continue;
                        case USERNAME:
                            if (s.isEmpty() || s.contains(" ")) {
                                editText.setError("Username tidak boleh ada spasi");
                                isSuccess = false;
                            }
                    }
                }
            }
        }

        return isSuccess;
    }
}
