package wtf.cockatoo.patpatbot.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import wtf.cockatoo.patpatbot.utils.TextUtils;

public enum BotMessageBuilder {
    INSTANCE;

    List<String> fallbackMessages = Arrays.asList(
            new String[] {
                    "I'm sorry, I didn't understand that. Would you mind repeating it?" + TextUtils.NEXT_PARAGRAPH
                            + "Or you may start all over again by going back to our main menu.",
                    "What was that? Could you repeat that, please?" + TextUtils.NEXT_PARAGRAPH + "Or you may start all over again by going back to our main menu." });

    public String randomFallbackReply() {
        return fallbackMessages.get(ThreadLocalRandom.current().nextInt(fallbackMessages.size()));
    }


    public String randomFallbackReply(List<String> messages) {
        return messages.get(ThreadLocalRandom.current().nextInt(messages.size()));
    }
}
