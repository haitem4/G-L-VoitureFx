package ma.emsi.glvoiturefx.services;

import ma.emsi.glvoiturefx.dao.IUser;
import ma.emsi.glvoiturefx.dao.IUserImpl;
import ma.emsi.glvoiturefx.entities.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private IUser userDao = new IUserImpl();

    public User find(int id){
        return userDao.find(id);
    }
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }
    public void save(User user) throws NoSuchAlgorithmException {
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.insert(user);
    }
    public void update(User user) throws NoSuchAlgorithmException {
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.update(user);
    }
    public void remove(User user) {
        userDao.delete(user.getId());
    }
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger bigInt = new BigInteger(1,messageDigest);
        return bigInt.toString(16);
    }
}
