package wtf.cockatoo.patpatbot.utils;

public interface TextUtils {
    String NEXT_LINE = "\n";
    String NEXT_PARAGRAPH = "\n\n";

    // It can be emoji text, and non English words like Chinese, Japanese, etc. characters.
    String UNICODE_REGEX = "\\\\u[0-9a-fA-F]{4}";
}
