package wtf.cockatoo.patpatbot.message;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

import wtf.cockatoo.patpatbot.utils.TextUtils;

public abstract class MessageHandler {
    MessageHandler nextHandler;
    List<String> fallbackResponse;


    protected MessageHandler() {
        fallbackResponse = new ArrayList<>();
    }


    // For COR pattern
    public abstract Integer getPriority();

    // For actual logic
    public abstract MessageVO buildResponse(JsonNode json) throws Exception;


    // For COR pattern
    public MessageHandler chain(final MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }


    // Commonly used methods
    protected boolean containsNonEnglishWord(final String text) {
        final Pattern unicodePattern = Pattern.compile(TextUtils.UNICODE_REGEX);
        final Matcher unicodeMatcher = unicodePattern.matcher(text);
        return unicodeMatcher.find();
    }
}
