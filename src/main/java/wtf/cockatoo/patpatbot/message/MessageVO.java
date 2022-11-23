package wtf.cockatoo.patpatbot.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import wtf.cockatoo.patpatbot.utils.MessageFormat;
import wtf.cockatoo.patpatbot.utils.TextUtils;

@Getter
@Setter
public class MessageVO {

    public MessageVO() {
        this(MessageFormat.HTML);
    }


    public MessageVO(final MessageFormat messageFormat) {
        this.messageFormat = messageFormat;
    }

    private final String chatId = "25137328";
    private String replyMarkup;
    private MessageFormat messageFormat;
    private String imageUrl;

    @Getter(AccessLevel.NONE)
    private String text;

    @Getter(AccessLevel.NONE)
    private String titleText;

    @Getter(AccessLevel.NONE)
    private String bodyText;


    public String getText() {
        if ((titleText != null && bodyText != null) && (!titleText.isBlank() && !bodyText.isBlank())) {
            text = "<b>" + titleText + "</b>";
            text += TextUtils.NEXT_PARAGRAPH;
            text += bodyText;
        }
        return text;
    }
}
