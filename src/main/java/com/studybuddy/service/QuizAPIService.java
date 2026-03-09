package com.studybuddy.service;

import com.studybuddy.model.Question;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizAPIService {

    private static int amount = 5;
    private static String difficulty = "easy";
    private static int category = 18;

    public static void setQuizConfig(int a,String subject,String d){

        amount = a;
        difficulty = d;

        switch(subject){

            case "Computer Science":
                category = 18;
                break;

            case "Mathematics":
                category = 19;
                break;

            case "Science":
                category = 17;
                break;

            case "History":
                category = 23;
                break;

            case "General Knowledge":
                category = 9;
                break;

            default:
                category = 18;
        }
    }

    public List<Question> fetchQuestions(){

        List<Question> questions = new ArrayList<>();

        try{

            String url = "https://opentdb.com/api.php?amount="
                    + amount
                    + "&category=" + category
                    + "&difficulty=" + difficulty
                    + "&type=multiple";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request,HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            JSONArray results = json.getJSONArray("results");

            for(int i=0;i<results.length();i++){

                JSONObject obj = results.getJSONObject(i);

                String question = obj.getString("question");
                String correct = obj.getString("correct_answer");

                JSONArray incorrect = obj.getJSONArray("incorrect_answers");

                List<String> options = new ArrayList<>();

                options.add(correct);

                for(int j=0;j<incorrect.length();j++){
                    options.add(incorrect.getString(j));
                }

                Collections.shuffle(options);

                questions.add(new Question(question,correct,options));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return questions;
    }
}