package wtf.cockatoo.patpatbot.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import wtf.cockatoo.patpatbot.message.MessageVO;

@ApplicationScoped
public class MessageSender {

    private static final Logger LOG = Logger.getLogger(MessageSender.class);

    @Inject
    @RestClient
    TelegramAPI telegramAPI;

    public Response reply(final MessageVO messageVO) {

        if (messageVO.getImageUrl() != null && !messageVO.getImageUrl().isBlank()) {
            return telegramAPI.sendPhoto(messageVO.getChatId(), messageVO.getMessageFormat(), messageVO.getReplyMarkup(), messageVO.getImageUrl(), messageVO.getText());
        }

        return telegramAPI.sendMessage(messageVO.getChatId(), messageVO.getMessageFormat(), messageVO.getReplyMarkup(), messageVO.getText());
    }
}
