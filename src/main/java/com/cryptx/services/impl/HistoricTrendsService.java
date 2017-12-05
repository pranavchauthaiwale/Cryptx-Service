package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;
import com.cryptx.services.IHistoricTrendsService;

@Service(IHistoricTrendsService.HISTORIC_TRENDS_SERVICE)
public class HistoricTrendsService implements IHistoricTrendsService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public List<Double[]> getBitcoinHistoricTrends() throws CryptxException, EmptyDataException {
		String query = String.format("SELECT timestmp, bitcoinvalue FROM bitcoindata");
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildCurrencyTrendsFromResultSet(rs, "bitcoinvalue");
		//return null;
	}

	@Override
	public List<Double[]> getLitecoinHistoricTrends() throws CryptxException, EmptyDataException {
		String query = String.format("SELECT timestmp, litecoinvalue FROM litecoindata");
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildCurrencyTrendsFromResultSet(rs, "litecoinvalue");
		//return null;
	}

	@Override
	public List<Double[]> getEthereumHistoricTrends() throws CryptxException, EmptyDataException {
		String query = String.format("SELECT timestmp, ethereumvalue FROM ethereumdata");
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildCurrencyTrendsFromResultSet(rs, "ethereumvalue");
		//return null;
	}

	private List<Double[]> buildCurrencyTrendsFromResultSet(ResultSet resultSet, String currency)
			throws CryptxException {
		List<Double[]> currencyTrends = new ArrayList<Double[]>();
		long timestamp;
		try {
			while (resultSet.next()) {
				Double[] currencyTrend = new Double[2];
				timestamp = resultSet.getBigDecimal("timestmp").longValue();
				currencyTrend[0] = (double) timestamp * 1000;
				currencyTrend[1] = resultSet.getDouble(currency);
				currencyTrends.add(currencyTrend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return currencyTrends;
	}

}
