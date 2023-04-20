import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Fad;

import java.sql.*;
import java.time.LocalDate;

public class Start extends Application {
    Connection database = Database.getConnection(Utility.getPassword());

    public void start(Stage stage) {
        stage.setTitle("DAOS Sall Whisky");
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    DatePicker dpDato = new DatePicker(LocalDate.now());
    TextField txfMaltBatch = new TextField();
    TextField txfKornsort = new TextField();
    TextField txfMedarbejder = new TextField();
    TextField txfKapacitetILiter = new TextField();
    TextField txfAlkoholsProcent = new TextField();
    TextField txfRygemateriale = new TextField();
    TextArea txaKommentar = new TextArea();
    Button btnOpretDestillering = new Button("Opret destillering");
    ListView<String> lvwMedarbejder = new ListView<>();
    Button btnMedarbejder = new Button("Vis statistik for medarbejde");
    ListView<Fad> lvwFade = new ListView<>();
    ComboBox<String> cbLager = new ComboBox<>();
    ComboBox<String> cbReol = new ComboBox<>();
    ComboBox<String> cbHylde = new ComboBox<>();
    ComboBox<String> cbPlads = new ComboBox<>();
    Button btnPlads = new Button("Tilføj fad til plads");

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        //Destillering
        Label lblDestillering = new Label("A) Lav destillering:");
        lblDestillering.setStyle("-fx-font-size: 20;");
        pane.add(lblDestillering, 0, 0, 2, 1);
        GridPane.setHalignment(lblDestillering, HPos.CENTER);

        //Dato
        pane.add(new Label("Dato:"), 0, 1);
        pane.add(dpDato, 1, 1);
        dpDato.setPrefWidth(200);

        //Malt Batch
        pane.add(new Label("Malt batch:"), 0, 2);
        pane.add(txfMaltBatch, 1, 2);

        //Kornsort
        pane.add(new Label("Kornsort:"), 0, 3);
        pane.add(txfKornsort, 1, 3);

        //Medarbejder
        pane.add(new Label("Medarbejder:"), 0, 4);
        pane.add(txfMedarbejder, 1, 4);

        //Kapacitet I Liter
        pane.add(new Label("Kapacitet i liter: "), 0, 5);
        pane.add(txfKapacitetILiter, 1, 5);

        //Alkohols Procent
        pane.add(new Label("Alkohols procent:"), 0, 6);
        pane.add(txfAlkoholsProcent, 1, 6);

        //Rygemateriale
        pane.add(new Label("Rygemateriale:"), 0, 7);
        pane.add(txfRygemateriale, 1, 7);

        //Kommentar
        pane.add(new Label("Kommentar:"), 0, 8);
        pane.add(txaKommentar, 1, 8);
        txaKommentar.setPrefWidth(200);
        txaKommentar.setPrefHeight(100);

        //Opret destillering knap
        pane.add(btnOpretDestillering, 0, 9, 2, 1);
        GridPane.setHalignment(btnOpretDestillering, HPos.CENTER);
        btnOpretDestillering.setPrefWidth(304.5);
        btnOpretDestillering.setOnAction(actionEvent -> this.btnDestilleringAction());


        //Medarbejder label
        Label lblMedarbejder = new Label("B) Find medarbejder:");
        lblMedarbejder.setStyle("-fx-font-size: 20;");
        pane.add(lblMedarbejder, 2, 0);
        GridPane.setHalignment(lblMedarbejder, HPos.CENTER);

        //Medarbejder listview
        pane.add(lvwMedarbejder, 2, 1, 1, 8);
        lvwMedarbejder.setPrefWidth(200);

        //Vis statistik knap
        pane.add(btnMedarbejder, 2, 9);
        btnMedarbejder.setPrefWidth(200);
        btnMedarbejder.setOnAction(actionEvent -> this.btnMedarbejderAction());

        //Fad label
        Label lblFad = new Label("C) Fad på plads:");
        lblFad.setStyle("-fx-font-size: 20;");
        pane.add(lblFad, 3, 0);
        GridPane.setHalignment(lblFad, HPos.CENTER);

        //Fad listview
        pane.add(lvwFade, 3, 1, 1, 7);
        lvwFade.setPrefWidth(200);
        lvwFade.setPrefHeight(200);


        //ComboBoxes - Lager, Reol Hylde og Plads
        VBox cbVBox = new VBox(cbLager, cbReol, cbHylde, cbPlads);
        pane.add(cbVBox, 3, 8, 1, 4);
        cbVBox.setSpacing(10);


        cbLager.setPrefWidth(200);
        cbLager.setPromptText("Lager");
        cbLager.setOnAction(actionEvent -> this.lagerComboBoxAction());

        cbReol.setPrefWidth(200);
        cbReol.setPromptText("Reol");
        cbReol.setDisable(true);
        cbReol.setOnAction(actionEvent -> this.reolComboBoxAction());
        cbReol.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Reol");
                } else {
                    setText(item);
                }
            }
        });

        cbHylde.setPrefWidth(200);
        cbHylde.setPromptText("Hylde");
        cbHylde.setDisable(true);
        cbHylde.setOnAction(actionEvent -> this.hyldeComboBoxAction());

        cbHylde.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Hylde");
                } else {
                    setText(item);
                }
            }
        });

        cbPlads.setPrefWidth(200);
        cbPlads.setPromptText("Plads");
        cbPlads.setDisable(true);

        cbPlads.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Plads");
                } else {
                    setText(item);
                }
            }
        });

        pane.add(btnPlads, 3, 9);
        btnPlads.setPrefWidth(200);
        btnPlads.setOnAction(actionEvent -> this.fadTilPladsAction());


        updateControls();
    }

    private void updateControls() {
        lvwMedarbejder.getItems().clear();
        try {
            Statement stmt = database.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT medarbejder FROM Destillering GROUP BY medarbejder");
            while (rs.next()) {
                lvwMedarbejder.getItems().add(rs.getString("medarbejder"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lvwFade.getItems().clear();
        try {
            Statement stmt = database.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Fad");
            while (rs.next()) {
                lvwFade.getItems().add(new Fad(rs.getInt("fadNummer"), rs.getString("fadType"), rs.getDouble("kapacitetILiter"), rs.getString("oprindelse")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbLager.getItems().clear();
        try {
            Statement stmt = database.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT adresse FROM Lager");
            while (rs.next()) {
                cbLager.getItems().add(rs.getString("adresse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opgave A
    private void btnDestilleringAction() {
        double kapacitet = Utility.checkerDouble(txfKapacitetILiter.getText());
        double alkoholsProcent = Utility.checkerDouble(txfAlkoholsProcent.getText());

        if (kapacitet < 0) {
            Utility.message("Error", "Fail in kapacitet", "Input valid kapacitet");
        } else if (alkoholsProcent < 0) {
            Utility.message("Error", "Fail in alkohols procent", "Input valid alkohols procent");
        } else {
            try {
                String query = String.format("'%s', '%s', '%s', '%s', %s, %s, '%s', '%s'", dpDato.getValue(), txfMaltBatch.getText(), txfKornsort.getText(), txfMedarbejder.getText(), kapacitet, alkoholsProcent, txfRygemateriale.getText(), txaKommentar.getText());
                Statement stmt = database.createStatement();
                int rows = stmt.executeUpdate("INSERT INTO Destillering (dato, maltBatch, kornsort, medarbejder, kapacitet, alkoholsprocent, rygemateriale, kommentar) VALUES (" + query + ")");
                Utility.message("Success", "Destilleringen has been added", rows + " rows was added");
            } catch (SQLException e) {
                switch (e.getErrorCode()) {
                    case 241 -> Utility.message("Error", "Error code: " + e.getErrorCode(), "Error pick a valid date!");
                    case 547 ->
                            Utility.message("Error", "Error code: " + e.getErrorCode(), "Error in alkohols procent must be between 0 and 100!");
                    default -> Utility.message("Error", "Error code: " + e.getErrorCode(), e.getMessage());
                }
            }
        }
    }

    // Opgave B
    private void btnMedarbejderAction() {
        String medarbejder = lvwMedarbejder.getSelectionModel().getSelectedItem();
        if (medarbejder != null) {
            try {
                Statement stmt = database.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM AlleMedarbejdere WHERE medarbejder = '" + medarbejder + "'");
                while (rs.next()) {
                    Utility.message("Medarbejder info", "Statistik for " + rs.getString("medarbejder") + ":", "Medarbejderen har lavet " + rs.getInt("antalDestilleringer") + " destilleringer og destilleret " + rs.getInt("antalLiterDestilleret") + " liter!");
                }
            } catch (SQLException e) {
                Utility.message("Error", "Error code: " + e.getErrorCode() + "", e.getMessage());
            }
        } else {
            Utility.message("Error", "Selected medarbejder", "Select valid medarbejder");
        }
    }

    //C
    private void lagerComboBoxAction() {
        cbReol.getItems().clear();
        cbHylde.getItems().clear();
        cbPlads.getItems().clear();
        if (cbLager.getSelectionModel().getSelectedItem() != null) {
            cbReol.setDisable(false);
            cbHylde.setDisable(true);
            cbPlads.setDisable(true);

            String adresse = cbLager.getSelectionModel().getSelectedItem();
            try {
                Statement stmt = database.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT reol FROM AlleLagerPladser WHERE adresse = '" + adresse + "'");
                while (rs.next()) {
                    cbReol.getItems().add(rs.getString("reol"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            cbReol.setDisable(true);
            cbHylde.setDisable(true);
            cbPlads.setDisable(true);
        }
    }

    private void reolComboBoxAction() {
        cbHylde.getItems().clear();
        cbPlads.getItems().clear();
        if (cbLager.getSelectionModel().getSelectedItem() != null) {
            cbHylde.setDisable(false);
            cbPlads.setDisable(true);

            String adresse = cbLager.getSelectionModel().getSelectedItem();
            String reol = cbReol.getSelectionModel().getSelectedItem();
            try {
                Statement stmt = database.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT hylde FROM AlleLagerPladser WHERE adresse = '" + adresse + "' AND reol = '" + reol + "'");
                while (rs.next()) {
                    cbHylde.getItems().add(rs.getString("hylde"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            cbHylde.setDisable(true);
            cbPlads.setDisable(true);
        }
    }

    public void hyldeComboBoxAction() {
        cbPlads.getItems().clear();
        if (cbLager.getSelectionModel().getSelectedItem() != null) {
            cbPlads.setDisable(false);

            String adresse = cbLager.getSelectionModel().getSelectedItem();
            String reol = cbReol.getSelectionModel().getSelectedItem();
            String hylde = cbHylde.getSelectionModel().getSelectedItem();
            try {
                Statement stmt = database.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT plads FROM AlleLagerPladser WHERE adresse = '" + adresse + "' AND reol = '" + reol + "' and hylde= '" + hylde + "'");
                while (rs.next()) {
                    cbPlads.getItems().add(rs.getString("plads"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            cbPlads.setDisable(true);
        }
    }

    public void fadTilPladsAction() {
        if (lvwFade.getSelectionModel().getSelectedItem() != null) {
            String adresse = cbLager.getSelectionModel().getSelectedItem();
            String reol = cbReol.getSelectionModel().getSelectedItem();
            String hylde = cbHylde.getSelectionModel().getSelectedItem();
            String plads = cbPlads.getSelectionModel().getSelectedItem();

            if (adresse == null) {
                Utility.message("Error", "Selected lager", "Please select a valid lager");
            } else if (reol == null) {
                Utility.message("Error", "Selected reol", "Please select a valid reol");
            } else if (hylde == null) {
                Utility.message("Error", "Selected hylde", "Please select a valid hylde");
            } else if (plads == null) {
                Utility.message("Error", "Selected plads", "Please select a valid plads");
            } else {
                try {

                    Statement stmt = database.createStatement();
                    String selectQuery = String.format("SELECT lagerPladsId FROM LagerPlads WHERE reol = '%s' AND hylde = '%s' AND plads = '%s'", reol, hylde, plads);
                    ResultSet rs = stmt.executeQuery(selectQuery);
                    int lagerPladsId = 0;
                    while (rs.next()) {
                        lagerPladsId = rs.getInt("lagerPladsId");
                    }

                    Fad fad = lvwFade.getSelectionModel().getSelectedItem();
                    String updateQuery = String.format("UPDATE Fad SET lagerPladsId = %s WHERE Fad.fadNummer = %s", lagerPladsId, fad.getFadID());
                    stmt.executeUpdate(updateQuery);

                    Utility.message("Success", "Fadet has been added to lagerPladsId: " + lagerPladsId, "Fadet was updated");
                } catch (SQLException e) {
                    Utility.message("Error", "Error code: " + e.getErrorCode() + "", e.getMessage());
                }
            }
        } else {
            Utility.message("Error", "Selected fad", "Please select a valid fad");
        }
    }
}
