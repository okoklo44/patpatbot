package wtf.cockatoo.patpatbot.message;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import wtf.cockatoo.patpatbot.capability.TelegramJsonKey;

@ApplicationScoped
public class KeyboardActionHandler extends MessageHandler {

    private static final Logger LOG = Logger.getLogger(KeyboardActionHandler.class);

    public KeyboardActionHandler() {
        super();
    }


    @Override
    public Integer getPriority() {
        return 1;
    }


    @Override
    public MessageVO buildResponse(final JsonNode json) throws Exception {
        final JsonNode actionNode = json.path(TelegramJsonKey.callback_query.name()).path(TelegramJsonKey.data.name());

        if (!actionNode.isMissingNode()) {
            final MessageVO messageVO = new MessageVO();

            final String actionValue = actionNode.asText();

            // TODO more logic. Echo the input action for now.
            messageVO.setText("You've entered: " + actionValue);

            return messageVO;
        }


        LOG.infof("1. NOT keyboard-action. Next");
        return nextHandler != null ? nextHandler.buildResponse(json) : null;
    }
}
