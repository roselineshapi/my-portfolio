// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

// import com.google.sps.data.ServerStats;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.sps.data.Comments;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Comments").addSort("timestamp", SortDirection.DESCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        List<Comments> comment = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            String message = (String) entity.getProperty("message");
            long timestamp = (long) entity.getProperty("timestamp"); 
            double score = (double) entity.getProperty("score");   
            Comments userComment = new Comments(message, timestamp, score);
            comment.add(userComment);
        }

        Gson gson = new Gson();
        String Json = gson.toJson(comment);
        response.setContentType("application/json;");
        response.getWriter().println(Json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get user input from the form
        String message = request.getParameter("message");
        long timestamp = System.currentTimeMillis();
        
        //calculate thesentiment of the message entered
        Document doc =
            Document.newBuilder().setContent(message).setType(Document.Type.PLAIN_TEXT).build();
        LanguageServiceClient languageService = LanguageServiceClient.create();
        Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
        double score = (double)sentiment.getScore();
        languageService.close();
        
        
        Entity taskEntity = new Entity("Comments");
        taskEntity.setProperty("score", score);
        taskEntity.setProperty("message",message);
        taskEntity.setProperty("timestamp", timestamp);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity); 
        
        //show sentiment score to the user
        response.setContentType("text/html;");
        response.getWriter().println("<h1>Sentiment Analysis</h1>");
        response.getWriter().println("<p>You entered: " + message + "</p>");
        response.getWriter().println("<p>Sentiment analysis score: " + score + "</p>");
        response.getWriter().println("<p><a href=\"/\">Back</a></p>");

       
    }
}




