package server.rest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import shared.rest.SLoginResponse;

import java.util.ArrayList;

public class MejnServer
{
    public static ArrayList<SLoginResponse> users = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new
                ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet =
                context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                Service.class.getCanonicalName());
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    public ArrayList<SLoginResponse> getUsers(){
        return users;
    }

}