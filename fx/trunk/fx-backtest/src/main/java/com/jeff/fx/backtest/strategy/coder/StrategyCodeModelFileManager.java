package com.jeff.fx.backtest.strategy.coder;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

public class StrategyCodeModelFileManager {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	public StrategyCodeModelFileManager() {
	}

	private Marshaller getMarshaller() {
		if(marshaller == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(StrategyCodeModel.class);
			    marshaller = context.createMarshaller();
			    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return marshaller;
	}
	
	private Unmarshaller getUnmarshaller() {
		if(unmarshaller == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(StrategyCodeModel.class);
				unmarshaller = context.createUnmarshaller();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return unmarshaller;
	}
	
	public void exportModel(StrategyCodeModel model, File file) throws JAXBException {
		getMarshaller().marshal(model, file);
	}
	
	public StrategyCodeModel importModel(File file) throws JAXBException {
		return (StrategyCodeModel)getUnmarshaller().unmarshal(file);
	}
}