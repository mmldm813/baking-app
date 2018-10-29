package com.example.michellemedina.bakingapp;

import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataHelper {

    public static String readFakeData() {
        try {
            InputStream is = InstrumentationRegistry.getContext().getAssets().open("fake_data.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
