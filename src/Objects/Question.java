package Objects;

/**
 * Creates a question object that is used as an universal
 */
public class Question {
    boolean correct;
    int type;
    int randInt;
    String answer;

    /**
     * Getter and setter methods for the question object.
     */
    public void setCorrect(boolean correct){
        this.correct = correct;
    }
    public boolean getCorrect(){
        return correct;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
    public void setRandInt(int randInt) {
        this.randInt = randInt;
    }
    public int getRandInt(){
        return randInt;
   }
    public void setAnswer(String answer){
        this.answer = answer;
   }
    public String getAnswer(){
        return answer;
   }
}
