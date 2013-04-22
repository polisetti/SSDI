import java.util.Iterator;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class MultiSelectBackingBean {
    private RichTable pfTable;

    public MultiSelectBackingBean() {
    }

    public void setPfTable(RichTable pfTable) {
        this.pfTable = pfTable;
    }

    public RichTable getPfTable() {
        return pfTable;
    }

    public void getSelectedRows(ActionEvent actionEvent) {
        // Add event code here...
        RowKeySet selectedEmps = getPfTable().getSelectedRowKeys();    
        Iterator selectedEmpIter = selectedEmps.iterator();
        DCBindingContainer bindings =
                          (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empIter = bindings.findIteratorBinding("PortfolioStocks2Iterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
         while(selectedEmpIter.hasNext()){
           Key key = (Key)((List)selectedEmpIter.next()).get(0);
           Row currentRow = empRSIter.getRow(key);
           System.out.println(currentRow.getAttribute("Stockid"));
         }
         //return null;
    }
}
