package ma.emsi.glvoiturefx.services;

import ma.emsi.glvoiturefx.dao.ILocation;
import ma.emsi.glvoiturefx.dao.ILocationImpl;
import ma.emsi.glvoiturefx.entities.Location;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LocationService {
    private ILocation locationDao = new ILocationImpl();

    public List<Location> findAll() {
        return locationDao.findAll();
    }

    public Location find(int id) {
        return locationDao.find(id);
    }

    public void save(Location location) {
        locationDao.insert(location);
    }

    public void update(Location location) {
        locationDao.update(location);
    }

    public void remove(Location location) {
        locationDao.delete(location.getId());
    }

    public void importFromExcelFile(String file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0;
            for (Row row : sheet) {
                if (i != 0)
                {
                    Location location = new Location();
                    location.setId((int) row.getCell(0).getNumericCellValue());
                    location.setCin(row.getCell(1).getStringCellValue());
                    location.setMatricule(row.getCell(2).getStringCellValue());

                    DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
                    Date date = formatter.parse(row.getCell(3).getStringCellValue());
                    location.setDateDebut(date);

                    date = formatter.parse(row.getCell(4).getStringCellValue());
                    location.setDateDebut(date);

                    location.setPrixParJour((float) row.getCell(5).getNumericCellValue());

                    if (this.find(location.getId()) instanceof Location)
                        this.update(location);
                    else
                        this.save(location);
                }
                i=1;
            }
            workbook.close();
            inputStream.close();
            System.out.println("Locations Data Imported Successfully From "+ file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private Location createLocationFromRow(Row row) {
        try {
            int id = (int) row.getCell(0).getNumericCellValue();
            String cin = row.getCell(1).getStringCellValue();
            String matricule = row.getCell(2).getStringCellValue();
            java.util.Date dateDebut = row.getCell(3).getDateCellValue();
            java.util.Date dateFin = row.getCell(4).getDateCellValue();
            float prixParJour = (float) row.getCell(5).getNumericCellValue();

            return new Location(id, cin, matricule, dateDebut, dateFin, prixParJour);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public void exportToExcelFile(String file) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Sheet");

        XSSFRow header = spreadsheet.createRow(0);
        header.createCell(0).setCellValue("Location ID");
        header.createCell(1).setCellValue("CIN");
        header.createCell(2).setCellValue("Matricule");
        header.createCell(3).setCellValue("Date_Debut");
        header.createCell(4).setCellValue("Date_Fin");
        header.createCell(5).setCellValue("PrixParJour");

        int rowNumber = 1;
        for (Location location : this.findAll()) {
            XSSFRow row = spreadsheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(location.getId());
            row.createCell(1).setCellValue(location.getCin());
            row.createCell(2).setCellValue(location.getMatricule());
            row.createCell(3).setCellValue(location.getDateDebut().toString());
            row.createCell(4).setCellValue(location.getDateFin().toString());
            row.createCell(5).setCellValue(location.getPrixParJour());
        }

        try (FileOutputStream out = new FileOutputStream(new File(file))) {
            workbook.write(out);
            System.out.println("Locations Data Exported Successfully To " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
