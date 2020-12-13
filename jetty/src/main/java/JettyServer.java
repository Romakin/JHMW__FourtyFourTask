import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8898);
        setWebAppServlet(server);
        server.start();
        server.join();
    }

    public static void setWebAppServlet(Server server) {
        WebAppContext webapp = new WebAppContext();
        webapp.setWar("ServletService/target/ServletService.war");
        webapp.setContextPath("/api");

        server.setHandler(webapp);
    }
}
