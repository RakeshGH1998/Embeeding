import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/GloveVectorServlet1")
public class GloveVectorServlet1 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, double[]> gloveMap;
    private final String SUPABASE_URL = "https://aeykxgpowytpmtchxuei.supabase.co";
    private final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFleWt4Z3Bvd3l0cG10Y2h4dWVpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU4Mzg3MTgsImV4cCI6MjAzMTQxNDcxOH0.mbxUgFGzObLSkNl2Dcc_VKQ6Hi_V7CdbnDj_2alSXts";

    @Override
    public void init() throws ServletException {
        String gloveFilePath = getServletContext().getRealPath("/WEB-INF/glove.6B.100d.txt");
        try {
            gloveMap = new HashMap<>();
            loadGloveData(gloveFilePath);
        } catch (IOException e) {
            throw new ServletException("Error loading GloVe data", e);
        }
    }

    private void loadGloveData(String gloveFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(gloveFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                String word = tokens[0];
                double[] vector = new double[tokens.length - 1];
                for (int i = 1; i < tokens.length; i++) {
                    vector[i - 1] = Double.parseDouble(tokens[i]);
                }
                gloveMap.put(word, vector);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String originalText = request.getParameter("text");
        String lowerText = originalText.toLowerCase();  // Convert the entire text to lowercase
        String[] words = lowerText.split("\\s+");
        String[] originalWords = originalText.split("\\s+");  // Split the original text as well

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().append("<html><body>");
        
        boolean foundAny = false;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            double[] vector = gloveMap.get(word);
            if (vector != null) {
                foundAny = true;
                insertVectorData(originalText, originalWords[i], vector);  // Store the original word
            }
        }

        if (foundAny) {
            response.getWriter().append("<p>Data stored successfully.</p>");
        } else {
            response.getWriter().append("<p>No valid words found in the GloVe dataset for the given sentence.</p>");
        }
        
        response.getWriter().append("</body></html>");
    }


    private void insertVectorData(String text, String word, double[] vector) throws IOException {
        URL url = new URL(SUPABASE_URL + "/rest/v1/vectors_data");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("apikey", SUPABASE_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Prefer", "return=representation");
        conn.setDoOutput(true);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        jsonObject.addProperty("word", word);
        jsonObject.add("vector", gson.toJsonTree(vector, double[].class));

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code != 201) {
            throw new IOException("Error inserting data into Supabase: " + code);
        }
    }
}
