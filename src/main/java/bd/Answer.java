package bd;

public class Answer {
    protected int number;
    protected String title;
    protected int weight;

    public Answer(int number, String title, int weight){
        this.number=number;
        this.title=title;
        this.weight = weight;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
