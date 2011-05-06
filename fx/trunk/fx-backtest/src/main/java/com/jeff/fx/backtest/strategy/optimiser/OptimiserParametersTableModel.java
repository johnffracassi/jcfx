package com.jeff.fx.backtest.strategy.optimiser;

import com.jeff.fx.backtest.AppCtx;
import com.jeff.fx.backtest.strategy.coder.Description;
import com.jeff.fx.backtest.strategy.coder.Optimiser;
import com.jeff.fx.backtest.strategy.coder.Parameter;
import com.jeff.fx.backtest.strategy.optimiser.param.OptimiserParamFactory;
import com.jeff.fx.backtest.strategy.optimiser.param.OptimiserParameter;
import com.jeff.fx.backtest.strategy.time.TimeStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OptimiserParametersTableModel extends DefaultTableModel
{
    private OptimiserParamFactory optimiserParamFactory;

    private List<OptimiserParameter<?>> params = new ArrayList<OptimiserParameter<?>>();

	public OptimiserParametersTableModel()
    {
        optimiserParamFactory = AppCtx.getBean(OptimiserParamFactory.class);
    }

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int arg0) {
		switch(arg0) {
			case 0: return "Key";
			case 1: return "Start";
			case 2: return "End";
			case 3: return "Step";
			case 4: return "Steps";
			default: return "XXX";
		}
	}

	public int getRowCount() {
		if(params == null) {
			return 0;
		}
		
		return params.size() + 1;
	}

	public Object getValueAt(int row, int col) {
		
		if(row == params.size()) {
			if(col == 4) {
				int perms = 1;
				for(OptimiserParameter<?> param : params) {
					perms *= param.getStepCount();
				}
				return perms;
			} else if(col == 0) {
				return "  Permutations";
			} else {
				return "";
			}
		}

        OptimiserParameter param = params.get(row);

		switch(col) {
			case 0: return param.getLabel();
			case 1: return param.fromDouble(param.getStart());
			case 2: return param.fromDouble(param.getEnd());
			case 3: return param.getStep();
			case 4: return param.getStepCount();
			default: return "XXX";
	}	}

	public boolean isCellEditable(int row, int col) {
		return (col != 0 && col != 4 && row != params.size());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValueAt(Object val, int row, int col) {
		
		if(col == 0 || col == 4 || row == params.size()) {
			return;
		}
		
		OptimiserParameter param = params.get(row);
		
		if(col == 1) {
			param.setStart(String.valueOf(val));
		} else if (col == 2) {
			param.setEnd(String.valueOf(val));
		} else if (col == 3) {
			param.setStep(Double.valueOf(String.valueOf(val)));
		}
		
		fireTableCellUpdated(row, 4);
		fireTableCellUpdated(params.size(), 4);
	}

	public List<OptimiserParameter<?>> getParameters()
    {
		return params;
	}

    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
        OptimiserParametersTableModel app = (OptimiserParametersTableModel)ctx.getBean("optimiserParametersTableModel");
        AppCtx.initialise(ctx);
        app.setStrategyClass(TimeStrategy.class);
    }

    public void setStrategyClass(Class<?> strategyClass)
    {
        params.clear();

        for (Field field : strategyClass.getDeclaredFields())
        {
            if(field.getAnnotation(Parameter.class) != null)
            {
                try
                {
                    OptimiserParameter param = generateParamFromField(field);
                    params.add(param);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        fireTableDataChanged();
    }

    private OptimiserParameter generateParamFromField(Field field) throws Exception
    {
        Class<?> fieldType = field.getType();
        Class<?> paramType = getParamClass(fieldType);
        
        OptimiserParameter param = (OptimiserParameter)paramType.newInstance();
        param.setKey(field.getName());
        param.setLabel(field.getAnnotation(Parameter.class).value());

        if(field.getAnnotation(Description.class) != null)
        {
            param.setDescription(field.getAnnotation(Description.class).value());
        }

        if(field.getAnnotation(Optimiser.class) != null)
        {
            Optimiser optimiser = field.getAnnotation(Optimiser.class);
            param.setStart(optimiser.min());
            param.setMin(optimiser.min());
            param.setEnd(optimiser.max());
            param.setMax(optimiser.max());
            param.setStep(optimiser.step());
        }

        return param;
    }

    private Class<? extends OptimiserParameter> getParamClass(Class<?> type)
    {
        return optimiserParamFactory.getParamClassFor(type);
    }
}
