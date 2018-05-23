package sample;

public class Professor {

    int id , dept;
    String name;

    public Professor(int id, String name, int dept) {
        this.id = id;
        this.dept = dept;
        this.name = name;
    }

    public Professor(){

    }

    public Professor(Professor professor){
        this.id=professor.getId();
        this.dept=professor.getDept();
        this.name=professor.getName();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
