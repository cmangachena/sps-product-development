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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson; 
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */

@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Query query = new Query("Result");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    ArrayList<String> resultsFinal = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String title = (String) entity.getProperty("title");
      String result = (String) entity.getProperty("result");
      resultsFinal.add(title);
      resultsFinal.add(result);
    }

    // Converts message to json
    Gson gson = new Gson();
   
    // Sets the value of what to return as response
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(resultsFinal));

  }
  

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

      System.out.println("HELLO WORLD ************************");
      System.out.println(request.getParameterMap());
      
      // define score variable
      int score = 0;

      // extract info from form and calculate score
      for(int i = 1; i <= 21; i++){ 
        String questionScore = request.getParameter("q" + i);
        int qInt = Integer.parseInt(questionScore);
        System.out.println("This is q1 " + qInt);
        score += qInt;
      }

      System.out.println("THIS IS YOUR SCORE " + score);
      String title = "Result";
      String result = "";
      // return results
      if(score >=0 && score <=10){
        result = "This is your score " + score + ". These ups and downs are considered normal";
      }
      else if(score >=11 && score <= 16){
        result =  "This is your score " + score + ". You are showing signs of mild mood disturbance";   
      }
      else if(score>=17 && score <= 20){
          result = "This is your score " + score + ". This shows you are experiencing bordeline clinical depression.";
      }
      else if(score>=21 && score <= 30){
          result = "This is your score " + score + ". This shows you are experiencing moderate clinical depression.";
      }
      else if(score>=31 && score <= 40){
          result = "This is your score " + score + ". This shows you are experiencing severe clinical depression. ";
      }
      else{
          result = "This is your score "+ score + ". This shows you are experiencing extreme clinical depression";
      }
      

      //Creates result entity
      Entity resultEntity = new Entity("Result");

      resultEntity.setProperty("title", title);
      resultEntity.setProperty("result", result);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(resultEntity);

      // Redirects to main page
      response.sendRedirect("/index.html");
  
  }
}

