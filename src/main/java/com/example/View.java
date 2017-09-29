package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class View extends PApplet {

	private static final int W_SIZE = 1024;
	private static final int H_SIZE = 768;
	private static final int W_BLOCK = 956;
	private static final int H_BLOCK = 64;
	private static final int HW_ICON = 64;


	@Inject
	private ITwitterService service;

	private int x;
	private int y;

	@Override
	public void setup() {
		super.setup();
		x = 0;
		y = 0;
		getInjector().injectMembers(this);
		noLoop();
		background(224,224,224);
	}

	@Override
	public void settings() {
		super.settings();
		size(W_SIZE, H_SIZE);
	}

	@Override
	public void draw() {
		super.draw();

		PFont font = createFont("MS Gothic", 16, true);
		textFont(font);
		fill(0);
		try {
			service.getStatus()
				.forEach(this::makeBlock);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased() {
		super.mouseReleased();
		draw();
	}

	protected void makeBlock(Status status) {
		String userName = status.getUser().getName();
		String text = status.getText();
		Instant instant = status.getCreatedAt().toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		PImage icon = loadImage(status.getUser().getOriginalProfileImageURL(), "png");
		String str = String.format("%s:%s\n%s", userName, text, zdt.toString());
		image(icon, x, y, HW_ICON, HW_ICON);
		text(str, HW_ICON, y, W_BLOCK, H_BLOCK);
		y = y + H_BLOCK;
		line(x, y, W_SIZE, y);
	}

	protected Injector getInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(ITwitterService.class).to(TwitterService.class);
			}
		});
	}

}
