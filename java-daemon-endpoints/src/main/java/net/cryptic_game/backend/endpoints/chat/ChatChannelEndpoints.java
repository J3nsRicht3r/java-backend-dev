package net.cryptic_game.backend.endpoints.chat;

import net.cryptic_game.backend.base.api.endpoint.ApiEndpoint;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpointCollection;
import net.cryptic_game.backend.base.api.endpoint.ApiParameter;
import net.cryptic_game.backend.base.api.endpoint.ApiParameterSpecialType;
import net.cryptic_game.backend.base.api.endpoint.ApiResponse;
import net.cryptic_game.backend.base.api.endpoint.ApiResponseType;
import net.cryptic_game.backend.data.chat.ChatChannel;
import net.cryptic_game.backend.data.chat.ChatChannelAccess;
import net.cryptic_game.backend.data.user.User;

import java.util.UUID;

public class ChatChannelEndpoints extends ApiEndpointCollection {

    public ChatChannelEndpoints() {
        super("chat_channel", "manage_channel");

    }

    @ApiEndpoint("create")
    public ApiResponse create(@ApiParameter(value = "user_id", special = ApiParameterSpecialType.USER) UUID userId,
                              @ApiParameter("name") String name) {
        User user = User.getById(userId);
        ChatChannel channel = ChatChannel.createChannel(name);
        ChatChannelAccess.join(user, channel, null);
        return new ApiResponse(ApiResponseType.OK, channel);
    }

    @ApiEndpoint("remove")
    public ApiResponse remove(@ApiParameter("channel_Id") UUID channelId) {

        ChatChannel.removeChannel(channelId);

        return new ApiResponse(ApiResponseType.OK, channelId);
    }

    @ApiEndpoint("join")
    public ApiResponse join(@ApiParameter(value = "user_id", special = ApiParameterSpecialType.USER) UUID userId,
                            @ApiParameter("manage_channel") ChatChannel channel) {
        User user = User.getById(userId);
        ChatChannelAccess.join(user, channel, null);

        return new ApiResponse(ApiResponseType.OK, channel);

    }

    @ApiEndpoint("leave")
    public ApiResponse leave(@ApiParameter(value = "user_id", special = ApiParameterSpecialType.USER) UUID userId,
                             @ApiParameter("chat_channel") ChatChannel channel) {
        User user = User.getById(userId);
        ChatChannelAccess.leave(user, channel, null);

        return new ApiResponse(ApiResponseType.OK, channel);

    }


}
