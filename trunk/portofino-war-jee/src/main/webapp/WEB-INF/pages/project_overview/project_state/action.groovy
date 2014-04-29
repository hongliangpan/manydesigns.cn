import java.text.MessageFormat

import javax.servlet.*

import net.sourceforge.stripes.action.*

import org.apache.shiro.*
import org.hibernate.*
import org.hibernate.criterion.*

import com.manydesigns.elements.messages.*
import com.manydesigns.elements.reflection.*
import com.manydesigns.portofino.*
import com.manydesigns.portofino.application.*
import com.manydesigns.portofino.buttons.*
import com.manydesigns.portofino.buttons.annotations.*
import com.manydesigns.portofino.dispatcher.*
import com.manydesigns.portofino.model.database.*
import com.manydesigns.portofino.pageactions.*
import com.manydesigns.portofino.pageactions.chart.ChartAction
import com.manydesigns.portofino.security.*
import com.manydesigns.portofino.shiro.*
import com.riil.itsboard.utils.GroovyUtils

@RequiresPermissions(level = AccessLevel.VIEW)
class MyChartAction extends ChartAction {

	protected String sql = "select s.c_name state,s.c_name state,count(0) from t_project p " +
	"INNER JOIN t_dict_state s ON p.c_state=s.c_id " +
	"INNER JOIN t_customer c ON p.c_customer_id=c.c_id " +
	" WHERE {0} " +
	"GROUP BY s.c_id ORDER BY s.c_sort_id";
	
	@DefaultHandler
	public Resolution execute() {

		if (this.chartConfiguration!=null) {
			String query = GroovyUtils.getQuerySql4RegionParam(sql,context);
			this.chartConfiguration.setQuery(query);
			//this.chartConfiguration.setLegend(" ");
		}
		super.execute();
	}
}