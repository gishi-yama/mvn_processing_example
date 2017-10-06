package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
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

  private ControlP5 cp5;

  @Override
  public void setup() {
    super.setup();
    reflesh();
    getInjector().injectMembers(this);
    PFont font = createFont("MS Gothic", 14, true);
    textFont(font);
    fill(255);
    stroke(255);
    cp5 = new ControlP5(this);
    cp5.addTextfield("input")
      .setPosition(30, 10)
      .setSize(200, 40)
      .setColorBackground(0)
      .setCaptionLabel("HashTag")
      .setAutoClear(false)
      .setFont(font)
      .setText("#")
      .setFocus(true);
    cp5.addBang("search")
      .setPosition(270,10)
      .setSize(80,40)
      .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER);
  }


  @Override
  public void settings() {
    super.settings();
    size(W_SIZE, H_SIZE);
  }

  @Override
  public void draw() {
    // no tasks.
  }

  public void search() {
    String tag = cp5.get(Textfield.class,"input").getText();
    cp5.get(Textfield.class,"input").clear().setText("#");
    if(tag.length() > 1) {
      makeBlocks(tag);
    }
  }

  protected void makeBlocks(String tag) {
    reflesh();
    try {
      service.getTimeLineBlock(8, tag)
        .forEach(this::makeBlock);
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  protected void makeBlock(TimeLineBlock tlb) {
    line(x, y, W_SIZE, y);
    PImage icon = loadImage(tlb.getIconUrI(), tlb.getExtention());
    image(icon, x, y, HW_ICON, HW_ICON);
    text(tlb.getBlockMessage(), HW_ICON, y, W_BLOCK, H_BLOCK);
    y = y + H_BLOCK;
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
    background(0);
    x = 0;
    y = 80;
  }

}
