
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import java.util.ListIterator;

import java.util.Random;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.model.RowKeySet;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class PortfolioBakingBean {
    private RichTable pfTable;
    public String stockName;
    public List<String> stocksSelected;
    public List<Number> stocksList;
    private Boolean showLineGraph;
    private RichCommandToolbarButton compareStocksButton;
    public Integer numberOfDays;
    public int startDate, endDate;
    public Map<Number, Double> resultMap;
    public List<Integer> dateList;
    public Map<Number, Object> globalMap;

    public PortfolioBakingBean() {
        showLineGraph = Boolean.FALSE;
        stocksSelected = new ArrayList<String>();
        stocksList = new ArrayList<Number>();
        dateList = new ArrayList<Integer>();
        globalMap = new HashMap<Number, Object>();
    }

    public void compareStocks(ActionEvent actionEvent) {
        //set first and second date
        setFirstAndSecondDate(5);
        
        //Get the binding
        RowKeySet selectedEmps = getPfTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        //get the binding iterator
        DCIteratorBinding empIter =
            bindings.findIteratorBinding("PortfolioStocksVOIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
        stocksSelected = new ArrayList<String>();
        //iterate through all the selected rows in the table

        while (selectedEmpIter.hasNext()) {
            Key key = (Key)((List)selectedEmpIter.next()).get(0);
            Row currentRow = empRSIter.getRow(key);
            Number sid = (Number)currentRow.getAttribute("Stockid");
            String stockName = getStockName(sid);
            
            stocksSelected.add(stockName);
        }
        setShowLineGraph(Boolean.TRUE);
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
    public List<Object[]> getTabularData() {
        List<Object[]> tabularData = new ArrayList<Object[]>();

          //time, stock id, price
          int time = 900;
          Random ran = new Random();
          List<String> Locations = new ArrayList<String>();

          //Add the selected stocks to the line graph
          for (String s : stocksSelected) {
              Locations.add(s);
          }

          while (time < 1500) {
              for (String loc : Locations) {
                  tabularData.add(new Object[] { time, loc, Double.valueOf(ran.nextInt(40) + 25) });
              }
              time += 100;
          }
          //displayLineGraph
          return tabularData;
    }
    public List<Object[]> getUpdatedTabularData() {
        List<Object[]> tabularDataNew = new ArrayList<Object[]>();
        List<String> Locations = new ArrayList<String>();
        Collections.sort(dateList);

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

    public void updateSelectedList() {
        stocksList = new ArrayList<Number>();
        RowKeySet selectedEmps = getPfTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        //get the binding iterator
        DCIteratorBinding empIter =
            bindings.findIteratorBinding("PortfolioStocksVOIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
        stocksSelected = new ArrayList<String>();
        //iterate through all the selected rows in the table
        //TODO Bring the while loop code outside
        while (selectedEmpIter.hasNext()) {
            Key key = (Key)((List)selectedEmpIter.next()).get(0);
            Row currentRow = empRSIter.getRow(key);
            Number sid = (Number)currentRow.getAttribute("Stockid");
            stocksList.add(sid);
            //System.out.println(sid);
        }
    }
    public void setFirstAndSecondDate(int delta) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        Date fd = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(fd);
        
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
        
        System.out.println(startDate);
        System.out.println(endDate);
    }
    public void updateLineGraph(ActionEvent actionEvent) {
        //Fivedays method
        updateSelectedList();
        setFirstAndSecondDate(5);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }
    public void updateLineGraphTenDays(ActionEvent actionEvent) {
        // Add event code here...
        updateSelectedList();
        setFirstAndSecondDate(10);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }
    public void updateLineGraphFifteenDays(ActionEvent actionEvent) {
        // Add event code here...
        updateSelectedList();
        setFirstAndSecondDate(15);
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);

    }
    public void updateGlobalMap() {
        //for each stock call the AM method and get the data here
        for (Number id : stocksList) {
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

    public void deleteStocksFromPortfolio(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deletePortfolio(ActionEvent actionEvent) {
        // Add event code here...
    }

    public List<SelectItem> auto(FacesContext facesContext,
                                 AutoSuggestUIHints autoSuggestUIHints) {
        String subValue = autoSuggestUIHints.getSubmittedValue();
        subValue = subValue.toLowerCase();

        facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding =
            binding.getOperationBinding("autoComplete");
        // Set the Input parameters to the operation bindings as below
        operationBinding.getParamsMap().put("inputValue", subValue);
        // Invoke the Application module method
        operationBinding.execute();
        // Get the result from operation bindings
        List<String> obj = (List<String>)operationBinding.getResult();
        ListIterator<String> litr = obj.listIterator();
        List<SelectItem> cont = new ArrayList<SelectItem>();

        while (litr.hasNext()) {
            String element = litr.next();
            cont.add(new SelectItem(element));
        }
        return cont;
    }

    public void addStocksToPortfolio(ActionEvent actionEvent) {
        // Add event code here...
        /*
         *  Get the stockID from a given stockname
         *  calles the displayGraph method in the AM
         */
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);

        //get the Operation Binding
        OperationBinding operationBinding =
            binding.getOperationBinding("getStockID");

        //set the parameter
        operationBinding.getParamsMap().put("pStockName", stockName);

        //execute and get the StockID
        operationBinding.execute();
        Number stockID = (Number)operationBinding.getResult();

        operationBinding = binding.getOperationBinding("addStocksToPortfolio");
        operationBinding.getParamsMap().put("stockID", stockID);
        operationBinding.execute();

        return;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setPfTable(RichTable pfTable) {
        this.pfTable = pfTable;
    }

    public RichTable getPfTable() {
        return pfTable;
    }

    public void setShowLineGraph(Boolean showLineGraph) {
        this.showLineGraph = showLineGraph;
    }

    public Boolean getIsShowLineGraph() {
        return showLineGraph;
    }

    public void setCompareStocksButton(RichCommandToolbarButton compareStocksButton) {
        this.compareStocksButton = compareStocksButton;
    }

    public RichCommandToolbarButton getCompareStocksButton() {
        return compareStocksButton;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }
}
