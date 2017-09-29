package com.example;

import com.google.inject.ImplementedBy;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

@ImplementedBy(TwitterService.class)
public interface ITwitterService {

	public List<Status> getStatus() throws TwitterException;

}
