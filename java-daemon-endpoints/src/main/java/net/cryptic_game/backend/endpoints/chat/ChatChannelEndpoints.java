package net.cryptic_game.backend.endpoints.chat;

import net.cryptic_game.backend.base.api.endpoint.*;
import net.cryptic_game.backend.data.chat.ChatChannel;
import net.cryptic_game.backend.data.user.User;

import java.util.UUID;

public class ChatChannelEndpoints extends ApiEndpointCollection {

    public ChatChannelEndpoints() {
        super("chat_channel");

    }
    @ApiEndpoint("create")
    public ApiResponse create(@ApiParameter("user_id") UUID userId,
                              @ApiParameter("name") String name) {
        User user = User.getById(userId);
        ChatChannel channel = ChatChannel.createChannel(name);

        return new ApiResponse(ApiResponseType.OK, channel);
    }
    /*
    @ApiEndpoint("join")
    public ApiResponse join(@ApiParameter ("name") String name,
                            @ApiParameter ("user_id") UUID userId){

        if(name==null){
            return new ApiResponse(ApiResponseType.);
        }
        return new ApiResponse(ApiResponseType.OK, channel);

    }
    @ApiEndpoint("leave")
    public ApiResponse leave(@ApiParameter ("name") String name,
                             @ApiParameter ("user_id") UUID userId){


        return new ApiResponse(ApiResponseType.OK, channel);

    }

     */
}
