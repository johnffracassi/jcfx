package com.jeff.fx.common;

import static com.jeff.fx.common.Instrument.AUDUSD;

public enum FXDataSource {
	Dukascopy(AUDUSD), GAIN(AUDUSD), FXCM(AUDUSD), Forexite(AUDUSD);

	Instrument[] instruments;

	FXDataSource(Instrument... instruments) {
		this.instruments = instruments;
	}

	public Instrument[] getInstruments() {
		return instruments;
	}
}
