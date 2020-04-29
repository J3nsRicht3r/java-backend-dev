package net.cryptic_game.backend.server.server.websocket.endpoints;

import com.google.gson.JsonObject;
import net.cryptic_game.backend.base.api.client.ApiClient;
import net.cryptic_game.backend.base.api.endpoint.*;
import net.cryptic_game.backend.base.daemon.Function;
import net.cryptic_game.backend.base.utils.JsonBuilder;
import net.cryptic_game.backend.data.user.Session;
import net.cryptic_game.backend.server.App;
import net.cryptic_game.backend.server.daemon.DaemonHandler;

import java.util.UUID;

public class WebSocketDaemonEndpoints extends ApiEndpointCollection {

    private final DaemonHandler daemonHandler;

    public WebSocketDaemonEndpoints(final DaemonHandler daemonHandler) {
        super("daemon");
        this.daemonHandler = daemonHandler;
    }

    @ApiEndpoint("send")
    public ApiResponse send(final ApiClient client,
                            @ApiTag final String tag,
                            @ApiParameter("function_name") final String functionName,
                            @ApiParameter(value = "data", optional = true) final JsonObject data) {

        final Session session = client.get(Session.class);

        if (!(session != null && session.isValid())) {
            return new ApiResponse(ApiResponseType.UNAUTHORIZED, "INVALID_SESSION");
        }

        final Function function = this.daemonHandler.getFunction(functionName);

        if (function == null) {
            return new ApiResponse(ApiResponseType.NOT_FOUND, "FUNCTION");
        }

        if (data != null && function.validateArguments(data)) {
            return new ApiResponse(ApiResponseType.BAD_REQUEST, "INVALID_PARAMETERS");
        }

        UUID requestTag = this.daemonHandler.executeFunction(tag, client.getChannel(), function, session.getUser().getId(), data == null ? new JsonObject() : data);

        App.addTimeout(App.getInstance().getConfig().getResponseTimeout() * 1000, () -> {
            if (daemonHandler.isRequstOpen(requestTag)) {
                daemonHandler.respondToClient(JsonBuilder.anJSON()
                        .add("tag", requestTag)
                        .add("info", JsonBuilder.anJSON(ApiResponseType.BAD_GATEWAY.serialize())
                                .add("notification", false)
                                .add("message", "DAEMON_TIMEOUT")
                                .build())
                        .build());
            }
        });

        return null;
    }
}
