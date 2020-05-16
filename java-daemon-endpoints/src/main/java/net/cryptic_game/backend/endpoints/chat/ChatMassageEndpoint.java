package net.cryptic_game.backend.endpoints.chat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpoint;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpointCollection;
import net.cryptic_game.backend.base.api.endpoint.ApiParameter;
import net.cryptic_game.backend.base.api.endpoint.ApiResponse;
import net.cryptic_game.backend.data.user.User;

import java.util.UUID;
public class ChatMassageEndpoint extends ApiEndpointCollection {
    public ChatMassageEndpoint() {
        super("chat_massage");
    }

    @ApiEndpoint("create")
    public ApiResponse create(@ApiParameter("user_id") UUID userId,
                              @ApiParameter("name") String name,
                              @ApiParameter("channel_id") UUID channelId,
                              @ApiParameter("message_content")String messageContent) {
        User user = User.getById(userId);

        return null;
    }
}
