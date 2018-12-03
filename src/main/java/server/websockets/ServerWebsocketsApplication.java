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

    public static void main(String[] args) throws InterruptedException {
        startWebsocketServer();
        boolean bool = false;
        if (args.length==1 ) {
            bool = Boolean.parseBoolean(args[0]);

        }
        GameManager manager = new GameManager(bool);
        WebsocketServerCommunicator.subscribe(manager);

    }

    private static void startWebsocketServer() throws InterruptedException {
        webSocketThread = new Thread(ServerWebsocketsApplication::startWebSocketServer);
        webSocketThread.start();
        webSocketThread.join();
    }


    // Start the web socket server
    private static void startWebSocketServer() {
        Server webSocketServer = new Server();
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
