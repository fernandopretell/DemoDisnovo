package com.limapps.base.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StringUtils {

    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    public static final String LINE_BREAK = "\n";
    public static final String EMPTY_JSON_OBJECT = "{}";
    public static final String EMPTY_JSON_ARRAY = "[]";
    public static final String COMMA_SEPARATOR = ",";
    public static final String UNDERSCORE = "_";
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static String dateToString(Calendar date, String format) {
        if (date != null) {
            Date target = new Date(date.getTimeInMillis());
            return new SimpleDateFormat(format).format(target);
        }

        return null;
    }

    public static boolean isValidFullName(String fullName) {

        if (TextUtils.isEmpty(fullName)) {
            return false;
        }

        fullName = fullName.trim();
        String[] parts = fullName.split(" ");
        return !(parts.length < 2);
    }

    public static String getFirstName(@Nullable String fullName) {

        if (TextUtils.isEmpty(fullName)) {
            return null;
        }

        fullName = fullName.trim();
        String[] parts = fullName.split(" ");
        if (parts.length < 2) {
            return null;
        }

        return parts[0].trim();
    }

    public static String getLastName(@Nullable String fullName) {
        if (TextUtils.isEmpty(fullName)) {
            return null;
        }

        fullName = fullName.trim();
        String[] parts = fullName.split(" ");
        if (parts.length < 2) {
            return null;
        }

        String lastName = "";
        for (int i = 1; i < parts.length; i++) {
            lastName += parts[i];
            if (i < (parts.length - 1)) {
                lastName += " ";
            }
        }

        return lastName;
    }

    public static boolean isPhoneValid(String phone) {
        return !TextUtils.isEmpty(phone) && !phone.startsWith("-") && !phone.equals("0");
    }

    public static String generateFullName(String firstName, String lastName) {

        if (TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName)) {
            return null;
        }

        return firstName + " " + lastName;

    }

    public static String upperCaseFirstLetterAllWords(@NonNull String sentence) {
        String[] words = sentence.trim().split(" ");
        StringBuilder stringBuffer = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (word.length() > 1) {
                    stringBuffer.append(Character.toUpperCase(word.charAt(0)));
                    stringBuffer.append(word.substring(1)).append(" ");
                } else {
                    stringBuffer.append(word).append(" ");
                }
            }
        }
        return stringBuffer.toString().trim();
    }

    public static String initials(@NonNull String sentence) {
        String[] words = sentence.trim().split(" ");
        StringBuilder stringBuffer = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (word.length() > 1) {
                    stringBuffer.append(word.charAt(0));
                } else {
                    stringBuffer.append(word);
                }
            }
        }
        return stringBuffer.toString().trim();
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}