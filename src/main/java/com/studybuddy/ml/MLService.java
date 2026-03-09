package com.studybuddy.ml;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MLService {

    public static String predict(
            int studytime,
            int failures,
            int absences,
            int difficulty,
            int studyHours
    ){

        try{

     ProcessBuilder pb = new ProcessBuilder(
        "python",
        "src/main/java/com/studybuddy/ml/predict.py",
        String.valueOf(studytime),
        String.valueOf(failures),
        String.valueOf(absences),
        String.valueOf(difficulty),
        String.valueOf(studyHours)
);

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String result = reader.readLine();

            if(result == null || result.trim().isEmpty()){
                return "ML model returned no prediction";
            }

            return result;

        }catch(Exception e){
            e.printStackTrace();
        }

        return "Prediction failed";
    }

}