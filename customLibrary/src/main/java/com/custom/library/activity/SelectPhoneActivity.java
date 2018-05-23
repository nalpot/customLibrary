package com.custom.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.library.R;
import com.custom.library.app.UtilsConstant;
import com.custom.library.helper.CountriesFetcher;
import com.custom.library.model.CountriesModel;
import com.custom.library.ui.EdittextShake;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Email: rifqim035@gmail.com
 */

public class SelectPhoneActivity extends AppCompatActivity {
    AppCompatTextView userCountryCode;
    EdittextShake userPhone;
    TextView shortDescription;
    RelativeLayout layout;

    private PhoneNumberUtil mPhoneUtil = PhoneNumberUtil.getInstance();
    private CountriesModel mSelectedCountry;
    private CountriesFetcher.CountryList mCountries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phone);
        userCountryCode = (AppCompatTextView) findViewById(R.id.country_code);
        userPhone = (EdittextShake) findViewById(R.id.phone_input);
        shortDescription = (TextView) findViewById(R.id.short_description_phone);
        layout = (RelativeLayout) findViewById(R.id.layout);
        initializerView();

        String img = getIntent().getStringExtra("background");
        if (img != null) {
            int image = getResources().getIdentifier(img, "drawable", this.getPackageName());
            layout.setBackgroundResource(image);
        }
    }

    private void initializerView() {
        userPhone.setEnabled(true);
        userCountryCode.setVisibility(View.VISIBLE);
        userCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SelectPhoneActivity.this, CountryActivity.class);
                mIntent.putExtra("forCountry", false);
                mIntent.putExtra("background", getIntent().getStringExtra("background"));
                SelectPhoneActivity.this.startActivityForResult(mIntent, UtilsConstant.SELECT_COUNTRY);
            }
        });

        mCountries = CountriesFetcher.getCountries(this);
    }

    @SuppressWarnings("unused")
    public void onSave(View view) {
        validateInformation();
    }

    /**
     * method to validate user information
     */
    private void validateInformation() {
        hideKeyboard();
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        if (phoneNumber != null) {
            String phoneNumberFinal = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            if (isValid()) {
                String internationalFormat = phoneNumberFinal.replace("-", "");
                String finalResult = internationalFormat.replace(" ", "");
                Intent mIntent = new Intent();
                mIntent.putExtra(UtilsConstant.KEY_PHONE, finalResult);
                setResult(Activity.RESULT_OK, mIntent);
                finish();
            } else {
                userPhone.setError(getString(R.string.enter_a_val_number));
            }
        } else {
            userPhone.setError(getString(R.string.enter_a_val_number));
        }
    }

    /**
     * Get PhoneNumber object
     *
     * @return PhoneNumber | null on error
     */
    @SuppressWarnings("unused")
    public Phonenumber.PhoneNumber getPhoneNumber() {
        try {
            String iso = null;
            if (mSelectedCountry != null) {
                iso = mSelectedCountry.getCode();
            }
            String phone = userCountryCode.getText().toString().concat(userPhone.getText().toString());
            return mPhoneUtil.parse(phone, iso);
        } catch (NumberParseException ignored) {
            return null;
        }
    }


    /**
     * Check if number is valid
     *
     * @return boolean
     */
    @SuppressWarnings("unused")
    public boolean isValid() {
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        return phoneNumber != null && mPhoneUtil.isValidNumber(phoneNumber);
    }

    /**
     * Hide keyboard from phoneEdit field
     */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(userPhone.getWindowToken(), 0);
    }


    /**
     * Set hint number for country
     */
    private void setHint() {

        if (userPhone != null && mSelectedCountry != null && mSelectedCountry.getCode() != null) {
            Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.getExampleNumberForType(mSelectedCountry.getCode(), PhoneNumberUtil.PhoneNumberType.MOBILE);
            if (phoneNumber != null) {
                String internationalNumber = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                String finalPhone = internationalNumber.substring(mSelectedCountry.getDial_code().length());
                userPhone.setHint(finalPhone);
                int numberLength = internationalNumber.length();
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(numberLength);
                userPhone.setFilters(fArray);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UtilsConstant.SELECT_COUNTRY) {
                userPhone.setEnabled(true);
                String codeIso = data.getStringExtra("countryIso");
                int defaultIdx = mCountries.indexOfIso(codeIso);
                mSelectedCountry = mCountries.get(defaultIdx);
                userCountryCode.setText(mSelectedCountry.getDial_code());
                setHint();
            }
        }
    }

}
