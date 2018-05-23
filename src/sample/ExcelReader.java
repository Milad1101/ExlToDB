package sample;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    ArrayList<Project> projects;
    ArrayList<Professor> professors;
    File file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    public static String[] fileTypes = new String[]{"ملف الاستبيان" , "ملف يوسف","ملف اسماء المشرفين"};




    public ExcelReader(String path , String fileType){

        projects = new ArrayList<>();
        professors = new ArrayList<>();
        file = new File(path);
        init();




        if(fileType.equals(fileTypes[1]))
            readYoussefFile();
        else if(fileType.equals(fileTypes[0]))
            readFile();
        else if(fileType.equals(fileTypes[2]))
            readProfessorsFile();

    }



    private void readProfessorsFile() {

        int id  = 0, dept = 0;
        String name;

        XSSFRow row;
        XSSFCell cell;
        int lastRowNum  = sheet.getLastRowNum();

        for(int i =1 ; i <= lastRowNum ; i++){
            name = "";

            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for(int j = 0 ; j < lastCellNum;j++){
                cell = row.getCell(j);

                if(j == 0) id =(int) cell.getNumericCellValue();

                if(j == 1) name = cell.getStringCellValue();

                if(j == 2)  dept =(int) cell.getNumericCellValue();

            }
            professors.add(new Professor(id,name,dept));

        }





    }





    private void readFile(){

        String title = "";
        boolean[] dept = new  boolean[3];
        boolean[] types = new boolean[14];
        String prof = "";

        XSSFRow row;
        XSSFCell cell;
        int lastRowNum  = sheet.getLastRowNum();

        for(int i =1 ; i <= lastRowNum ; i++){

            title = "";
            dept = new  boolean[3];
            types = new boolean[14];
            prof = "";
            Professor professor = new Professor();

            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for(int j = 0 ; j < lastCellNum;j++){
                cell = row.getCell(j);

                if(j == 0) title = cell.getStringCellValue();

                if(j == 1) fillDept(dept,cell.getStringCellValue().split(","));

                if(j == 2) fillTypes(types,cell.getStringCellValue().split(","));

                if(j == 3){ prof = cell.getStringCellValue();
                            professor.setName(prof);

                }
            }

            projects.add(new Project(title,dept,types,professor));

        }




    }


    private void readYoussefFile(){

        String title = "";
        boolean[] dept = new  boolean[3];
        boolean[] types = new boolean[14];
        String prof = "";

        XSSFRow row;
        XSSFCell cell;
        int lastRowNum  = sheet.getLastRowNum();


        for(int i =1 ; i <= lastRowNum ; i++){

            title = "";
            dept = new  boolean[3];
            types = new boolean[14];
            Professor professor = new Professor();
            prof = "";

            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for(int j = 0 ; j < lastCellNum;j++) {
                cell = row.getCell(j);
                if (cell == null) continue;
                if (j == 0) title = cell.getStringCellValue();

                if (j > 0 && j < 15) {

                    if (cell.getStringCellValue().replaceAll(" ", "").equals("*")) {
                        types[j - 1] = true;
                    } else {
                        types[j - 1] = false;
                    }

                }

                if (j > 14 && j < 18) {
                    if (cell.getStringCellValue().replaceAll(" ", "").equals("*")) {
                        dept[j - 15] = true;
                    } else {
                        dept[j - 15] = false;
                    }
                }


                if (j == 18){ prof = cell.getStringCellValue();
                professor.setName(prof);
                }
            }
            projects.add(new Project(title,dept,types,professor));

        }


    }

    private void fillTypes(boolean[] types, String[] values){

        for (String s : values) {
            for (int i = 0; i < types.length; i++) {

                String [] arr = s.split(" ");
                s ="" ;
                for (String ss : arr){
                    ss = ss.replaceAll(" ","");

                    if(!ss.equals("")){
                        if(s.equals("")){
                            s = ss;
                        }
                        else{
                            s += " "+ss;
                        }
                    }
                }

                 if (s.equals(DBManager.projectTypes[i])) {
                    types[i] = true;
                    break;
                }
            }
        }


    }







    private void fillDept(boolean[] dept,String[] values){

        for(String s : values){
            String [] arr = s.split(" ");
            s ="" ;
            for (String ss : arr){
                ss = ss.replaceAll(" ","");

                if(!ss.equals("")){
                    if(s.equals("")){
                        s = ss;
                    }
                    else{
                        s += " "+ss;
                    }
                }
            }


            if(s.equals("برمجيات"))
                dept[0] = true;


            if(s.equals("شبكات"))
                dept[1] = true;


            if(s.equals("ذكاء صنعي"))
                dept[2] = true;
        }

    }





    private void init(){

        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public ArrayList<Project> getProjects() {
        return projects;
    }


    public ArrayList<Professor> getProfessors() {
        return professors;
    }
}
