import javafx.scene.control.Alert;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility {
    public static String getPassword() {
        String password;
        try {
            BufferedReader brTest = new BufferedReader(new FileReader("password.txt"));
            password = brTest.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    public static double checkerDouble(String str) {
        double number;
        try {
            number = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            number = -1;
        }
        return number;
    }

    public static void message(String titel,String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
