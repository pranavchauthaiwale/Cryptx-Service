package com.cryptx.services;

import java.util.List;

import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;

public interface IHistoricTrendsService {

	public static final String HISTORIC_TRENDS_SERVICE = "HistoricTrendsService";

	List<Double[]> getBitcoinHistoricTrends() throws CryptxException, EmptyDataException;

	List<Double[]> getLitecoinHistoricTrends() throws CryptxException, EmptyDataException;

	List<Double[]> getEthereumHistoricTrends() throws CryptxException, EmptyDataException;
}
