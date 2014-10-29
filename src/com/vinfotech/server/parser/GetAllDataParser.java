package com.vinfotech.server.parser;

import com.vinfotech.model.GetAllData;

public class GetAllDataParser extends BaseParser {
	private GetAllData getData;

	public boolean parse(String json) {
		boolean ret = super.parse("all_data", json);
		if (getStaus()) {
			getData = new GetAllData(getDataObject());
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public GetAllData getAllData() {
		return getData;
	}

}
