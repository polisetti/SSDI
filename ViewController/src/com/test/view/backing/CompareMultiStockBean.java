package com.test.view.backing;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import java.util.ListIterator;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class CompareMultiStockBean {
    public String stockSelected;
    public List<String> listOfStocks;
    public List<Integer> dateList;
    public int startDate, endDate;
    private Boolean showLineGraph;
    public Integer numberOfDays;
    public Map<Number, Double> resultMap;
    public Map<Number, Object> globalMap;
    public CompareMultiStockBean() {
        if(listOfStocks == null || listOfStocks.size() == 0) {
            listOfStocks = new ArrayList<String>();
        }
        showLineGraph = Boolean.FALSE;
        globalMap = new HashMap<Number, Object>();
    }
    public List<Object[]> getUpdatedTabularData() {
        List<Object[]> tabularDataNew = new ArrayList<Object[]>();
        List<String> Locations = new ArrayList<String>();
        Collections.sort(dateList);
        List<Number> stocksList = new ArrayList<Number>();
        for (String stockName: listOfStocks) {
            stocksList.add(getStockID(stockName));
        }
        Collections.sort(stocksList);

        int counter = 0;
        while (counter < numberOfDays) {
           for (Number loc : stocksList) {
                try {
                    Integer currentDate = dateList.get(counter);
                    Map<Number, Double> x = (Map<Number, Double>)globalMap.get(loc);
                    Double price;
                    
                    price = x.get(new Number(dateList.get(counter)));
                    System.out.println(price);
                    tabularDataNew.add(new Object[] { currentDate, getStockName(loc), price });
                    //tabularDataNew.add(new Object[] { dateList.get(counter), getStockName(loc), Double.valueOf(ran.nextInt(40) + 25) });
                } 
                catch (SQLException e) {
                }
           }
           counter += 1;
        }
        //displayLineGraph
        return tabularDataNew;
    }
    public String getStockName(Number sid) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding =
            binding.getOperationBinding("getStockName");

        // Set the Input parameters to the operation bindings as below
        operationBinding.getParamsMap().put("pStockID", sid);

        // Invoke the Application module method
        operationBinding.execute();

        // Get the result from operation bindings
        Object obj = operationBinding.getResult();
        String stockName = (String)operationBinding.getResult();

        return stockName;
    }
    public void updateSelectedList() {
        listOfStocks.add(stockSelected);
    }
    public Number getStockID(String stockName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding =
            binding.getOperationBinding("getStockID");

        // Set the Input parameters to the operation bindings as below
        operationBinding.getParamsMap().put("pStockName", stockName);

        // Invoke the Application module method
        operationBinding.execute();

        // Get the result from operation bindings
        Number stockID = (Number)operationBinding.getResult();

        return stockID;
    }
    public void updateLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        dateList = new ArrayList<Integer>();
        updateSelectedList();
        setFirstAndSecondDate(5);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }
    public void updateTenDaysLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        dateList = new ArrayList<Integer>();
        updateSelectedList();
        setFirstAndSecondDate(10);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }

    public void updateFifteenDaysLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        dateList = new ArrayList<Integer>();
        updateSelectedList();
        setFirstAndSecondDate(15);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);

    }

    public void updateGlobalMap() {
        //for each stock call the AM method and get the data here
        for (String stockName : listOfStocks) {
            Number id = getStockID(stockName);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Application app = facesContext.getApplication();
            ExpressionFactory elFactory = app.getExpressionFactory();
            ELContext elContext = facesContext.getELContext();
            ValueExpression valueExp =
                elFactory.createValueExpression(elContext, "#{bindings}",
                                                Object.class);
            BindingContainer binding =
                (BindingContainer)valueExp.getValue(elContext);

            OperationBinding operationBinding =
                binding.getOperationBinding("getThePastPrices");

            // Set the Input parameters to the operation bindings as below
            operationBinding.getParamsMap().put("stockID", id);
            operationBinding.getParamsMap().put("stDate", startDate);
            operationBinding.getParamsMap().put("enDate", endDate);

            // Invoke the Application module method
            operationBinding.execute();

            // Get the result from operation bindings
            resultMap = (Map<Number, Double>)operationBinding.getResult();
            globalMap.put(id, resultMap);
            
        }
    }
    public void setFirstAndSecondDate(int delta) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        Date fd = new Date();
        Calendar cal = new GregorianCalendar();
        Calendar calFinal = new GregorianCalendar();
        
        cal.setTime(fd);
        calFinal.setTime(fd);
        
        Number firstDate = new Number(Integer.parseInt(dateFormat.format(fd)));
        Number secondDate = new Number();
        dateList = new ArrayList<Integer>();
        dateList.add(firstDate.intValue());
        System.out.println(firstDate);
        
        for (int count=0; count<delta; count++){
            cal.add(Calendar.DAY_OF_MONTH, -1);
            
            Date sd = new Date();
            sd = cal.getTime();
            secondDate = new Number(Integer.parseInt(dateFormat.format(sd)));
            dateList.add(secondDate.intValue());
            System.out.println(secondDate);            
        }
        
        startDate = secondDate.intValue();
        endDate = firstDate.intValue();
    }
    public List autoComplete(FacesContext facesContext,
                             AutoSuggestUIHints autoSuggestUIHints) {
        String subValue = autoSuggestUIHints.getSubmittedValue();
        subValue = subValue.toLowerCase();
        
        facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
          elFactory.createValueExpression(elContext, "#{bindings}", Object.class);
        BindingContainer binding= (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding=binding.getOperationBinding("autoComplete");
        // Set the Input parameters to the operation bindings as below
           operationBinding.getParamsMap().put("inputValue", subValue);  
        // Invoke the Application module method
        operationBinding.execute();
        // Get the result from operation bindings
        List<String> obj = (List<String>)operationBinding.getResult();
        ListIterator<String> litr = obj.listIterator();
        List <SelectItem> cont = new ArrayList<SelectItem>();

        while (litr.hasNext()) {
          String element = litr.next();
          cont.add(new SelectItem(element));
        }
        return cont;
    }
    public void setShowLineGraph(Boolean showLineGraph) {
        this.showLineGraph = showLineGraph;
    }

    public Boolean getShowLineGraph() {
        return showLineGraph;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }
    public void setStockSelected(String stockSelected) {
        this.stockSelected = stockSelected;
    }

    public String getStockSelected() {
        return stockSelected;
    }
}
