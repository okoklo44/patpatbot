package wtf.cockatoo.patpatbot.capability;

/**
 * Not going to follow the best practise to have constant names all in upper case, and later on convert to lower case at use (Telegram JSON keys are all in lower case).
 * Hopefully by using lower case directly will cut short the end-to-end processing time required in AWS Lambda for cost efficiency.
 */
public enum TelegramJsonKey {
    message, entities, offset, length, type, bot_command, chat, text, sticker, emoji, photo, document, file_id, file_name, mime_type, callback_query, reply_markup, inline_keyboard,
    callback_data, data
}
