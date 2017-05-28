package br.univel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton
public final class ConexaoDB {

	private static ConexaoDB self;
	
	private Connection con;
	
	private ConexaoDB() {
		try {
			Class.forName("org.postgresql.Driver");
			this.con = DriverManager
					.getConnection(
					"jdbc:postgresql://localhost:5432/agenda",
					"postgres", "univel");
			
			Runtime.getRuntime()
				.addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							ConexaoDB.this.con.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public final static synchronized ConexaoDB getInstance() {
		if (self == null) {
			self = new ConexaoDB();
		}
		return self;
	}
	
	public Connection getConnection() {
		return this.con;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("S� pode haver um.");
	}
	
}
