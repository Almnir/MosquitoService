import static spark.Spark.get;

/**
 * Роутер
 */
public class ServiceRouter {

    private static final String API_CONTEXT = "/api/v1";

    private final ServiceController serviceController;

    public ServiceRouter(ServiceController serviceController) {
        this.serviceController = serviceController;
        setupRoutes();
    }

    private void setupRoutes() {
        // выдать информацию по конкретному ведру
        get(API_CONTEXT + "/buckets/:id", "application/json", (request, response)

                -> serviceController.find(request.params(":id")), new JsonTransformer());

        // выдать инфу по всем
        get(API_CONTEXT + "/buckets", "application/json", (request, response)
                -> serviceController.findAll(), new JsonTransformer());
    }
}
