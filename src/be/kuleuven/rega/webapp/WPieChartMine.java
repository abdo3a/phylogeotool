package be.kuleuven.rega.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WStandardItemModel;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WVBoxLayout;
import eu.webtoolkit.jwt.WWidget;
import eu.webtoolkit.jwt.chart.LabelOption;
import eu.webtoolkit.jwt.chart.WPieChart;
import eu.webtoolkit.jwt.utils.EnumUtils;

public class WPieChartMine {

	private WPieChart chart = null;
	private WHBoxLayout whBoxLayout = null;
	private WVBoxLayout wvBoxLayout = null;
	private WWidget legend0 = null;
	private WWidget legend1 = null;
	private WWidget legend2 = null;
	private WWidget legend3 = null;
	private WWidget legend4 = null;
	private WWidget legend5 = null;
	private WWidget legend6 = null;
	
	public WPieChartMine(Map<String,Integer> map, WContainerWidget parent) {
		whBoxLayout = new WHBoxLayout();
		wvBoxLayout = new WVBoxLayout();
		chart = new WPieChart(parent);
		setData(map);
		chart.setMargin(new WLength(10), EnumSet.of(Side.Top, Side.Left));
		whBoxLayout.addWidget(chart);
		whBoxLayout.addLayout(wvBoxLayout);
	}
	
	public WPieChartMine(Map<String,Integer> map) {
		whBoxLayout = new WHBoxLayout();
		wvBoxLayout = new WVBoxLayout();
		chart = new WPieChart();
		setData(map);
		chart.setMargin(new WLength(10), EnumSet.of(Side.Top, Side.Left));
		whBoxLayout.addWidget(chart);
		whBoxLayout.addLayout(wvBoxLayout);
	}
	
	public WPieChart getWPieChart() {
		return this.chart;
	}
	
	public WHBoxLayout getWidget() {
		//widget.setLayout(whBoxLayout);
		return this.whBoxLayout;
	}
	
	public void setData(Map<String,Integer> map) {
		WStandardItemModel model = new WStandardItemModel();
		model.insertColumns(model.getColumnCount(), 2);
		model.insertRows(model.getRowCount(), 7);
		model.setHeaderData(0, new WString("Label"));
		model.setHeaderData(1, new WString("Values"));
		int row = 0;
		int totalValues = 0;
		int totalValuesSet = 0;

		if(map != null) {
			Map<String,Integer> sorted_map = sortMap(map);
			for(int value: map.values()) {
				 totalValues += value;
			}
			for(String label:sorted_map.keySet()) {
				model.setData(row, 0, new WString(label));
				model.setData(row, 1, sorted_map.get(label));
				totalValuesSet += sorted_map.get(label);
				if(++row > 5) {
					model.setData(row, 0, new WString("Other"));
					model.setData(row, 1, totalValues - totalValuesSet);
					break;
				}
			}
		}
		chart.setModel(model);
		chart.setLabelsColumn(0);
		chart.setDataColumn(1);
		if(legend0 != null) {
			wvBoxLayout.removeWidget(legend0);
		}
		if(legend1 != null) {
			wvBoxLayout.removeWidget(legend1);
		}
		if(legend2 != null) {
			wvBoxLayout.removeWidget(legend2);
		}
		if(legend3 != null) {
			wvBoxLayout.removeWidget(legend3);
		}
		if(legend4 != null) {
			wvBoxLayout.removeWidget(legend4);
		}
		if(legend5 != null) {
			wvBoxLayout.removeWidget(legend5);
		}
		if(legend6 != null) {
			wvBoxLayout.removeWidget(legend6);
		}
		
		int legendWidth = 150;
		if(map != null && map.keySet().size() > 0) {
			legend0 = chart.createLegendItemWidget(0, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
			legend0.setWidth(new WLength(legendWidth));
			wvBoxLayout.addWidget(legend0);
			if(map.keySet().size() > 1) {
				legend1 = chart.createLegendItemWidget(1, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
				legend1.setWidth(new WLength(legendWidth));
				wvBoxLayout.addWidget(legend1);
				if(map.keySet().size() > 2) {
					legend2 = chart.createLegendItemWidget(2, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
					legend2.setWidth(new WLength(legendWidth));
					wvBoxLayout.addWidget(legend2);
					if(map.keySet().size() > 3) {
						legend3 = chart.createLegendItemWidget(3, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
						legend3.setWidth(new WLength(legendWidth));
						wvBoxLayout.addWidget(legend3);
						if(map.keySet().size() > 4) {
							legend4 = chart.createLegendItemWidget(4, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
							legend4.setWidth(new WLength(legendWidth));
							wvBoxLayout.addWidget(legend4);
							if(map.keySet().size() > 5) {
								legend5 = chart.createLegendItemWidget(5, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
								legend5.setWidth(new WLength(legendWidth));
								wvBoxLayout.addWidget(legend5);
								if(map.keySet().size() > 6) {
									legend6 = chart.createLegendItemWidget(6, EnumUtils.or(EnumSet.of(LabelOption.Outside, LabelOption.TextLabel), LabelOption.TextPercentage));
									legend6.setWidth(new WLength(legendWidth));
									wvBoxLayout.addWidget(legend6);
								}
							}
						}
					}
				}
			}
		}
	}

	public <K, V extends Comparable<? super V>> Map<K, V> sortMap(final Map<K, V> mapToSort) {
		List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(mapToSort.size());
 
		entries.addAll(mapToSort.entrySet());
 
		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(final Map.Entry<K, V> entry1, final Map.Entry<K, V> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
 
		Collections.reverse(entries);
		
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public WWidget getLegend0() {
		return legend0;
	}

	public WWidget getLegend1() {
		return legend1;
	}

	public WWidget getLegend2() {
		return legend2;
	}

	public WWidget getLegend3() {
		return legend3;
	}

	public WWidget getLegend4() {
		return legend4;
	}

	public WWidget getLegend5() {
		return legend5;
	}

	public WWidget getLegend6() {
		return legend6;
	}
}
