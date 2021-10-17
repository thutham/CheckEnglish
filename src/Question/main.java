
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
    public static List<Answer> setAnswer(JSONArray arr,JSONObject obj){
        List<Answer> answerList = new ArrayList<Answer>();
        printQuestion(obj);
        answerList.add(getAnswer(obj));
        ArrayList<String> answer = listENAnswer(arr);
        answer.remove(getAnswer(obj).value);
        Random rand = new Random();
        for ( int i =0 ; i< 2; i++){
            for (int j =0; j< answer.size(); j++){
            String nextAnswer = answer.get(j);
            Answer answer2 = new Answer(i+2,  nextAnswer.toString());
            answerList.add(answer2);
            answer.remove(nextAnswer);}
        }
        return answerList;
    }
    // Random Result
    public static List<Answer> randomAnswer(List<Answer> answerls){
        ArrayList<String>answer = new ArrayList<>();
        Random rand = new Random();
        List<Answer> newAnswerList =  new ArrayList<Answer>();
        for ( int i =0 ; i< answerls.size(); i++){
           answer.add( answerls.get(i).value) ;
        }

        for ( int i =0 ; i< answerls.size(); i++){
            int randoindex = rand.nextInt(answer.size());
            answerls.get(i).setValue(answer.get(randoindex));
            answer.remove(answer.get(randoindex));
        }
        return answerls;
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
                List<Answer> answerList = randomAnswer(setAnswer(data,object));
                randomAnswer(answerList);

                printRandomAnswer(answerList);
                System.out.println("Nhap cau tra loi: [1], [2], [3]");
                int n = in.nextInt();
                checkAnswer(n,data,answerList,object);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

    }
}
