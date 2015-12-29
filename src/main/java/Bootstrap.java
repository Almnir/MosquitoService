import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.*;

public class Bootstrap {
//    private static final String IP_ADDRESS = System.getenv("HSERVICE_IP") != null ? System.getenv("HSERVICE_IP") : "localhost";
//    private static final int PORT = System.getenv("HSERVICE_PORT") != null ? Integer.parseInt(System.getenv("HSERVICE_PORT")) : 8080;
//    private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";

    private static String IP_ADDRESS;
    private static int PORT;
    private static String CONNECTION_STRING;
    private static String tableName = "hackerdata";
    private static Connection conn = null;

    public static void main(String[] args) throws Exception {
        getConfiguration();
        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        createConnection();
        new ServiceRouter(new ServiceController(conn));
        shutdown();
    }

    private static void getConfiguration() {
        Config conf = ConfigFactory.load();
        IP_ADDRESS = conf.getString("hostSettings.ipAddress");
        PORT = conf.getInt("hostSettings.port");
        CONNECTION_STRING = conf.getString("dbConnectionString");
    }

    private static void createConnection() throws Exception {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (Exception except) {
            throw new RuntimeException("Not able to connect to DB");
        }
    }

    private static void shutdown() throws Exception {
        try {
            if (conn != null) {
                DriverManager.getConnection(CONNECTION_STRING + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            throw new RuntimeException("Not able to connect to DB");
        }

    }

//    private static DB mongo() throws Exception {
//        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
//        if (host == null) {
//            MongoClient mongoClient = new MongoClient("localhost");
//            return mongoClient.getDB("todoapp");
//        }
//        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
//        String dbname = System.getenv("OPENSHIFT_APP_NAME");
//        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
//        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
//        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
//        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
//        mongoClient.setWriteConcern(WriteConcern.SAFE);
//        DB db = mongoClient.getDB(dbname);
//        if (db.authenticate(username, password.toCharArray())) {
//            return db;
//        } else {
//            throw new RuntimeException("Not able to authenticate with MongoDB");
//        }
//    }
}