package com.jeff.fx.gui.beanform;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.indicator.Label;
import com.jeff.fx.indicator.Property;
import com.jeff.fx.indicator.ValueListener;
import com.jeff.fx.indicator.overlay.SimpleMovingAverage;

import net.miginfocom.swing.MigLayout;

public class BeanForm extends JPanel
{

    private static final long serialVersionUID = 770458164263389928L;

    private Object bean;

    public static void main(String[] args)
    {
        BeanForm bt = new BeanForm();
        bt.buildForm(new SimpleMovingAverage(14, CandleValueModel.BuyOpen));

        JFrame jf = new JFrame();
        jf.setContentPane(bt);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(400, 250));
        jf.setVisible(true);
    }

    public Object getBean()
    {
        return bean;
    }

    public void buildForm(final Object bean)
    {

        this.bean = bean;

        Field[] fields = bean.getClass().getDeclaredFields();
        int row = 1;
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(Property.class))
            {
                try
                {
                    String label = null;
                    String fieldName = field.getName();
                    String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    Method getterMethod = bean.getClass().getMethod(getterName);
                    final Method setterMethod = bean.getClass().getMethod(setterName, field.getType());
                    Object fieldValue = getterMethod.invoke(bean);

                    if (field.isAnnotationPresent(Label.class))
                    {
                        label = field.getAnnotation(Label.class).value();
                    }
                    else
                    {
                        label = fieldName;
                    }

                    BeanPropertyEditor editor = BeanPropertyEditorFactory.build(field.getType(), label, fieldValue);
                    add(editor.getLabelComponent(), String.format("cell %d %d,alignx left", 0, row));
                    add(editor.getValueComponent(), String.format("cell %d %d,alignx left", 1, row));
                    row++;

                    editor.setListener(new ValueListener() {
                        public void valueChanged(Object newValue)
                        {
                            try
                            {
                                setterMethod.invoke(BeanForm.this.bean, newValue);
                                System.out.println("new value = " + BeanForm.this.bean + " / " + newValue);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public BeanForm(Object bean)
    {
        this();
        buildForm(bean);
    }

    public BeanForm()
    {
        setLayout(new MigLayout("", "[][grow]", "[]"));
    }
}
