package ru.olegshulika.asmeet12_aidlsp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class SharedPreferenceIntentService extends IntentService {
    // IntentService can perform this actions
    private static final String ACTION_VALUE_ADD = "ru.olegshulika.asmeet12_aidlsp.action.value.add";
    private static final String ACTION_VALUE_GET = "ru.olegshulika.asmeet12_aidlsp.action.value.get";
    private static final String ACTION_VALUE_DELETE = "ru.olegshulika.asmeet12_aidlsp.action.value.delete";
    private static final String ACTION_SP_READ = "ru.olegshulika.asmeet12_aidlsp.action.sp.read";
    private static final String ACTION_SP_CLEAR = "ru.olegshulika.asmeet12_aidlsp.action.sp.clear";

    private static final String EXTRA_PARAM_KEY = "ru.olegshulika.asmeet12_aidlsp.extra.PARAM.KEY";
    private static final String EXTRA_PARAM_VALUE = "ru.olegshulika.asmeet12_aidlsp.extra.PARAM.VALUE";

    public SharedPreferenceIntentService() {
        super("SharedPreferenceIntentService");
    }

    /**
     * Starts this service to perform action Value_Add with parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionValueAdd(Context context, String prefKey, String prefValue) {
        Intent intent = new Intent(context, SharedPreferenceIntentService.class);
        intent.setAction(ACTION_VALUE_ADD);
        intent.putExtra(EXTRA_PARAM_KEY, prefKey);
        intent.putExtra(EXTRA_PARAM_VALUE, prefValue);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Value_Get
     *
     * @see IntentService
     */
    public static void startActionValueGet(Context context, String prefKey) {
        Intent intent = new Intent(context, SharedPreferenceIntentService.class);
        intent.setAction(ACTION_VALUE_GET);
        intent.putExtra(EXTRA_PARAM_KEY, prefKey);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Value_Delete
     *
     * @see IntentService
     */
    public static void startActionValueDelete(Context context, String prefKey) {
        Intent intent = new Intent(context, SharedPreferenceIntentService.class);
        intent.setAction(ACTION_VALUE_DELETE);
        intent.putExtra(EXTRA_PARAM_KEY, prefKey);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action SP_Read - read all values from SP
     *
     * @see IntentService
     */
    public static void startActionSPRaed(Context context) {
        Intent intent = new Intent(context, SharedPreferenceIntentService.class);
        intent.setAction(ACTION_SP_READ);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action SP_Clear - clear all values from SP
     *
     * @see IntentService
     */
    public static void startActionSPClear(Context context) {
        Intent intent = new Intent(context, SharedPreferenceIntentService.class);
        intent.setAction(ACTION_SP_CLEAR);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_VALUE_ADD.equals(action)) {
                final String prefKey = intent.getStringExtra(EXTRA_PARAM_KEY);
                final String prefValue = intent.getStringExtra(EXTRA_PARAM_VALUE);
                handleActionValueAdd(prefKey, prefValue);
            } else if (ACTION_VALUE_GET.equals(action)) {
                final String prefKey = intent.getStringExtra(EXTRA_PARAM_KEY);
                handleActionValueGet(prefKey);
            } else if (ACTION_VALUE_DELETE.equals(action)) {
                final String prefKey = intent.getStringExtra(EXTRA_PARAM_KEY);
                handleActionValueDelete(prefKey);
            } else if (ACTION_SP_READ.equals(action)) {
                handleActionSPRead();
            } else if (ACTION_SP_CLEAR.equals(action)) {
                handleActionSPClear();
            }
        }
    }

    /**
     * Handle action Value_Add in the provided background thread with the provided
     * parameters.
     */
    private boolean handleActionValueAdd(String prefKey, String prefValue) {
        SharedPreferences pref = this.getSharedPreferences(this.getPackageName(), this.MODE_PRIVATE);
        return pref.edit().putString(prefKey,prefValue).commit();
    }

    /**
     * Handle action Value_Get in the provided background thread
     */
    private String handleActionValueGet(String prefKey) {
        SharedPreferences pref = this.getSharedPreferences(this.getPackageName(), this.MODE_PRIVATE);
        return pref.getString(prefKey,null);
    }

    /**
     * Handle action Value_Delete in the provided background thread
     */
    private boolean handleActionValueDelete(String prefKey) {
        SharedPreferences pref = this.getSharedPreferences(this.getPackageName(), this.MODE_PRIVATE);
        return pref.edit().remove(prefKey).commit();
    }

    /**
     * Handle action SP_Read in the provided background thread
     */
    private Map<String,?> handleActionSPRead() {
        SharedPreferences pref = this.getSharedPreferences(this.getPackageName(), this.MODE_PRIVATE);
        return pref.getAll();
    }

    /**
     * Handle action SP_Clear in the provided background thread
     */
    private boolean handleActionSPClear() {
        SharedPreferences pref = this.getSharedPreferences(this.getPackageName(), this.MODE_PRIVATE);
        return pref.edit().clear().commit();
    }

}
