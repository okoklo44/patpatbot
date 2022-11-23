package wtf.cockatoo.patpatbot.message;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import wtf.cockatoo.patpatbot.capability.TelegramJsonKey;
import wtf.cockatoo.patpatbot.service.BotMessageBuilder;


@ApplicationScoped
public class StickerHandler extends MessageHandler {

    private static final Logger LOG = Logger.getLogger(StickerHandler.class);

    public StickerHandler() {
        super();
        fallbackResponse.add("Sorry, I'm not ready to handle this.");
        fallbackResponse.add("Please send me text message instead.");
    }


    @Override
    public Integer getPriority() {
        return 4;
    }


    @Override
    public MessageVO buildResponse(final JsonNode json) throws Exception {
        final JsonNode stickerNode = json.path(TelegramJsonKey.message.name()).path(TelegramJsonKey.sticker.name());

        if (!stickerNode.isMissingNode()) {
            // String emoji = stickerNode.path(TelegramJsonKey.emoji.name()).asText();
            final MessageVO messageVO = new MessageVO();
            messageVO.setText(BotMessageBuilder.INSTANCE.randomFallbackReply(fallbackResponse));

            return messageVO;
        }


        LOG.infof("4. NOT Sticker. Next");
        return nextHandler != null ? nextHandler.buildResponse(json) : null;
    }
}
