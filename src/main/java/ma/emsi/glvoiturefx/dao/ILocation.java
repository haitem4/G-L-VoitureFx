package ma.emsi.glvoiturefx.dao;

import ma.emsi.glvoiturefx.entities.Location;
import java.util.List;

public interface ILocation {
    void insert(Location location);
    void update(Location location);
    void delete(int id);
    Location find(int id);
    List<Location> findAll();
}
