package com.test.model.am.client;

import com.test.model.am.common.SSDI_AM;

import java.sql.SQLException;

import java.util.List;

import java.util.Map;

import oracle.jbo.client.remote.ApplicationModuleImpl;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 22 11:37:18 EDT 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SSDI_AMClient extends ApplicationModuleImpl implements SSDI_AM {
    /**
     * This is the default constructor (do not remove).
     */
    public SSDI_AMClient() {
    }

    public List<String> autoComplete(String inputValue) {
        Object _ret =
            this.riInvokeExportedMethod(this,"autoComplete",new String [] {"java.lang.String"},new Object[] {inputValue});
        return (List<String>)_ret;
    }


    public void deleteStocksFromPortfolio(int stockID, int portfolioID) {
        Object _ret =
            this.riInvokeExportedMethod(this,"deleteStocksFromPortfolio",new String [] {"int","int"},new Object[] {new Integer(stockID), new Integer(portfolioID)});
        return;
    }

    public void deletePortfolio(int portfolioID) {
        Object _ret =
            this.riInvokeExportedMethod(this,"deletePortfolio",new String [] {"int"},new Object[] {new Integer(portfolioID)});
        return;
    }

    public void compareStocks(int stockID1, int stockID2) {
        Object _ret =
            this.riInvokeExportedMethod(this,"compareStocks",new String [] {"int","int"},new Object[] {new Integer(stockID1), new Integer(stockID2)});
        return;
    }

    public void initialScreen(int portfolioID) {
        Object _ret =
            this.riInvokeExportedMethod(this,"initialScreen",new String [] {"int"},new Object[] {new Integer(portfolioID)});
        return;
    }

    public void initialScreen() {
        Object _ret = this.riInvokeExportedMethod(this,"initialScreen",null,null);
        return;
    }

    public void displayGraph(String pStockName) {
        Object _ret =
            this.riInvokeExportedMethod(this,"displayGraph",new String [] {"java.lang.String"},new Object[] {pStockName});
        return;
    }

    public Number getStockID(String pStockName) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getStockID",new String [] {"java.lang.String"},new Object[] {pStockName});
        return (Number)_ret;
    }

    public void addStocksToPortfolio(Number stockID) {
        Object _ret =
            this.riInvokeExportedMethod(this,"addStocksToPortfolio",new String [] {"oracle.jbo.domain.Number"},new Object[] {stockID});
        return;
    }

    public String getStockName(Number pStockID) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getStockName",new String [] {"oracle.jbo.domain.Number"},new Object[] {pStockID});
        return (String)_ret;
    }

    public Map getThePastPrices(Number stockID, Number stDate, Number enDate) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getThePastPrices",new String [] {"oracle.jbo.domain.Number","oracle.jbo.domain.Number","oracle.jbo.domain.Number"},new Object[] {stockID, stDate, enDate});
        return (Map)_ret;
    }
}
