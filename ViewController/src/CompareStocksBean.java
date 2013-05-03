import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;

import java.util.Random;

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
import com.proxy.ssdi.types.ServiceInput;
import com.proxy.ssdi.types.ServiceOutput;
import com.proxy.SoaServiceClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CompareStocksBean {
    //string of stocks
    public List<String> listOfStocks;
    
    //list of ids
    public List<Number> selectedStockIDs;
    public String stockSelected;
    public Integer numberOfDays;
    private Boolean showLineGraph;
    public int startDate, endDate;
    public List<Integer> dateList, finalDateList;
    public double XBar, YBar, Beta0, Beta1;
    public Map<Number, Object> globalMap;
    public Map<Number, String> stocksMap;
    
    //All the default values go to the constructor
    public CompareStocksBean() {
        if(listOfStocks == null || listOfStocks.size() == 0) {
            listOfStocks = new ArrayList<String>();
            selectedStockIDs = new ArrayList<Number>();
        }
        showLineGraph = Boolean.FALSE;
        dateList = new ArrayList<Integer>();
        finalDateList = new ArrayList<Integer>();
        globalMap = new HashMap<Number, Object>();
        stocksMap = new HashMap<Number, String>();
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
            
            calFinal.add(Calendar.DAY_OF_MONTH, 1);
            
            Date sdFinal = new Date();
            sdFinal = calFinal.getTime();
            Number secondDateFinal = new Number(Integer.parseInt(dateFormat.format(sdFinal)));
            finalDateList.add(secondDateFinal.intValue());
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
    
    public void compareStocks(ActionEvent actionEvent) {
        listOfStocks.add(getStockSelected());
        Number stID = getStockID(getStockSelected());
        selectedStockIDs.add(stID);
    }
    public List<Object[]> getTabularData() {
       List<Object[]> tabularData = new ArrayList<Object []>();
         
       //time, stock id, price  
       int counter = 0;
       Random ran = new Random();
       Collections.sort(dateList);
       Collections.sort(finalDateList);
       
       while (counter < numberOfDays) {
           for (Number id: selectedStockIDs) {
               Integer currentDate = finalDateList.get(counter);
               String loc = stocksMap.get(id);
               List <Double> priceList = (List<Double>)globalMap.get(id);
               Double tempBeta0 = priceList.get(2);
               Double tempBeta1 = priceList.get(3);
               Double price = tempBeta0 + (ran.nextInt(numberOfDays) * 15 * tempBeta1);

               tabularData.add(new Object[]{currentDate, loc, price});    
           }
           counter += 1;
         }
        //displayLineGraph
        return tabularData;
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
        String stockName = (String)operationBinding.getResult();

        return stockName;
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
        finalDateList = new ArrayList<Integer>();
        setFirstAndSecondDate(5);
        listOfStocks.add(getStockSelected());
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }
    public void updateGlobalMap() {
        for (String stName: listOfStocks) {
            SoaServiceClient client = new SoaServiceClient();
            List <Double>  localList = new ArrayList<Double>();
            Number currentStockID = getStockID(stName);
            selectedStockIDs.add(currentStockID);
            stocksMap.put(currentStockID, stName);
            ServiceOutput sout = client.invokeWebService(currentStockID.bigDecimalValue(), startDate, endDate);
            System.out.println("In the CompareStocksBeanClass");
            localList.add(sout.getOutputXBar());
            localList.add(sout.getOutputYBar());
            localList.add(sout.getOutputBeta0());
            localList.add(sout.getOutputBeta1());
            System.out.println(localList.get(0) + "\t" + localList.get(1) + "\t" + localList.get(2) + "\t" + localList.get(3));
            globalMap.put(currentStockID, localList);
        }        
    }
    public void setStockSelected(String stockSelected) {
        this.stockSelected = stockSelected;
    }

    public String getStockSelected() {
        return stockSelected;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }


    public void setShowLineGraph(Boolean showLineGraph) {
        this.showLineGraph = showLineGraph;
    }

    public Boolean getShowLineGraph() {
        return showLineGraph;
    }

    public void updateTenDaysLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        dateList = new ArrayList<Integer>();
        finalDateList = new ArrayList<Integer>();
        setFirstAndSecondDate(10);
        listOfStocks.add(getStockSelected());
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }

    public void updateFifteenDaysLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        dateList = new ArrayList<Integer>();
        finalDateList = new ArrayList<Integer>();
        setFirstAndSecondDate(15);
        listOfStocks.add(getStockSelected());
        updateGlobalMap();
        setShowLineGraph(Boolean.TRUE);
    }
}
