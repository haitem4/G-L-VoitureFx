package ma.emsi.glvoiturefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ma.emsi.glvoiturefx.entities.Location;
import ma.emsi.glvoiturefx.services.LocationService;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class LocationController implements Initializable {
    private final LocationService locationService = new LocationService();
    public Button btnExportTXT;
    public Button btnImportTXT;

    private int id = 0;

    @FXML
    private TextField TCin;

    @FXML
    private TextField TMatricule;

    @FXML
    private DatePicker Date_Debut;

    @FXML
    private DatePicker Date_Fin;

    @FXML
    private TextField TPrixParJour;

    @FXML
    private TableView<Location> Table;

    @FXML
    private TableColumn<Location, Integer> colId;

    @FXML
    private TableColumn<Location, String> colCin;

    @FXML
    private TableColumn<Location, String> colMatricule;

    @FXML
    private TableColumn<Location, Date> colDateDebut;

    @FXML
    private TableColumn<Location, Date> colDateFin;

    @FXML
    private TableColumn<Location, Float> colPrixParJour;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnExportExcel;

    @FXML
    private Button btnImportExcel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLocations();
    }

    public ObservableList<Location> getLocation() {
        return FXCollections.observableArrayList(locationService.findAll());
    }

    public void showLocations() {
        Table.setItems(getLocation());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        colPrixParJour.setCellValueFactory(new PropertyValueFactory<>("prixParJour"));
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void deleteLocation(ActionEvent event) {
        Location location = locationService.find(id);
        if (location != null) {
            locationService.remove(location);
            showLocations();
            clear();
        }
    }

    @FXML
    void saveLocation(ActionEvent event) {
        Location location = new Location();
        location.setCin(TCin.getText());
        location.setMatricule(TMatricule.getText());
        location.setDateDebut(convertToDate(Date_Debut.getValue()));
        location.setDateFin(convertToDate(Date_Fin.getValue()));
        location.setPrixParJour(Float.parseFloat(TPrixParJour.getText()));
        locationService.save(location);
        showLocations();
        clear();
    }

    @FXML
    void updateLocation(ActionEvent event) {
        Location location = locationService.find(id);
        if (location != null) {
            location.setCin(TCin.getText());
            location.setMatricule(TMatricule.getText());
            location.setDateDebut(convertToDate(Date_Debut.getValue()));
            location.setDateFin(convertToDate(Date_Fin.getValue()));
            location.setPrixParJour(Float.parseFloat(TPrixParJour.getText()));
            locationService.update(location);
            showLocations();
            clear();
        }
    }

    @FXML
    void getData(MouseEvent event) {
        Location location = Table.getSelectionModel().getSelectedItem();
        if (location != null) {
            id = location.getId();
            TCin.setText(location.getCin());
            TMatricule.setText(location.getMatricule());
            Date_Debut.setValue(LocalDate.now());
            Date_Fin.setValue(LocalDate.now());
            TPrixParJour.setText(location.getPrixParJour()+"");
            btnSave.setDisable(true);
        }
    }

    void clear() {
        TCin.setText("");
        TMatricule.setText("");
        Date_Debut.setValue(null);
        Date_Fin.setValue(null);
        TPrixParJour.setText("");
        btnSave.setDisable(false);
    }

    private static java.util.Date convertToDate(LocalDate localDate) {
        if (localDate != null) {
            return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    private LocalDate convertToLocalDate(java.util.Date date) {
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    // Export to Excel

    @FXML
    void exportToExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            locationService.exportToExcelFile(file.getAbsolutePath());
        }
    }

    @FXML
    void importFromExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            locationService.importFromExcelFile(file.getAbsolutePath());
            showLocations();
        }
    }
    // Export to TXT
    public void exportDataToTxtFile() {
        try (FileOutputStream fout = new FileOutputStream("src/main/resources/exportdata.txt")) {
          List<Location> list =locationService.findAll();
            for (Location location : list) {
                fout.write(location.toString().getBytes());
                fout.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
    //Import to TXT

    public List<Location> importDataFromTxtFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.txt"));

        ArrayList<Location> list = new ArrayList<>();
        String readLine = br.readLine();

        while (readLine != null) {
            String[] vol = readLine.split(",");
            Location p = new Location();

            try {
                p.setId(Integer.parseInt(vol[0].trim()));
                p.setCin(vol[1].trim());
                p.setMatricule(vol[2].trim());
                p.setDateDebut(new SimpleDateFormat("dd/MM/yyyy").parse(vol[3]));
                p.setDateFin(new SimpleDateFormat("dd/MM/yyyy").parse(vol[4]));
                p.setPrixParJour(Float.parseFloat(vol[5].trim()));

                if (locationService.find(p.getId()) instanceof Location)
                    locationService.update(p);
                else
                    locationService.save(p);

            } catch (NumberFormatException | ParseException e) {
                System.out.println("Erreur lors de la conversion des données : " + e.getMessage());
            }

            readLine = br.readLine();
        }

        return list;
    }


}





