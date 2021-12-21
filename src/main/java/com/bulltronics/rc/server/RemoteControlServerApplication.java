package com.bulltronics.rc.server;

import com.bulltronics.rc.server.ui.UiService;
import com.bulltronics.rc.server.util.Util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RemoteControlServerApplication {
    static ConfigurableApplicationContext ctx = null;

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("System is up and Running...");
        UiService.renderUI(e -> startServer());
    }

    private static void startServer() {
        UiService.setStatusLabel("Starting server...");

        ctx = SpringApplication.run(RemoteControlServerApplication.class);

        UiService.setActionButtonListener(e -> stopServer());
        UiService.setActionButtonLabel("Stop Server");
        UiService.setStatusLabel("Server Started.");
    }

    private static void stopServer() {
        UiService.setStatusLabel("Stopping server...");
        Util.waitForMillis(1000);

        int exitCode = SpringApplication.exit(ctx, () -> 0);
        if (exitCode == 0) {
            UiService.setStatusLabel("Server Stopped");
            UiService.setActionButtonListener(e -> startServer());
            UiService.setActionButtonLabel("Start Server");
        } else {
            UiService.setStatusLabel("Server Failed to Stop.");
        }
    }

}
