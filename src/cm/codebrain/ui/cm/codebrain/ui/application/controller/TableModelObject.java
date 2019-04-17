/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author KSA-INET
 */
public class TableModelObject {

    private List<HashMap> listModelMap;
    private final HashMap formDatas;
    private Object key;

    public TableModelObject(HashMap formDatas) {
        this.formDatas = formDatas;
        listModelMap = new ArrayList<>();
    }

    public List<HashMap> getModel() {
        return listModelMap;
    }

    public void setListModelMap(List<HashMap> listModelMap) {
        this.listModelMap = listModelMap;
    }

    public void setModel(Object... objects) {
        HashMap modelMap = new HashMap();
        
        for (Object obj : objects) {
            if (obj.getClass().equals(JTextField.class)) {
                formDatas.keySet().stream().map((ky) -> {
                        this.key = ky;
                        return ky;
                    }).filter((ky) -> (formDatas.get(ky).equals(obj))).map((ky) -> (JTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
                        if(this.key.toString().endsWith("Id")){
                            String entity = this.key.toString().substring(0, (this.key.toString().length() - 2));
                            modelMap.put(this.key, FormParameters.get(getUpperValue(entity)));
                        }else{
                            modelMap.put(this.key, ((JTextField) val).getText());
                        }
                    });
            }else if(obj.getClass().equals(HashMap.class)){
                modelMap.putAll((HashMap) obj);
            }
        }
        
            if(!modelMap.isEmpty())
                listModelMap.add(modelMap);

//            if (obj.getClass().equals(JTextField.class)) {
//                String name = ((JTextField) obj).getName();
//                if (!name.isEmpty()) {
//                    formDatas.keySet().stream().map((ky) -> {
//                        this.key = ky;
//                        return ky;
//                    }).filter((ky) -> (formDatas.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) formDatas.get(ky)).forEachOrdered((Object val) -> {
//                        ((JTextPane) formDatas.get(this.key.toString())).setText("");
//                    });
//                    modelMap.put(name, ((JTextField) obj).getText());
//                }
//            } else if (obj.getClass().equals(HashMap.class)) {
//                    modelMap = (HashMap) obj;
//            } else if (obj.getClass().equals(String.class)) {
//                modelMap.put(Value, obj.toString());
//            } else if (obj.getClass().equals(List.class)) {
//                List listTmp = (List) obj;
//                modelMap = new HashMap();
//                for (Object model : listTmp) {
//                    
//                    if (model.getClass().equals(HashMap.class)) {
//                        modelMap = (HashMap) model;
//                    } else if (model.getClass().equals(String.class)) {
//                        modelMap.put(Value, model.toString());
//                    } else {
//                        /**
//                         * To Do
//                         */
//                    }
//                    
//                    listModelMap.add(modelMap);
//                    modelMap = new HashMap();
//                }
//            } else {
//                /**
//                 * To Do
//                 */
//            }
//            if(!modelMap.isEmpty())
//                listModelMap.add(modelMap);
//        }
    }
    
	public String getUpperValue(String value) {
		return value.substring(0, 1).toUpperCase()
				+ value.substring(1, value.length());
	}
}
