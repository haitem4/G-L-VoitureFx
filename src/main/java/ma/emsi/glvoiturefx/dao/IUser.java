package ma.emsi.glvoiturefx.dao;

import ma.emsi.glvoiturefx.entities.User;


public interface IUser {
    void insert(User user);
    void update(User user);
    void delete(int id);
    User find(int id);
    User findByUsername(String username);
}
