package com.example;

import processing.core.PApplet;
import processing.core.PFont;

public class App extends PApplet {

	public static void main(String[] args) {
		PApplet.main(new String[]{"com.example.App"});
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void settings() {
		super.settings();
		size(400, 400);
	}

	@Override
	public void draw() {
		super.draw();
		PFont font = createFont("MS Gothic", 48, true);
		textFont(font);
		fill(0);
		text("こんにちは!", 0, height / 2);
	}
}
