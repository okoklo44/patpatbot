package wtf.cockatoo.patpatbot.message;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import wtf.cockatoo.patpatbot.capability.TelegramJsonKey;
import wtf.cockatoo.patpatbot.service.BotMessageBuilder;

@ApplicationScoped
public class TextHandler extends MessageHandler {

    private static final Logger LOG = Logger.getLogger(TextHandler.class);

    public TextHandler() {
        super();
        fallbackResponse.add("Sorry, I can only understand English. Speaking of which, no emoji and smiley face too.");
        fallbackResponse.add("Please try again in English. Oh, by the way, no emoji and smiley face please.");
    }


    @Override
    public Integer getPriority() {
        return 3;
    }


    /**
     * What implemented here is to ignore all the Unicode characters (Emoji, non English characters).
     */
    @Override
    public MessageVO buildResponse(final JsonNode json) throws Exception {
        final JsonNode textNode = json.path(TelegramJsonKey.message.name()).path(TelegramJsonKey.text.name());

        if (!textNode.isMissingNode()) {
            final MessageVO messageVO = new MessageVO();
            final String text = JsonMapper.builder().enable(JsonWriteFeature.ESCAPE_NON_ASCII).build().writeValueAsString(textNode);


            // -- Unable to handle the input (that contains UNICODE)
            if (containsNonEnglishWord(text)) {
                messageVO.setText(BotMessageBuilder.INSTANCE.randomFallbackReply(fallbackResponse));
            }
            // -- Handling the input
            else {
                // TODO more logic. Temporary echo back what received.
                messageVO.setText(textNode.asText());
            }
            return messageVO;
        }


        LOG.infof("3. NOT Text. Next");
        return nextHandler != null ? nextHandler.buildResponse(json) : null;
    }
}
