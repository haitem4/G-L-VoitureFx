package ma.emsi.glvoiturefx.dao;

import ma.emsi.glvoiturefx.entities.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ILocationImpl implements ILocation {
    private final Connection conn;

    public ILocationImpl() {
        conn = DB.getConnection();
    }

    @Override
    public void insert(Location location) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO location (cin,matricule,date_debut,date_fin,prixparjour) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, location.getCin());
            ps.setString(2, location.getMatricule());
            ps.setDate(3, new Date(location.getDateDebut().getTime()));
            ps.setDate(4, new Date(location.getDateDebut().getTime()));
            ps.setFloat(5, location.getPrixParJour());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        location.setId(id);
                    }
                }
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'une nouvelle location : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Location location) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE location SET cin=?, matricule=?, date_debut=?, date_fin=?, prixparjour=? WHERE id=?")) {
            ps.setString(1, location.getCin());
            ps.setString(2, location.getMatricule());
            ps.setDate(3, new Date(location.getDateDebut().getTime()));
            ps.setDate(4, new Date(location.getDateDebut().getTime()));
            ps.setFloat(5, location.getPrixParJour());
            ps.setInt(6, location.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de mise à jour de la location : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM location WHERE id=?")) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de suppression de la location : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Location find(int id) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM location WHERE id=?")) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Location location = new Location();
                    location.setId(rs.getInt("id"));
                    location.setCin(rs.getString("cin"));
                    location.setMatricule(rs.getString("matricule"));
                    location.setDateDebut(rs.getDate("date_debut"));
                    location.setDateFin(rs.getDate("date_fin"));
                    location.setPrixParJour(rs.getFloat("prixparjour"));

                    return location;
                }
            }
        } catch (SQLException e) {
            System.err.println("Problème de recherche de la location : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Location> findAll() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM location")) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Location> locations = new ArrayList<>();

                while (rs.next()) {
                    Location location = new Location();
                    location.setId(rs.getInt("id"));
                    location.setCin(rs.getString("cin"));
                    location.setMatricule(rs.getString("matricule"));
                    location.setDateDebut(rs.getDate("date_debut"));
                    location.setDateFin(rs.getDate("date_fin"));
                    location.setPrixParJour(rs.getFloat("prixparjour"));

                    locations.add(location);
                }
                locations.forEach(System.out::println);
                return locations;

            }
        } catch (SQLException e) {
            System.err.println("Problème de recherche de toutes les locations : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
