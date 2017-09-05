package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import processing.core.PApplet;
import processing.core.PFont;

public class App extends PApplet {

	@Inject
	private IService service;

	public static void main(String[] args) {
		PApplet.main(new String[]{App.class.getName()});
	}

	@Override
	public void setup() {
		super.setup();
		getInjector().injectMembers(this);
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
		text(service.getMessage(), 0, height / 2);
	}

	protected Injector getInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IService.class).to(Service.class);
			}
		});
	}
}
