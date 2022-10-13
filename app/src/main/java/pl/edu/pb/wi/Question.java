package pl.edu.pb.wi;

public class Question {
    public int questionID;
    public boolean trueAnswer;
    public Question(int questionID,boolean trueAnswer)
    {
        this.questionID=questionID;
        this.trueAnswer=trueAnswer;
    }
    public boolean isTrueAnswer()
    {
        return trueAnswer;
    }
    public int getQuestionId()
    {
        return questionID;
    }
}
