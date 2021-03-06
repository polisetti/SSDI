package com.test.model.view;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Mar 23 22:42:22 EDT 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class StockDetailsROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Stockid {
            public Object get(StockDetailsROVORowImpl obj) {
                return obj.getStockid();
            }

            public void put(StockDetailsROVORowImpl obj, Object value) {
                obj.setStockid((Number)value);
            }
        }
        ,
        Stockname {
            public Object get(StockDetailsROVORowImpl obj) {
                return obj.getStockname();
            }

            public void put(StockDetailsROVORowImpl obj, Object value) {
                obj.setStockname((String)value);
            }
        }
        ,
        Stocksymbol {
            public Object get(StockDetailsROVORowImpl obj) {
                return obj.getStocksymbol();
            }

            public void put(StockDetailsROVORowImpl obj, Object value) {
                obj.setStocksymbol((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(StockDetailsROVORowImpl object);

        public abstract void put(StockDetailsROVORowImpl object,
                                 Object value);

        public int index() {
            return StockDetailsROVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return StockDetailsROVORowImpl.AttributesEnum.firstIndex() + StockDetailsROVORowImpl.AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = StockDetailsROVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int STOCKID = StockDetailsROVORowImpl.AttributesEnum.Stockid.index();
    public static final int STOCKNAME = StockDetailsROVORowImpl.AttributesEnum.Stockname.index();
    public static final int STOCKSYMBOL = StockDetailsROVORowImpl.AttributesEnum.Stocksymbol.index();

    /**
     * This is the default constructor (do not remove).
     */
    public StockDetailsROVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Stockid.
     * @return the Stockid
     */
    public Number getStockid() {
        return (Number) getAttributeInternal(STOCKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Stockid.
     * @param value value to set the  Stockid
     */
    public void setStockid(Number value) {
        setAttributeInternal(STOCKID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Stockname.
     * @return the Stockname
     */
    public String getStockname() {
        return (String) getAttributeInternal(STOCKNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Stockname.
     * @param value value to set the  Stockname
     */
    public void setStockname(String value) {
        setAttributeInternal(STOCKNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Stocksymbol.
     * @return the Stocksymbol
     */
    public String getStocksymbol() {
        return (String) getAttributeInternal(STOCKSYMBOL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Stocksymbol.
     * @param value value to set the  Stocksymbol
     */
    public void setStocksymbol(String value) {
        setAttributeInternal(STOCKSYMBOL, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= StockDetailsROVORowImpl.AttributesEnum.firstIndex()) && (index < StockDetailsROVORowImpl.AttributesEnum.count())) {
            return StockDetailsROVORowImpl.AttributesEnum.staticValues()[index - StockDetailsROVORowImpl.AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= StockDetailsROVORowImpl.AttributesEnum.firstIndex()) && (index < StockDetailsROVORowImpl.AttributesEnum.count())) {
            StockDetailsROVORowImpl.AttributesEnum.staticValues()[index - StockDetailsROVORowImpl.AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
