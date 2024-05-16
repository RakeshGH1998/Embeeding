# Embeeding

__Overview__

GloveVectorServlet1 is a Java servlet that reads GloVe word embeddings and stores vector data for words found in user-provided text into a Supabase database. This project demonstrates how to load pre-trained GloVe vectors, process text input from a client, and insert relevant word vectors into a database using a RESTful API.


__Features__

* Load GloVe Data: Reads and stores GloVe word vectors from a specified file.
  
* Text Processing: Converts input text to lowercase and extracts individual words.
  
* Database Storage: Stores the original text, the identified words, and their corresponding vectors in a Supabase database.

* HTTP Servlet: Handles POST requests, processes text, and returns success or error messages to the client.


__Dependencies__

* Java Servlet API
  
* GloVe pre-trained word vectors
  
* Gson for JSON processing
  
* Supabase for database storage


__Setup and Configuration__

__Prerequisites__

* Java Development Kit (JDK) 8 or higher
  
* Apache Tomcat or any servlet container
  
* Supabase account and project


  __GloVe Data__
  
Ensure you have the GloVe file glove.6B.100d.txt. This file should be placed in the WEB-INF directory of your web application.


__Supabase Configuration__

You need a Supabase project URL and an API key. These should be set in the servlet code:

__private final String SUPABASE_URL = "https://your-supabase-url";__
__private final String SUPABASE_KEY = "your-supabase-api-key";__


__Code Explanation__

__Initialization__
In the init method, the servlet loads GloVe data from a specified file into a HashMap where the key is the word and the value is the vector.

__Text Processing__
In the doPost method, the servlet:

__Receives text input.__
Converts the text to lowercase and splits it into words.
Checks each word against the loaded GloVe data.
Stores the original text, word, and vector data in Supabase for words that exist in the GloVe dataset.


__Data Insertion__
The insertVectorData method sends a POST request to the Supabase REST API to store the vector data. It uses HttpURLConnection to handle the HTTP request and response.
