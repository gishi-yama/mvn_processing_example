package com.example;

import com.example.value.ScreenName;
import com.example.value.Tweet;
import twitter4j.Status;
import twitter4j.User;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TimeLineBlock {

	private final Tweet tweet;
	private final ScreenName screenName;
	private final URI iconURI;
	private final ZonedDateTime tweetAt;

	public TimeLineBlock(Status status) {
		this.tweet = new Tweet(status.getText());

		User user = status.getUser();
		this.screenName = new ScreenName(user.getScreenName());
		this.iconURI = URI.create(user.getOriginalProfileImageURLHttps());

		Instant instant = status.getCreatedAt().toInstant();
		this.tweetAt = instant.atZone(ZoneId.systemDefault());
	}

	public String getIconUrI() {
		return iconURI.toString();
	}

	public String getExtention() {
		String uri = iconURI.toString();
		int index = uri.lastIndexOf(".") + 1;
		String extension = uri.substring(index);
		return Objects.equals(extension, "jpeg") ? "jpg" : extension;
	}

	public String getBlockMessage() {
		return String.format("%s\t%s\n%s",
			screenName.getValue(), tweetAt.toString(), tweet.getValue());
	}

}
