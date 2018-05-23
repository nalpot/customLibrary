package com.custom.library.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.custom.library.R;
import com.custom.library.adapter.CountriesAdapter;
import com.custom.library.adapter.TextWatcherAdapter;
import com.custom.library.helper.UtilsHelper;
import com.custom.library.model.CountriesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Abderrahim El imame on 10/30/16.
 *
 * @Email : abderrahim.elimame@gmail.com
 * @Author : https://twitter.com/Ben__Cherif
 * @Skype : ben-_-cherif
 */

public class CountryActivity extends AppCompatActivity {

    TextInputEditText searchInput;
    AppCompatImageView clearBtn;
    AppCompatImageView closeBtn;
    RecyclerView CountriesList;
    LinearLayout layouts;
    private CountriesAdapter mCountriesAdapter;
    private boolean forCountry;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counrty);

        searchInput = (TextInputEditText) findViewById(R.id.search_input);
        CountriesList = (RecyclerView) findViewById(R.id.CounrtriesList);
        closeBtn = (AppCompatImageView) findViewById(R.id.close_btn_search_view);
        clearBtn = (AppCompatImageView) findViewById(R.id.clear_btn_search_view);
        layouts = (LinearLayout) findViewById(R.id.layouts);
        initializerView();
        String img = getIntent().getStringExtra("background");
        if (img != null) {
            int image = getResources().getIdentifier(img, "drawable", this.getPackageName());
            layouts.setBackgroundResource(image);
        }
    }

    void initializerView() {

        if (getIntent().getExtras() != null) {
            forCountry = getIntent().getExtras().getBoolean("forCountry", false);
            initializerSearchView(searchInput, clearBtn);
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CountryActivity.this.clearSearchView();
                }
            });
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CountryActivity.this.closeSearchView();
                }
            });
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            CountriesList.setLayoutManager(mLinearLayoutManager);
            mCountriesAdapter = new CountriesAdapter(this, forCountry);
            CountriesList.setAdapter(mCountriesAdapter);
            Gson gson = new Gson();
            final List<CountriesModel> list = gson.fromJson(UtilsHelper.loadJSONFromAsset(this), new TypeToken<List<CountriesModel>>() {
            }.getType());
            mCountriesAdapter.setCountries(list);
        }

    }


    /**
     * method to clear/reset search view content
     */
    public void clearSearchView() {
        if (searchInput.getText() != null) {
            searchInput.setText("");
            Gson gson = new Gson();
            final List<CountriesModel> list = gson.fromJson(UtilsHelper.loadJSONFromAsset(this), new TypeToken<List<CountriesModel>>() {
            }.getType());
            mCountriesAdapter.setCountries(list);
        }

    }

    /**
     * method to initial the search view
     */
    public void initializerSearchView(TextInputEditText searchInput, final ImageView clearSearchBtn) {

        final Context context = this;
        searchInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });
        searchInput.addTextChangedListener(new TextWatcherAdapter() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                clearSearchBtn.setVisibility(View.GONE);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCountriesAdapter.setString(s.toString());
                Search(s.toString().trim());
                clearSearchBtn.setVisibility(View.VISIBLE);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    clearSearchBtn.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    final List<CountriesModel> list = gson.fromJson(UtilsHelper.loadJSONFromAsset(CountryActivity.this), new TypeToken<List<CountriesModel>>() {
                    }.getType());
                    mCountriesAdapter.setCountries(list);
                }
            }
        });

    }

    /**
     * method to start searching
     *
     * @param string this is parameter of Search method
     */
    public void Search(String string) {

        final List<CountriesModel> filteredModelList;
        filteredModelList = FilterList(string);
        if (filteredModelList.size() != 0) {
            mCountriesAdapter.animateTo(filteredModelList);
            CountriesList.scrollToPosition(0);
        }
    }

    /**
     * method to filter the list
     *
     * @param query this is parameter of FilterList method
     * @return this for what method return
     */
    private List<CountriesModel> FilterList(String query) {
        query = query.toLowerCase();
        List<CountriesModel> countriesModelList = mCountriesAdapter.getCountries();
        final List<CountriesModel> filteredModelList = new ArrayList<>();
        for (CountriesModel countriesModel : countriesModelList) {
            final String name = countriesModel.getName().toLowerCase();
            if (name.contains(query)) {
                filteredModelList.add(countriesModel);
            }
        }
        return filteredModelList;
    }

    /**
     * method to close the search view
     */
    public void closeSearchView() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
