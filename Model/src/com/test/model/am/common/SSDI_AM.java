package com.test.model.am.common;

import java.util.List;

import java.util.Map;

import oracle.jbo.ApplicationModule;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 22 11:37:18 EDT 2013
// ---------------------------------------------------------------------
public interface SSDI_AM extends ApplicationModule {
    void deleteStocksFromPortfolio(int stockID, int portfolioID);

    void deletePortfolio(int portfolioID);

    void compareStocks(int stockID1, int stockID2);


    void initialScreen(int portfolioID);

    void initialScreen();


    void displayGraph(String pStockName);

    Number getStockID(String pStockName);

    void addStocksToPortfolio(Number stockID);

    String getStockName(Number pStockID);

    Map<Number, Double> getTodayPrice(Number stockID, Number stDate,
                                      Number enDate);
}
