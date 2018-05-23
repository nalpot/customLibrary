package com.custom.library.helper.processing;

import android.os.AsyncTask;

/**
 * Created by Nalpot on 28/02/2018.
 * Author : Nalpot
 */

@SuppressWarnings("unused")
public class AsynctaskHelper extends AsyncTask<Void, String, Boolean> {
    private static AsynctaskHelper instance;
    private Response response;
    private OnSuccess onSuccess;

    public static AsynctaskHelper getInstance() {
        AsynctaskHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (AsynctaskHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AsynctaskHelper();
                }
            }
        }
        return localInstance;
    }

    public void process(Response response) {
        this.response = response;
        execute();
    }

    public void process(Response response, OnSuccess onSuccess) {
        this.response = response;
        this.onSuccess = onSuccess;
        execute();
    }

    public void cancels() {
        if (!this.isCancelled())
            cancel(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return response.doInBackground();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (onSuccess != null)
            onSuccess.onSuccess(aBoolean);
    }

    public interface Response {
        boolean doInBackground();
    }

    public interface OnSuccess {
        void onSuccess(boolean isSuccess);
    }
}
