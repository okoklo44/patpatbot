package wtf.cockatoo.patpatbot.capability;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import wtf.cockatoo.patpatbot.message.CommandHandler;
import wtf.cockatoo.patpatbot.message.MessageVO;
import wtf.cockatoo.patpatbot.utils.TextUtils;

@ApplicationScoped
public class CommandCapability extends TelegramCapability {

    private static final Logger LOG = Logger.getLogger(CommandHandler.class);

    enum CMD {
        expense, hdb
    }


    @Override
    public MessageVO handle(final String data) {
        final MessageVO messageVO = new MessageVO();
        CMD command = null;

        try {
            command = CMD.valueOf(data);

            switch (command) {
                case expense:
                    buildPage1(messageVO);
                    break;
                case hdb:
                    buildPage2(messageVO);
                    break;
            }
        }
        catch (final IllegalArgumentException e) {
            // TODO remove this. Shouldn't build any page over here.
            buildPage3(messageVO);
            LOG.warnf(e, "Unable to handle command: %s", data);
        }
        catch (final Exception e) {
            LOG.errorf(e, "Unable to handle command: %s", data);
        }

        // In the event of exception, so long MessageVO text is null, it will be handled as "not supported" in handler layer.
        return messageVO;
    }


    private void buildPage1(final MessageVO messageVO) {
        final Map<String, String> ctaItems = new HashMap<>();

        ctaItems.put("1", "1");
        ctaItems.put("2", "2");
        ctaItems.put("3", "3");
        ctaItems.put("4", "4");
        ctaItems.put("5", "5");
        ctaItems.put("6", "6");

        messageVO.setTitleText("&#49;&#65039;&#8419; This is 1st page menu");
        messageVO.setBodyText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas eu fringilla lectus. Suspendisse auctor aliquet tortor eget porttitor.");
        messageVO.setReplyMarkup(createInlineKeyboard(createKeyboardButtonInRow(ctaItems), createKeyboardButtonInRow("Buy Now", "buy")));
    }


    // Photo (wide) , title text, body text, bullet point text, URL, CTA
    private void buildPage2(final MessageVO messageVO) {
        messageVO.setTitleText("&#127474;&#127486; This is 2nd page menu");
        final String bodyText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam faucibus suscipit turpis, ac egestas orci dignissim eget. Fusce non purus at orci commodo tempus vitae at nunc."
                + TextUtils.NEXT_PARAGRAPH
                + "Lornare final sapien sed erat final commodo gravida. Phasellus ornare molestie ipsum final vel dictum. Nam ut ante viverra, consequat final massa feugiat, aliquet final lacus. Phasellus laoreet nisl risus. Etiam ultricies mollis nibh, eu suscipit est dictum eget."
                + TextUtils.NEXT_PARAGRAPH + "&#128547; Cras sollicitudin fermentum blandit." + TextUtils.NEXT_LINE + "&#128541; Praesent vulputate" + TextUtils.NEXT_LINE
                + "&#128531;elit et euismod volutpat" + TextUtils.NEXT_PARAGRAPH + "https://core.telegram.org/bots/payments";
        messageVO.setBodyText(bodyText);
        messageVO.setImageUrl("https://picsum.photos/1280/800");
        messageVO.setReplyMarkup(createInlineKeyboard(createKeyboardButtonInRow("Back", "back")));
    }


    // Photo (square), body text, CTA
    private void buildPage3(final MessageVO messageVO) {
        final String bodyText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam at faucibus mi. Vivamus rutrum eu sapien sed scelerisque. Morbi et purus id nibh iaculis imperdiet eget a tortor. Mauris consequat quam in libero laoreet hendrerit. Integer aliquet vel orci a suscipit. Nullam ultrices turpis et sodales fringilla. Ut odio lectus, feugiat sed blandit vitae, varius sit amet sapien. Maecenas lobortis a nunc sit amet placerat."
                + TextUtils.NEXT_PARAGRAPH
                + "Curabitur placerat nibh semper porttitor venenatis. Ut dignissim purus quis euismod venenatis. Nulla aliquet, neque et gravida faucibus, eros sem vulputate nisl, a aliquet risus odio ut sem. Etiam at arcu consequat, cursus libero et, accumsan libero. Quisque convallis efficitur dui. Fusce interdum mi nec bibendum aliquam. Cras ligula augue, malesuada vel dui vel, aliquet euismod orci. Nam quis augue facilisis, imperdiet sapien vel, tristique ex. Duis sit amet eleifend risus, id sollicitudin mauris.";
        messageVO.setText(bodyText);
        messageVO.setImageUrl("https://picsum.photos/1280");
        messageVO.setReplyMarkup(createInlineKeyboard(createKeyboardButtonInRow("Back", "back")));
    }
}

