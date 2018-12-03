package server.websockets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import server.logic.GameManager;

import javax.websocket.server.ServerContainer;

public class ServerWebsocketsApplication {
    private static final int PORT = 1234;

    public static Thread webSocketThread;
    public static GameManager manager;
    private static Server webSocketServer;

    public static void main(String[] args) throws InterruptedException {

        wws();

        boolean bool = false;
        if (args.length==1 ) {
            bool = Boolean.parseBoolean(args[0]);

        }
        manager = new GameManager(bool);
        WebsocketServerCommunicator.subscribe(manager);

    }

    public static void stop() throws Exception {
        WebsocketServerCommunicator.unSubscribe(manager);
        webSocketServer.stop();
        webSocketThread.interrupt();
        webSocketThread = null;
        int i=0;

    }

    private static void wws() throws InterruptedException {
        webSocketThread = new Thread(ServerWebsocketsApplication::startWebSocketServer);
        webSocketThread.start();
        webSocketThread.join();
    }



    // Start the web socket server
    private static void startWebSocketServer() {
        webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(PORT);
        webSocketServer.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);

            // Add WebSocket endpoint to javax.websocket layer

            wscontainer.addEndpoint(WebsocketServerCommunicator.class);
            webSocketServer.start();
            //server.dump(System.err);

            //webSocketServer.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
