import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class HelloWorld {

    private static Map<String, String> usernamePasswords = new HashMap<String, String>();

    public static void main(String[] args) {

        usernamePasswords.put("foo", "bar");
        usernamePasswords.put("admin", "admin");

        staticFileLocation("/public");

        get("/", (req, res) -> "Я у мамки программист!");
        get("/users/:name", (request, response) -> "Привет, " + request.params(":name"));


        before("/hello", (request, response) -> {
            String user = request.queryParams("user");
            String password = request.queryParams("password");

            String dbPassword = usernamePasswords.get(user);
            if (!(password != null && password.equals(dbPassword))) {
                halt(401, "Вам здесь не рады!!!");
            }
        });

        before("/hello", (request, response) -> response.header("Foo", "Set by second before filter"));
        get("/hello", (req, res) -> "Кулхацкир привет!");
        after("/hello", (request, response) -> response.header("spark", "added by after-filter"));

    }
}