package wtf.cockatoo.patpatbot.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import wtf.cockatoo.patpatbot.message.MessageHandler;
import wtf.cockatoo.patpatbot.message.MessageVO;

@ApplicationScoped
public class MessageReceiver {

    private static final Logger LOG = Logger.getLogger(MessageReceiver.class);

    @Inject
    Instance<MessageHandler> messageHandlers;

    /**
     * -- This is how the COR pattern of message handler can be created, if it's using plain old java instantiation approach.
     * 
     * MessageHandler messageHandler = new CommandHandler().chain(new TextHandler()).chain(new StickerHandler()).chain(new FileHandler());
     * 
     * -- The following is the only workaround I can think about while using CDI annotation.
     */
    public MessageVO getReplyMessage(final String incomingJson) throws Exception {

        final List<MessageHandler> handlers = messageHandlers.stream().sorted((a, b) -> a.getPriority().compareTo(b.getPriority())).collect(Collectors.toList());
        for (int i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).chain(handlers.get(i + 1));
        }

        MessageVO messageVO = handlers.get(0).buildResponse(new ObjectMapper().readTree(incomingJson));

        // Get ChatBot fallback message
        if (messageVO == null) {
            messageVO = new MessageVO();
            messageVO.setText(BotMessageBuilder.INSTANCE.randomFallbackReply());
        }
        return messageVO;
    }
}
