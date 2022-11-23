package wtf.cockatoo.patpatbot.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import wtf.cockatoo.patpatbot.utils.MessageFormat;

@RegisterRestClient
public interface TelegramAPI {

    /**
     * https://core.telegram.org/bots/api#sendmessage
     */
    @GET
    @Path("/sendMessage")
    Response sendMessage(
            @QueryParam("chat_id") final String chatId,
            @QueryParam("parse_mode") final MessageFormat format,
            @QueryParam("reply_markup") final String replyMarkup,
            @QueryParam final String text);


    /**
     * https://core.telegram.org/bots/api#sendphoto
     */
    @GET
    @Path("/sendPhoto")
    Response sendPhoto(
            @QueryParam("chat_id") final String chatId,
            @QueryParam("parse_mode") final MessageFormat format,
            @QueryParam("reply_markup") final String replyMarkup,
            @QueryParam final String photo,
            @QueryParam final String caption);
}
