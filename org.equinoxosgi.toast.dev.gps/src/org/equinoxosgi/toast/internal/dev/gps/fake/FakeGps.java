package org.equinoxosgi.toast.internal.dev.gps.fake;

import org.equinoxosgi.toast.dev.gps.IGps;

public class FakeGps implements IGps {

	@Override
	public int getHeading() {
		return 90;
	}
	
	@Override
	public int getLatitude() {
		return 3776999;
	}
	
	@Override
	public int getLongitude() {
		return -12244694;
	}
	
	@Override
	public int getSpeed() {
		return 50;
	}
}
