package sample;

public class Project {

    private String title;
    private boolean[] dept;
    private boolean[] types;
    private Professor professor;

    public Project(String title, boolean[] dept, boolean[] types, Professor professor) {
        this.title = title;
        this.dept = dept;
        this.types = types;
        this.professor=professor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean[] getDept() {
        return dept;
    }

    public void setDept(boolean[] dept) {
        this.dept = dept;
    }

    public boolean[] getTypes() {
        return types;
    }

    public void setTypes(boolean[] types) {
        this.types = types;
    }

    public Professor getProf() {
        return professor;
    }

    public void setProf(Professor professor) {
        this.professor = professor;
    }

    public void printProject(){
        System.out.print(title+" ");
        for(boolean value:dept)
            System.out.print(value+" ");

        for(boolean value:types)
            System.out.print(value+" ");

        System.out.println(professor.getName());

    }
}
