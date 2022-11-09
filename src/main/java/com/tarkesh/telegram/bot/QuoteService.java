package com.tarkesh.telegram.bot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class QuoteService {

  private static final String QUOTE_API = "https://api.quotable.io/random"; // ?minLength=100&maxLength=140

  public String getRandomQuote(boolean withAuthorName) {
    return getQuote(withAuthorName);
  }

  private String getQuote(boolean withAuthorName) {
    Response response = RestAssured.get(QUOTE_API);
    String quoteResponse = response.asString();
    return parseQuoteJson(quoteResponse, withAuthorName);
  }

  private String parseQuoteJson(String quoteJson, boolean withAuthorName) {
    StringBuilder sb = new StringBuilder();
    JsonObject jsonObject = JsonParser.parseString(quoteJson).getAsJsonObject();

    sb.append(jsonObject.get("content").getAsString());
    if (withAuthorName)
      sb.append("\n\n Author - " + jsonObject.get("author").getAsString());
    return sb.toString();

  }

}
