package com.example;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TwitterService implements ITwitterService {

	private static final int MAX_STATUS = 12;

	private static final String CONSUMER_KEY = "xxxx";
	private static final String CONSUMER_SECRET = "xxxx";
	private static final String ACCESS_TOKEN = "xxxx";
	private static final String ACCESS_TOKEN_SECRET = "xxxx";

	private Twitter twitter;

	public TwitterService() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_TOKEN)
			.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		twitter = new TwitterFactory(cb.build()).getInstance();
	}


	@Override
	public List<TimeLineBlock> getTimeLineBlock(int limit, String tag) throws TwitterException {
		System.out.println("get status...");
		return twitter.search(new Query(tag))
      .getTweets()
      .stream()
      .filter(s -> !s.getUser().isProtected())
			.limit(limit)
			.map(TimeLineBlock::new)
			.collect(toList());
	}
}