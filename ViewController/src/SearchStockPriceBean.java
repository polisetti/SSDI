import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;

import java.util.Map;

import java.util.Random;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class SearchStockPriceBean {
    private RichInputText inputTextMethod;
    private Boolean showLineGraph;
    public String stockSelected;
    public Map<Number, Double> globalMap;
    public int startDate, endDate;

    public SearchStockPriceBean() {
        showLineGraph = Boolean.FALSE;
        globalMap = new HashMap<Number, Double>();
    }


    public List autoComplete(FacesContext facesContext,
                             AutoSuggestUIHints autoSuggestUIHints) {
        // This is the backing bean autocomplete
        // AMImpl autocomplete method is invoked here
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
    public void setFirstAndSecondDate(int delta) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        Date fd = new Date();
        Date sd = new Date();

        Calendar cal = new GregorianCalendar();
        cal.setTime(fd);
        
        Number firstDate = new Number(Integer.parseInt(dateFormat.format(fd)));      
        cal.add(Calendar.DAY_OF_MONTH, -delta);
        sd = cal.getTime();
        Number secondDate = new Number(Integer.parseInt(dateFormat.format(sd)));

        startDate = secondDate.intValue();
        endDate = firstDate.intValue();
        System.out.println(startDate);
        System.out.println(endDate);
    }
    public List<Object[]> getTabularData() {
        List<Object[]> tabularData = new ArrayList<Object[]>();

          List<Number> timesList = new ArrayList<Number>();
          for (Iterator<Number> it = globalMap.keySet().iterator(); it.hasNext(); ) {
            timesList.add(it.next());
          }

        Collections.sort(timesList);
        
          for(Number time : timesList) {
                  tabularData.add(new Object[] { time, stockSelected, globalMap.get(time) });
              }

          return tabularData;
    }
    public void updateLineGraph(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println(stockSelected);
        Number stID = getStockID(stockSelected);
        System.out.println(stID);
        setFirstAndSecondDate(0);
        updateGlobalMap(stID);
        setShowLineGraph(Boolean.TRUE);
    }
    public void updateGlobalMap(Number stockID) {
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
            binding.getOperationBinding("getTodayPrice");

        // Set the Input parameters to the operation bindings as below
        operationBinding.getParamsMap().put("stockID", stockID);
        operationBinding.getParamsMap().put("stDate", startDate);
        operationBinding.getParamsMap().put("enDate", endDate);

        // Invoke the Application module method
        operationBinding.execute();

        // Get the result from operation bindings
        globalMap = (Map<Number, Double>)operationBinding.getResult();
    }

    public void setShowLineGraph(Boolean showLineGraph) {
        this.showLineGraph = showLineGraph;
    }

    public Boolean getShowLineGraph() {
        return showLineGraph;
    }

    public void setStockSelected(String stockSelected) {
        this.stockSelected = stockSelected;
    }

    public String getStockSelected() {
        return stockSelected;
    }


}
