import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер
 */
public class ServiceController {

    private final Connection conn;

    public ServiceController(Connection conn) {
        this.conn = conn;
    }

    public Bucket find(String id) {
        // TODO: найти по айди и набить объект
        return new Bucket();
    }

    public List<Bucket> findAll() {
        List<Bucket> buckets = new ArrayList<>();
        // TODO: найти все
        return buckets;
    }
}
