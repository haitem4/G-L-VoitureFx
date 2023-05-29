package ma.emsi.glvoiturefx.dao;

import ma.emsi.glvoiturefx.entities.User;

import java.sql.*;

public class IUserImpl implements IUser {
    private Connection conn= DB.getConnection();
    @Override
    public void insert(User user) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO user (username,password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    user.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un User");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(User user) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE user SET username = ? , password = ? WHERE id = ?");

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM user WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un user");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public User find(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                return user;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un user");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public User findByUsername(String username) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE username = ?");

            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                return user;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un user par username");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
