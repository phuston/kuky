package com.example.keenan.kuky.helpers;

import android.content.Context;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.models.Ku;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class KuComposeHelper {
    //Syllables --> Used to store dictionary of words and syllable counts
    private Map<String, Integer> mSyllables = new HashMap<String, Integer>();
    private Context context;


    public KuComposeHelper(Context context) throws IOException {
        this.context = context;

        // Load syllable dictionary to save time on loading it again.
        loadSyllableDict();
    }

    private void loadSyllableDict() throws IOException {
        InputStream raw = context.getResources().openRawResource(R.raw.syllables);
        InputStreamReader streamReader = new InputStreamReader(raw);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        CSVReader reader = new CSVReader(bufferedReader);
        String[] nextLine;
        while((nextLine = reader.readNext()) != null) {
            mSyllables.put(nextLine[0], Integer.parseInt(nextLine[1]));
        }
        reader.close();
    }

    public int getSyllables(String line) {
        int count = 0;
        for (String word : line.split("\\s+")) {
            if (mSyllables.get(word) != null) {
                count += mSyllables.get(word);
            }
        }
        return count;
    }

    public int[] checkKu(String line1, String line2, String line3) {
        return new int[] {getSyllables(line1), getSyllables(line2), getSyllables(line3)};
    }
}
