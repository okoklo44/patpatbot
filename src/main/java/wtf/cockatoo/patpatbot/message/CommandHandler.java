package wtf.cockatoo.patpatbot.message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import wtf.cockatoo.patpatbot.capability.CommandCapability;
import wtf.cockatoo.patpatbot.capability.TelegramJsonKey;
import wtf.cockatoo.patpatbot.service.BotMessageBuilder;

@ApplicationScoped
public class CommandHandler extends MessageHandler {

    private static final Logger LOG = Logger.getLogger(CommandHandler.class);

    @Inject
    CommandCapability capability;

    public CommandHandler() {
        super();
        fallbackResponse.add("Sorry, I can't help with this command. Perhaps you can try one  of the commands from <u>Menu</u> button at the bottom.");
        fallbackResponse.add("Sorry, I can't recognize this command. Perhaps you can try one  of the commands from <u>Menu</u> button at the bottom.");
    }


    @Override
    public Integer getPriority() {
        return 2;
    }


    @Override
    public MessageVO buildResponse(final JsonNode json) throws Exception {
        final JsonNode messageNode = json.path(TelegramJsonKey.message.name());
        final JsonNode commandNode = messageNode.path(TelegramJsonKey.entities.name());

        if (!commandNode.isMissingNode()) {
            MessageVO messageVO = new MessageVO();
            final JsonNode entityNode = commandNode.get(0);

            // Ensure it's a command and the command can be found as the first block of string (from index 0)
            if (entityNode.path(TelegramJsonKey.type.name()).asText().equalsIgnoreCase(TelegramJsonKey.bot_command.name())
                    && entityNode.path(TelegramJsonKey.offset.name()).asInt() == 0) {

                // -- "Hello! I'm patpat, your digital butler. I can help you to keep track of your expenses &#128181;.";
                messageVO = capability.handle(messageNode.path(TelegramJsonKey.text.name()).asText().substring(1, entityNode.path(TelegramJsonKey.length.name()).asInt()));
            }

            if (messageVO.getText() == null || messageVO.getText().isBlank()) {
                messageVO.setText(BotMessageBuilder.INSTANCE.randomFallbackReply(fallbackResponse));
            }
            return messageVO;
        }


        LOG.infof("2. NOT Command. Next");
        return nextHandler != null ? nextHandler.buildResponse(json) : null;
    }
}
