package com.example.keenan.kuky.helpers;

import android.content.Context;

import com.example.keenan.kuky.R;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public int countSyllables(String word) {
        Pattern pattern1 = Pattern.compile("[eaoui][eaoui]");
        Pattern pattern2 = Pattern.compile("[eaoui][^eaoui]");
        Pattern pattern3 = Pattern.compile("[eaoui][eaoui][eaoui]");
        Pattern pattern4 = Pattern.compile("[eaoui]");

        String[] exception_add = {"serious", "critical"};
        String[] exception_del = {"fortunately", "unfortunately"};
        String[] co_one = {"cool","coach","coat","coal","count","coin","coarse","coup","coif","cook","coign","coiffe","coof","court"};
        String[] co_two = {"coapt","coed","coinci"};

        String[] pre_one = {"preach"};

        int syls = 0;
        int disc = 0;

        if (word.length() <= 3) {
            syls = 1;
            return syls;
        }

        if (word.endsWith("es") || word.endsWith("ed")) {
            int doubleAndtripple_1 = 0;
            Matcher matcher1 = pattern1.matcher(word);
            while (matcher1.find()) {
                doubleAndtripple_1++;
            }

            int theothercount = 0;
            Matcher matcher2 = pattern2.matcher(word);
            while (matcher2.find()){
                theothercount++;
            }
            if (doubleAndtripple_1 > 1 || theothercount > 1) {
                if (!(word.endsWith("ted") || word.endsWith("tes") || word.endsWith("ses") || word.endsWith("ied") || word.endsWith("ies"))) {
                    disc++;
                }
            }
        }

        String[] le_except = {"whole","mobile","pole","male","female","hale","pale","tale","sale","aisle","whale","while"};

        if (word.endsWith("e")) {
            if (word.endsWith("le") && !(Arrays.asList(le_except).contains(word))){
                // Doing nothing yo
            } else {
                disc += 1;
            }
        }

        int doubleAndtripple = 0;
        Matcher matcher3 = pattern1.matcher(word);
        while (matcher3.find()) {
            doubleAndtripple ++;
        }

        int tripple = 0;
        Matcher matcher4 = pattern3.matcher(word);
        while (matcher4.find()) {
            tripple++;
        }

        disc += doubleAndtripple+tripple;

        int numVowels = 0;
        Matcher matcher5 = pattern4.matcher(word);
        while (matcher5.find()) {
            numVowels++;
        }

        if (word.substring(0, 2) == "mc"){
            syls++;
        }

        String vowels = "aeoui";
        if (word.endsWith("y") && !vowels.contains(String.valueOf(word.charAt(word.length()-2)))) {
            syls++;
        }

        for (int i = 0; i < word.length(); i++) {
            char j = word.charAt(i);
            if (j == 'y') {
                if (i != 0 && i != word.length() - 1) {
                    if (!vowels.contains(String.valueOf(word.charAt(i-1))) && !vowels.contains(String.valueOf(word.charAt(i+1)))) {
                        syls ++;
                    }
                }
            }
        }

        if (word.substring(0,3) == "tri" && vowels.contains(String.valueOf(word.charAt(3)))) {
            syls ++;
        }

        if (word.substring(0,2) == "bi" && vowels.contains(String.valueOf(word.charAt(2)))) {
            syls ++;
        }

        if (word.endsWith("ian")) {
            if (!(word.endsWith("cian") || word.endsWith("tian"))) {
                syls++;
            }
        }

        if (word.startsWith("co") && vowels.contains(String.valueOf(word.charAt(2)))) {
            if (Arrays.asList(co_two).contains(word.substring(0, 4)) || Arrays.asList(co_two).contains(word.substring(0, 5)) || Arrays.asList(co_two).contains(word.substring(0, 6))){
                syls++;
            }
            if (!(Arrays.asList(co_one).contains(word.substring(0, 4)) || Arrays.asList(co_one).contains(word.substring(0, 5)) || Arrays.asList(co_one).contains(word.substring(0, 6)))) {
                syls++;
            }
        }

        if (word.startsWith("pre") && vowels.contains(String.valueOf(word.charAt(3)))) {
            if (!(Arrays.asList(pre_one).contains(word.substring(0,6)))) {
                syls++;
            }
        }

        String[] negatives = {"doesn't", "isn't", "shouldn't", "couldn't","wouldn't"};

        if (word.endsWith("n't")) {
            if (Arrays.asList(negatives).contains(word)) {
                syls++;
            }
        }

        if (Arrays.asList(exception_del).contains(word)) {
            disc++;
        }

        if (Arrays.asList(exception_add).contains(word)) {
            syls++;
        }

        return numVowels - disc + syls;
    }

    public int getSyllables(String line) {
        int count = 0;
        for (String word : cleanLine(line)) {
            if (word.length() <= 2) {
                count +=  1;
            } else if (mSyllables.get(word) != null) {
                count += mSyllables.get(word);
            } else {
                count += countSyllables(word);
            }
        }
        return count;
    }

    public int[] checkKu(String line1, String line2, String line3) {
        return new int[] {getSyllables(line1), getSyllables(line2), getSyllables(line3)};
    }

    public String[] cleanLine(String line){
        line = line.toLowerCase().replaceAll("[^a-z-' ]","");
        return line.split("\\s+");
    }
}
