package com.tarkesh.telegram.bot;

import org.apache.commons.lang3.StringUtils;

public class App {
  public static void main(String[] args) {

    String botToken = System.getenv("BOT_TOKEN");

    if (StringUtils.isEmpty(botToken)) {
      System.out.println("Set bot token as environment variable, Varibale name - BOT_TOKEN ");
      System.exit(0);
    }

    QuoteBot quoteBot = new QuoteBot(botToken);

    // QuoteBot quoteBot = new QuoteBot(botToken);
    System.out.println("Bot started listening.....");
    quoteBot.startBot();
  }
}
