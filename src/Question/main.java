
package Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;


public class main extends Answer{
    public main(int label, String value) {
        super(label, value);
    }
    // Random question
    public static JSONObject chooseQuestion(JSONArray arr){
        Random rand = new Random();
        JSONObject obj = (JSONObject) arr.get(rand.nextInt(arr.size()));
        return obj;
    }
    //List EN from file
    public static ArrayList  listENAnswer(JSONArray arr){
        ArrayList<String> EN = new ArrayList<String>();
        for (int i=0; i< arr.size(); i++){
            JSONObject obj = (JSONObject) arr.get(i);
            EN.add(obj.get("english").toString());
        }
        return EN;


    }

    //Print Question
    public static void printQuestion(JSONObject obj){
        System.out.println(String.format("what is the meaning word in english \"%s\"",  obj.get("vietnamese")));
    }
    //get Answer
    public static Answer getAnswer(JSONObject obj){
        Answer answer1 = new Answer(1,  obj.get("english").toString());
        return answer1;
    }
    //Set Answer
    public static ArrayList<String> setAnswer(JSONArray arr,JSONObject obj){
        printQuestion(obj);
        ArrayList<String> answer = new ArrayList<>();
        answer.add(getAnswer(obj).value);
        Random rand = new Random();
        while ( answer.size()<4){
            int index = rand.nextInt(listENAnswer(arr).size());
            String key = (String) listENAnswer(arr).get(index);
            if(!answer.contains(key)){
                answer.add(key);
            }
        }
        return answer;
    }
    // Random Result
    public static List<Answer> randomAnswer(ArrayList<String> arr){
        Collections.shuffle(arr);
        Answer answer1 = new Answer(1,arr.get(0).toString());
        List<Answer> answerList =  new ArrayList<Answer>();
        answerList.add(answer1);
        for (int i=1; i< arr.size(); i++){
            answer1 = new Answer(i+1,arr.get(i));
            answerList.add(answer1);
        }
        return answerList;
    }

    // Print Random Result
    public static void printRandomAnswer(List<Answer> answerls){
        for ( int i =0 ; i< answerls.size(); i++){
            System.out.println(answerls.get(i).label+":" + answerls.get(i).value);
        }
    }
    //check Answer
    public static void checkAnswer(int inputValue,JSONArray arr,List<Answer>aws,JSONObject obj){
        for ( int i=0; i<aws.size(); i++) {
            if (inputValue == aws.get(i).label) {
                if (aws.get(i).value.equals(getAnswer(obj).value)) {
                    System.out.println("correct");
                } else {
                    System.out.println("wrong");
                }
            }
        }
    }

    public static void main(String[] args) {
            Object obj;
            Scanner in = new Scanner(System.in);
            try {
                obj = new JSONParser().parse(new FileReader("../Question/src/Question/data.json"));
                JSONArray data = (JSONArray) obj;
                JSONObject object = chooseQuestion(data);
                ArrayList<String> answerArrayList = setAnswer(data,object);

                printRandomAnswer(randomAnswer(answerArrayList));
                System.out.println("Nhap cau tra loi: [1], [2], [3]");
                int n = in.nextInt();
                checkAnswer(n,data,randomAnswer(answerArrayList),object);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

    }
}
