package wtf.cockatoo.patpatbot.capability;

import java.util.Map;
import java.util.Objects;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import wtf.cockatoo.patpatbot.message.MessageVO;

public abstract class TelegramCapability {
    private static final Logger LOG = Logger.getLogger(TelegramCapability.class);
    private final ObjectMapper mapper;

    public TelegramCapability() {
        mapper = new ObjectMapper();
    }


    public abstract MessageVO handle(final String data);


    protected String createInlineKeyboard(final ArrayNode... buttonRows) {
        final ArrayNode buttons = mapper.createArrayNode();

        for (final ArrayNode row : buttonRows) {
            buttons.add(row);
        }

        final ObjectNode keyboard = mapper.createObjectNode();
        keyboard.set(TelegramJsonKey.inline_keyboard.name(), buttons);


        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(keyboard);
        }
        catch (final Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }


    /**
     * Create one button at one row
     */
    protected ArrayNode createKeyboardButtonInRow(final String buttonLabel, final String buttonValue) {
        final ArrayNode buttonColumn = mapper.createArrayNode();

        buttonColumn.add(createKeyboardButton(buttonLabel, buttonValue));

        return buttonColumn;
    }


    /**
     * Create multiple buttons at one row
     */
    protected ArrayNode createKeyboardButtonInRow(final Map<String, String> items) {
        final ArrayNode buttonColumn = mapper.createArrayNode();

        for (final var item : Objects.requireNonNull(items).entrySet()) {
            buttonColumn.add(createKeyboardButton(item.getKey(), item.getValue()));
        }
        return buttonColumn;
    }


    /**
     * Construct the button details
     */
    private ObjectNode createKeyboardButton(final String buttonLabel, final String buttonValue) {
        final ObjectNode button = mapper.createObjectNode();

        button.put(TelegramJsonKey.text.name(), Objects.requireNonNull(buttonLabel));
        button.put(TelegramJsonKey.callback_data.name(), Objects.requireNonNull(buttonValue));

        return button;
    }
}
