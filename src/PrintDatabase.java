import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class PrintDatabase {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost/semProject?user=root&password=5314");

            ResultSet resultSet = connection.createStatement().executeQuery("select * from dataset");
            while(resultSet.next()){
                for(int i=4;i<=17;i++) {
                    System.out.print(resultSet.getString(i)+" ");
                }
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
