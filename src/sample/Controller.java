package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {



    private ArrayList<CheckBox> typesChB = new ArrayList<>();

    private DBManager dbManager;
    private ExcelReader excelReader;
    @FXML TextField projectTitle;


    @FXML CheckBox softwareChB;
    @FXML CheckBox networksChB;
    @FXML CheckBox aiChB;


    @FXML CheckBox v1ChB;
    @FXML CheckBox v2ChB;
    @FXML CheckBox v3ChB;
    @FXML CheckBox v4ChB;
    @FXML CheckBox v5ChB;
    @FXML CheckBox v6ChB;
    @FXML CheckBox v7ChB;
    @FXML CheckBox v8ChB;
    @FXML CheckBox v9ChB;
    @FXML CheckBox v10ChB;
    @FXML CheckBox v11ChB;
    @FXML CheckBox v12ChB;
    @FXML CheckBox v13ChB;
    @FXML CheckBox v14ChB;


    @FXML ChoiceBox professorMenu;

    @FXML Button excelReadBut;
    @FXML TextField excelFilePathTf;
    @FXML Button but;
    @FXML ChoiceBox fileTypeMenu;

    public void init(){

        typesChB.add(v1ChB);
        typesChB.add(v2ChB);
        typesChB.add(v3ChB);
        typesChB.add(v4ChB);
        typesChB.add(v5ChB);
        typesChB.add(v6ChB);
        typesChB.add(v7ChB);
        typesChB.add(v8ChB);
        typesChB.add(v9ChB);
        typesChB.add(v10ChB);
        typesChB.add(v11ChB);
        typesChB.add(v12ChB);
        typesChB.add(v13ChB);
        typesChB.add(v14ChB);


        projectTitle.setDisable(false);
        softwareChB.setDisable(false);
        networksChB.setDisable(false);
        aiChB.setDisable(false);
        for(CheckBox cb : typesChB){
            cb.setDisable(false);
        }

        professorMenu.setDisable(false);
        excelFilePathTf.setDisable(false);
        excelReadBut.setDisable(false);
        fileTypeMenu.setDisable(false);
        String[] names = {"د.ناصرناصر","د.هلا نصار","د.محمد صبيح"};

        professorMenu.setItems(FXCollections.observableArrayList(names));
        fileTypeMenu.setItems(FXCollections.observableArrayList(ExcelReader.fileTypes));

        dbManager = new DBManager();

        but.setText("إضافة");
    }

    public void ButClicked(){

        if(but.getText().equals("تحضير")){

            init();

            return;

        }

        addProject();


    }


    public void addProject(){


        String title = "";
        boolean[] dept = new  boolean[3];
        boolean[] types = new boolean[14];
        String prof = "";

        title = projectTitle.getText();

        if(softwareChB.isSelected()) dept[0] = true;
        else dept[0] = false;

        if(networksChB.isSelected()) dept[1] = true;
        else dept[1] = false;

        if(aiChB.isSelected()) dept[2] = true;
        else dept[2] = false;


        for(int i = 0; i < typesChB.size(); i++){
            if(typesChB.get(i).isSelected()){
                types[i] = true;
            }else{
                types[i] = false;
            }
        }


        prof = professorMenu.getValue().toString();


        Project p = new Project(title,dept,types,prof);

        dbManager.addProject(p);

        clearAll();
    }


    public void addDataFromExcelFile(){

        excelReader = new ExcelReader(excelFilePathTf.getText(),fileTypeMenu.getValue().toString());

        if(!fileTypeMenu.getValue().toString().equals(ExcelReader.fileTypes[2])){
            ArrayList<Project> projects = excelReader.getProjects();

            for (Project p : projects){
                dbManager.addProject(p);
            }
        }else {

            ArrayList<Professor> professors = excelReader.getProfessors();

            for(Professor p : professors){
                dbManager.addProfessor(p);
            }


        }


    }

    private void clearAll(){
        projectTitle.setText("");
        softwareChB.setSelected(false);
        networksChB.setSelected(false);
        aiChB.setSelected(false);

        for(CheckBox cb : typesChB){
            cb.setSelected(false);
        }

        excelFilePathTf.setText("");
        professorMenu.setValue(null);
        fileTypeMenu.setValue(null);
    }
}
