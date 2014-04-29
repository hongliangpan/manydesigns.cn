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

	protected String sql =
	"SELECT c_x, c_y, c_value, c_labelx, c_labely FROM\n" +
	"(\n" +
	"( SELECT 'm' c_x, d.c_name c_y, COUNT(0) c_value, '部署模块' c_labelx, d.c_name c_labely FROM t_project_module m INNER JOIN t_dict_module d ON d.c_id = m.c_module_id INNER JOIN t_customer c ON m.c_customer_id = c.c_id \n" +
	"WHERE 1 = 1 GROUP BY d.c_id )\n" +
	"		UNION ALL\n" +
	"( SELECT 'h' c_x, d.c_name c_y, COUNT(0) - 5, '热点模块', d.c_name c_module_name FROM t_project_module m INNER JOIN t_dict_module d ON d.c_id = m.c_module_id INNER JOIN t_customer c ON m.c_customer_id = c.c_id \n" +
	"WHERE 1 = 1 GROUP BY d.c_id )\n" +
	") mm\n" +
	"ORDER BY mm.c_x DESC, c_value DESC";

	//	"SELECT m.c_name c_module_name, m.c_name c_module_name, count(0) FROM t_project_module_hot h "+
	//	"INNER JOIN t_dict_module m ON m.c_id = h.c_module_id  "+
	//	"INNER JOIN t_customer c ON h.c_customer_id = c.c_id "+
	//	" WHERE {0} " +
	//	"GROUP BY m.c_id ORDER BY count(0) DESC";

	@DefaultHandler
	public Resolution execute() {

		if (this.chartConfiguration!=null) {
			String query = GroovyUtils.getQuerySql4RegionParam(sql.replaceAll("1 = 1", "1 = 1 AND {0}"),context);
			this.chartConfiguration.setQuery(query);
			this.chartConfiguration.setLegend(" ");
		}
		super.execute();
	}
}