package com.jeff.fx.backtest.strategy.coder;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StrategyCodeModel {

	private String className;
	private String openCode;
	private String closeCode;
	private List<StrategyParam> parameters;

	public StrategyCodeModel() {
	}

	@XmlElement(name="class-name")
	public String getClassName() {
		return className;
	}

	@XmlElement(name="open-code")
	public String getOpenCode() {
		return openCode;
	}

	@XmlElement(name="close-code")
	public String getCloseCode() {
		return closeCode;
	}

	@XmlElementWrapper(name="parameters")
	@XmlElement(name="parameter")
	public List<StrategyParam> getParameters() {
		return parameters;
	}

	public void setParameters(List<StrategyParam> params) {
		this.parameters = params;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}

	public void setCloseCode(String closeCode) {
		this.closeCode = closeCode;
	}
}
