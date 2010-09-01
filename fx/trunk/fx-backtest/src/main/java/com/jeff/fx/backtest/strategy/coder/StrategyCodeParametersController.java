package com.jeff.fx.backtest.strategy.coder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.jeff.fx.common.CandleValueModel;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.TimeOfWeek;

public class StrategyCodeParametersController {

	private StrategyCodeParametersView view;
	private StrategyCodeParametersModel model;
	private List<StrategyParametersListener> listeners = new ArrayList<StrategyParametersListener>();

	public static void main(String[] args) {
		StrategyCodeParametersController scpc = new StrategyCodeParametersController();
		JFrame frame = new JFrame();
		frame.setContentPane(scpc.getView());
		frame.setSize(350, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public StrategyCodeParametersController() {
		
		model = new StrategyCodeParametersModel();
		view = new StrategyCodeParametersView();
	
		view.getTable().setModel(model);
		view.getTable().getColumnModel().getColumn(0).setPreferredWidth(110);
		view.getTable().getColumnModel().getColumn(1).setPreferredWidth(220);
		view.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		model.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				System.out.println(e);
				if(e.getType() == TableModelEvent.UPDATE) {
					StrategyParam param = model.getParam(e.getFirstRow());
					for(StrategyParametersListener listener : listeners) {
						listener.parameterUpdated(param);
					}
				}
			}
		});
		
		view.getBtnNew().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StrategyParam param = new StrategyParam("param" + (1 + model.getRowCount()), String.class);
				model.add(param);
				for(StrategyParametersListener listener : listeners) {
					listener.parameterAdded(param);
				}
			}
		});
		
		view.getBtnDelete().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx = view.getTable().getSelectedRow();
				if(idx >= 0) {
					view.getBtnDelete().setEnabled(false);
					StrategyParam param = model.delete(idx);
					for(StrategyParametersListener listener : listeners) {
						listener.parameterRemoved(param);
					}
				}
			}
		});
		
		view.getTable().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(view.getTable().getSelectedRow() >= 0) {
					view.getBtnDelete().setEnabled(true);
				}
			}
		});
		
		view.getTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new ClassSelectionComboModel())));
		
	}

	public List<StrategyParam> getParams() {
		return model.getParams();
	}
	
	public StrategyCodeParametersView getView() {
		return view;
	}

	public void setParams(List<StrategyParam> parameters) {
		model.setParams(parameters);
		for(StrategyParametersListener listener : listeners) {
			listener.reset();
			for(StrategyParam param : parameters) {
				listener.parameterAdded(param);
			}
		}
	}

	public void addListener(StrategyParametersListener listener) {
		listeners.add(listener);
	}
}

class ClassSelectionComboModel extends KeyValueComboBoxModel<Class<?>> {

	private static final long serialVersionUID = -6837385173080792752L;
	
	public ClassSelectionComboModel() {
		addElement(String.class);
		addElement(Integer.class);
		addElement(Double.class);
		addElement(Float.class);
		addElement(LocalDate.class);
		addElement(LocalTime.class);
		addElement(LocalDateTime.class);
		addElement(TimeOfWeek.class);
		addElement(CandleValueModel.class);
		addElement(Instrument.class);
		addElement(FXDataSource.class);
	}
	
	public KeyValueLabeler<Class<?>> getLabeler() {
		return new KeyValueLabeler<Class<?>>() {
			public String generateLabel(Class<?> obj) {
				return obj.getSimpleName();
			}
		};
	}
}

class KeyValueComboBoxModel<T> extends DefaultComboBoxModel {

	private static final long serialVersionUID = 7127403223704763260L;
	private KeyValueLabeler<T> labeler;
	
	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return ((KeyValue)super.getSelectedItem()).getValue();
	}
	
	@SuppressWarnings("unchecked")
	public void addElement(Object obj) {
		super.addElement(new KeyValue(generateLabel((T)obj), (T)obj));
	}
	
	public KeyValueLabeler<T> getLabeler() {
		labeler = new KeyValueLabeler<T>() {
			public String generateLabel(T obj) {
				return String.valueOf(obj);
			}
		};
		return labeler;
	}
	
	public String generateLabel(T obj) {
		if(labeler == null) {
			labeler = getLabeler();
		}
		return labeler.generateLabel(obj);
	}
	

	class KeyValue {
		
		private String label;
		private T value;
		
		public KeyValue(String label, T value) {
			this.label = label;
			this.value = value;
		}
		
		public String getLabel() {
			return label;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}
		
		public T getValue() {
			return value;
		}
		
		public void setValue(T value) {
			this.value = value;
		}
		
		public String toString() {
			return getLabel();
		}
	}
}

interface KeyValueLabeler<T> {
	String generateLabel(T obj);
}

