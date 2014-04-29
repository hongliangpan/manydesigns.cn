package net.sf.dynamicreports.report.builder.column;

import net.sf.dynamicreports.report.base.column.DRColumn;
import net.sf.dynamicreports.report.base.component.DRComponent;
import net.sf.dynamicreports.report.builder.AbstractBuilder;
import net.sf.dynamicreports.report.builder.expression.Expressions;
import net.sf.dynamicreports.report.builder.grid.ColumnGridComponentBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
import net.sf.dynamicreports.report.constant.ComponentDimensionType;
import net.sf.dynamicreports.report.constant.Constants;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.definition.expression.DRIPropertyExpression;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
@SuppressWarnings("unchecked")
public abstract class ColumnBuilder<T extends ColumnBuilder<T, U>, U extends DRColumn<?>> extends AbstractBuilder<T, U>
		implements ColumnGridComponentBuilder {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
	// hongliangpan add
	private String fieldName;

	protected ColumnBuilder(U column) {
		super(column);
	}

	/**
	 * Sets the column title.
	 * 
	 * @param titleExpression the title expression
	 * @return a column builder
	 */
	public T setTitle(DRIExpression<?> titleExpression) {
		getObject().setTitleExpression(titleExpression);
		return (T) this;
	}

	/**
	 * Sets the column title.
	 * 
	 * @param title the title
	 * @return a column builder
	 */
	public T setTitle(String title) {
		getObject().setTitleExpression(Expressions.text(title));
		return (T) this;
	}

	/**
	 * Sets the column title style.
	 * 
	 * @param titleStyle the title style
	 * @return a column builder
	 */
	public T setTitleStyle(ReportStyleBuilder titleStyle) {
		if (titleStyle != null) {
			getObject().setTitleStyle(titleStyle.getStyle());
		} else {
			getObject().setTitleStyle(null);
		}
		return (T) this;
	}

	/**
	 * Sets the column value style.
	 * 
	 * @param style the value style
	 * @return a column builder
	 */
	public T setStyle(ReportStyleBuilder style) {
		if (style != null) {
			getComponent().setStyle(style.getStyle());
		} else {
			getComponent().setStyle(null);
		}
		return (T) this;
	}

	/**
	 * Sets the print when expression. The expression must be a type of Boolean and it decides whether or not a column
	 * value will be printed.
	 * 
	 * @param printWhenExpression the print expression
	 * @return a column builder
	 */
	public T setPrintWhenExpression(DRIExpression<Boolean> printWhenExpression) {
		getComponent().setPrintWhenExpression(printWhenExpression);
		return (T) this;
	}

	/**
	 * This method is used to define the preferred height of a column title. The height is set to the <code>rows</code>
	 * multiplied by height of the font
	 * 
	 * @param rows the number of preferred rows >= 0
	 * @exception IllegalArgumentException if <code>rows</code> is < 0
	 * @return a column builder
	 */
	public T setTitleRows(Integer rows) {
		getObject().setTitleRows(rows);
		return (T) this;
	}

	/**
	 * This method is used to define the fixed height of a column title. The height is set to the <code>rows</code>
	 * multiplied by height of the font
	 * 
	 * @param rows the number of fixed rows >= 0
	 * @exception IllegalArgumentException if <code>rows</code> is < 0
	 * @return a column builder
	 */
	public T setTitleFixedRows(Integer rows) {
		getObject().setTitleRows(rows);
		getObject().setTitleHeightType(ComponentDimensionType.FIXED);
		return (T) this;
	}

	/**
	 * This method is used to define the minimum height of a column title. The height is set to the <code>rows</code>
	 * multiplied by height of the font
	 * 
	 * @param rows the number of minimum rows >= 0
	 * @exception IllegalArgumentException if <code>rows</code> is < 0
	 * @return a column builder
	 */
	public T setTitleMinRows(Integer rows) {
		getObject().setTitleRows(rows);
		getObject().setTitleHeightType(ComponentDimensionType.EXPAND);
		return (T) this;
	}

	/**
	 * Sets the preferred height of a column title.
	 * 
	 * @see net.sf.dynamicreports.report.builder.Units
	 * @param height the column title preferred height >= 0
	 * @exception IllegalArgumentException if <code>height</code> is < 0
	 * @return a column builder
	 */
	public T setTitleHeight(Integer height) {
		getObject().setTitleHeight(height);
		return (T) this;
	}

	/**
	 * Sets the fixed height of a column title.
	 * 
	 * @see net.sf.dynamicreports.report.builder.Units
	 * @param height the column title fixed height >= 0
	 * @exception IllegalArgumentException if <code>height</code> is < 0
	 * @return a column builder
	 */
	public T setTitleFixedHeight(Integer height) {
		getObject().setTitleHeight(height);
		getObject().setTitleHeightType(ComponentDimensionType.FIXED);
		return (T) this;
	}

	/**
	 * Sets the minimum height of a column title.
	 * 
	 * @see net.sf.dynamicreports.report.builder.Units
	 * @param height the column title minimum height >= 0
	 * @exception IllegalArgumentException if <code>height</code> is < 0
	 * @return a column builder
	 */
	public T setTitleMinHeight(Integer height) {
		getObject().setTitleHeight(height);
		getObject().setTitleHeightType(ComponentDimensionType.EXPAND);
		return (T) this;
	}

	public T setTitleStretchWithOverflow(Boolean stretchWithOverflow) {
		getObject().setTitleStretchWithOverflow(stretchWithOverflow);
		return (T) this;
	}

	/**
	 * Adds a jasper property to the column title.
	 * 
	 * @param propertyExpression the property expression
	 * @return a column builder
	 */
	public T addTitleProperty(DRIPropertyExpression propertyExpression) {
		getObject().addTitlePropertyExpression(propertyExpression);
		return (T) this;
	}

	/**
	 * Adds a jasper property to the column title.
	 * 
	 * @param name the property name
	 * @param valueExpression the property value expression
	 * @return a column builder
	 */
	public T addTitleProperty(String name, DRIExpression<String> valueExpression) {
		getObject().addTitlePropertyExpression(Expressions.property(name, valueExpression));
		return (T) this;
	}

	/**
	 * Adds a jasper property to the column title.
	 * 
	 * @param name the property name
	 * @param value the property value
	 * @return a column builder
	 */
	public T addTitleProperty(String name, String value) {
		getObject().addTitlePropertyExpression(Expressions.property(name, value));
		return (T) this;
	}

	protected DRComponent getComponent() {
		return (DRComponent) getObject().getComponent();
	}

	public U getColumn() {
		return build();
	}

	/**
	 * @return fieldName - {return content description}
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName - {parameter description}.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
