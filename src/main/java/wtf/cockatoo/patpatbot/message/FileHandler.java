package wtf.cockatoo.patpatbot.message;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import wtf.cockatoo.patpatbot.capability.TelegramJsonKey;

@ApplicationScoped
public class FileHandler extends MessageHandler {

    private static final Logger LOG = Logger.getLogger(FileHandler.class);

    public FileHandler() {
        super();
    }


    @Override
    public Integer getPriority() {
        return 5;
    }


    @Override
    public MessageVO buildResponse(final JsonNode json) throws Exception {
        final JsonNode fileNode = json.path(TelegramJsonKey.message.name()).path(TelegramJsonKey.document.name());

        if (!fileNode.isMissingNode()) {
            final MessageVO messageVO = new MessageVO();

            final String fileId = fileNode.path(TelegramJsonKey.file_id.name()).asText();
            final String mime = fileNode.path(TelegramJsonKey.mime_type.name()).asText();
            final String fileName = fileNode.path(TelegramJsonKey.file_name.name()).asText();

            final String data = fileName + " | " + mime + " | " + fileId;

            // TODO more logic. Returning the File attributes as a quick test for now.
            messageVO.setText(data);

            return messageVO;
        }


        LOG.infof("5. NOT File/Doc. Next");
        return nextHandler != null ? nextHandler.buildResponse(json) : null;
    }
}
