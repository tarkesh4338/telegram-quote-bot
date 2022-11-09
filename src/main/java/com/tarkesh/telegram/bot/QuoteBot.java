package com.tarkesh.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.commons.lang3.StringUtils;

public class QuoteBot {
  private TelegramBot bot;
  private QuoteService quoteService;

  public QuoteBot(String token) {
    this.bot = new TelegramBot(token);
    this.quoteService = new QuoteService();
  }

  public void startBot() {

    bot.setUpdatesListener(updates -> {

      updates.forEach(update -> {
        
        if (update != null && update.message() != null) {
          Message userMessage = update.message();
          Long chatId = userMessage.chat().id();

          String text = userMessage.text();

          if (StringUtils.equalsAnyIgnoreCase(text, "/quote", "quote")) {
            quote(chatId);
            return;
          }

          if (StringUtils.equalsAnyIgnoreCase(text, "/start", "start")) {
            start(chatId);
            return;
          }

          if (StringUtils.equalsAnyIgnoreCase(text, "/about", "about")) {
            about(chatId);
            return;
          }

          // Invalid Command
          Integer messageId = userMessage.messageId();
          SendMessage msg = new SendMessage(chatId, "Invalid command, Use below commands");
          msg.replyToMessageId(messageId);
          this.sendMessage(msg);
          
          start(chatId);

        }
      });

      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });

  }

  private void quote(Long chatId) {
    String quote = this.quoteService.getRandomQuote(true);
    SendMessage msg = new SendMessage(chatId, quote);
    this.sendMessage(msg);
  }

  // User Welcome Message Handler
  private void start(Long chatId) {
    String startMessage = "Motivational Quotes Bot  \n\n" + "Use /quote to get a random motivational quote \n\n"
        + "Use /about for detail and Source code.";

    SendMessage msg = new SendMessage(chatId, startMessage);
    this.sendMessage(msg);

  }

  // About Message handler
  private void about(Long chatId) {
    String about = "This bot is made by Tarkeshwer Kumar \n\n " + "Github : https://github.com/tarkesh4338 \n"
        + "Source Code : https://github.com/tarkesh4338/Telegram-Quote-Bot";

    SendMessage msg = new SendMessage(chatId, about);
    this.sendMessage(msg);
  }

  // Reply to User messages
  private void sendMessage(SendMessage msg) {
    bot.execute(msg);
  }

}
