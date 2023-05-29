package ma.emsi.glvoiturefx.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				String user = props.getProperty("user");
				String password = props.getProperty("password");
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				System.err.println("Problème de chargement de Driver Manager");
				e.printStackTrace();
			}
		}

		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("Erreur de fermeture de connexion");
				e.printStackTrace();
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("C:\\Users\\haite\\Bureau\\G-L-VoitureFx\\src\\main\\resources\\ma\\emsi\\glvoiturefx\\db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.err.println("Erreur de fermeture de Statement");
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.err.println("Erreur de fermeture de ResultSet");
				e.printStackTrace();
			}
		}
	}
}
