package ict376.murdoch.edu.au.mobileappass2attempt2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;

public class GetTracks {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * used to get json from a server but now just get from strings.xml
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * this gets json from strings.xml
     * @param jsonstring
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray readJsonFromString(String jsonstring) throws IOException, JSONException {

        try {

            String jsonText = jsonstring;
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {

        }
    }
}
