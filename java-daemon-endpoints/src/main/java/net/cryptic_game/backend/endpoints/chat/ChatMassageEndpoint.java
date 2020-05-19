package net.cryptic_game.backend.endpoints.chat;

import net.cryptic_game.backend.base.api.endpoint.ApiEndpoint;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpointCollection;
import net.cryptic_game.backend.base.api.endpoint.ApiParameter;
import net.cryptic_game.backend.base.api.endpoint.ApiParameterSpecialType;
import net.cryptic_game.backend.base.api.endpoint.ApiResponse;
import net.cryptic_game.backend.data.user.User;

import java.util.UUID;
public class ChatMassageEndpoint extends ApiEndpointCollection {
    public ChatMassageEndpoint() {
        super("chat_massage", "massage");
    }

    @ApiEndpoint("create")
    public ApiResponse create(@ApiParameter(value = "user_id", special = ApiParameterSpecialType.USER) UUID userId,
                              @ApiParameter("name") String name,
                              @ApiParameter("channel_id") UUID channelId,
                              @ApiParameter("message_content") String messageContent) {
        User user = User.getById(userId);

        return null;
    }
}
