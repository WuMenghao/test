package com.brillilab.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EasyExcelBean<T> {

    private List<T> objects=new ArrayList<>();
    private Class entityClazz=null;

    private EasyExcelBean() {
    }

    public static <T> EasyExcelBean<T> getInstance(){
        return new EasyExcelBean<T>();
    }

    public List<T> read(Class entityClazz,Sheet sheet) {

        Assert.notNull(entityClazz,"entityClazz can not be null");
        Assert.notNull(sheet,"sheet can not be null");

        this.entityClazz=entityClazz;

        try (InputStream in=new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xlsx")) {

            ExcelReader excelReader=new ExcelReader(in,ExcelTypeEnum.XLSX,null,new ExcelListener());
            excelReader.read(sheet);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    class ExcelListener extends AnalysisEventListener {

        Map<String, Method> getMethods=new HashMap<>();
        Map<String, Method> setMethods=new HashMap<>();

        @Override
        public void invoke(Object object,AnalysisContext analysisContext) {
            try {

                Object instans=null;
                List colum=(ArrayList) object;
                instans=entityClazz.newInstance();

                Map<String, Method> gets=getGetMethods(entityClazz);
                Map<String, Method> sets=getSetMethods(entityClazz);
                Field[] fields=entityClazz.getDeclaredFields();

                for (int i=0; i < fields.length; i++) {
                    String name="set" + fields[i].getName();
                    String type=fields[i].getType().getSimpleName();

                    ExcelProperty ep=fields[i].getAnnotation(ExcelProperty.class);
                    if(ep == null) continue;

                    Method method=sets.get(name);
                    Object cell=getCell(colum,type,ep);

                    method.invoke(instans,cell);
                }
                objects.add((T)instans);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        private Object getCell(List colum,String type,ExcelProperty ep) {
            Object cell=colum.get(ep.index());
            switch (type){
                case "Integer": cell =(cell !=null ? Integer.valueOf((String) cell ): null ); break;
                case "Long": cell = (cell !=null ? Long.valueOf((String) cell ): null );break;
                case "String":cell = (cell!=null ? (String)cell : null) ;break;
            }
            return cell;
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }

        private Map<String, Method> getSetMethods(Class clazz) {
            List<Method> methods=Arrays.asList(clazz.getMethods());
            methods.forEach(e -> {
                if(e.getName().startsWith("set")){
                    getMethods.put(e.getName().toLowerCase(),e);
                }
            });
            return getMethods;
        }

        private Map<String, Method> getGetMethods(Class clazz) {
            List<Method> methods=Arrays.asList(clazz.getMethods());
            methods.forEach(e -> {
                if(e.getName().startsWith("get")){
                    setMethods.put(e.getName().toLowerCase(),e);
                }
            });
            return setMethods;
        }

    }

}
