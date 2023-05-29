package ma.emsi.glvoiturefx.entities;

import java.util.Date;
import java.util.Objects;

public class Location {
    private int id;
    private String cin;
    private String matricule;
    private Date dateDebut;
    private Date dateFin;
    private float prixParJour;

    public Location() {
    }

    public Location(int id, String cin, String matricule, Date dateDebut, Date dateFin, float prixParJour) {
        this.id = id;
        this.cin = cin;
        this.matricule = matricule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixParJour = prixParJour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Float.compare(location.prixParJour, prixParJour) == 0 && Objects.equals(cin, location.cin) && Objects.equals(matricule, location.matricule) && Objects.equals(dateDebut, location.dateDebut) && Objects.equals(dateFin, location.dateFin);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", cin='" + cin + '\'' +
                ", matricule='" + matricule + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prixParJour=" + prixParJour +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cin, matricule, dateDebut, dateFin, prixParJour);
    }

    public float getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(float prixParJour) {
        this.prixParJour = prixParJour;
    }

}
