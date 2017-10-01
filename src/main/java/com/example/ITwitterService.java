package com.example;

import com.google.inject.ImplementedBy;
import twitter4j.TwitterException;

import java.util.List;

@ImplementedBy(TwitterService.class)
public interface ITwitterService {

	List<TimeLineBlock> getTimeLineBlock(int limit) throws TwitterException;

}
