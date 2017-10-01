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

public class View extends PApplet {

	private static final int W_SIZE = 1024;
	private static final int H_SIZE = 720;
	private static final int W_BLOCK = 940;
	private static final int H_BLOCK = 80;
	private static final int HW_ICON = 80;

	@Inject
	private ITwitterService service;

	private int x;
	private int y;

	@Override
	public void setup() {
		super.setup();
		getInjector().injectMembers(this);
		noLoop();
		PFont font = createFont("MS Gothic", 12, true);
		textFont(font);
		fill(0);
		stroke(255);
	}

	@Override
	public void settings() {
		super.settings();
		size(W_SIZE, H_SIZE);
	}

	@Override
	public void draw() {
		makeBlocks();
	}

	@Override
	public void mouseReleased() {
		super.mouseReleased();
		redraw();
	}

	protected void makeBlocks() {
		reflesh();
		try {
			service.getTimeLineBlock(8)
				.forEach(this::makeBlock);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	protected void makeBlock(TimeLineBlock tlb) {
		PImage icon = loadImage(tlb.getIconUrI(), tlb.getExtention());
		image(icon, x, y, HW_ICON, HW_ICON);
		text(tlb.getBlockMessage(), HW_ICON, y, W_BLOCK, H_BLOCK);
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

	protected void reflesh() {
		background(236);
		x = 0;
		y = 0;
	}

}
