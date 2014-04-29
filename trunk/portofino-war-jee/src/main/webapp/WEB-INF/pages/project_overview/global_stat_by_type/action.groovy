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

	protected String sql =
	"SELECT NAME n1, NAME n2, c FROM (" +
	"SELECT '客户' AS NAME, count(0) AS c FROM t_customer t WHERE 1=1  AND {0} UNION " +
	"SELECT '售后' AS NAME, count(0) AS c FROM t_customer t WHERE t.c_state='售后' AND {0} UNION " +
	"SELECT '有意愿' AS NAME, count(0) AS c FROM t_customer t WHERE t.c_client_aspiration='有意愿' AND {0} UNION " +
	"SELECT '使用较好' AS NAME, count(0) AS c FROM t_customer t WHERE t.c_is_good=1 AND {0} UNION " +
	"SELECT '样板' AS NAME, count(0) AS c FROM t_customer t WHERE t.c_is_template=1 AND {0} UNION " +
	"SELECT '精品样板' AS NAME, count(0) AS c FROM t_customer t WHERE t.c_is_template_boutique=1 AND {0}" +
	") tt";

	@DefaultHandler
	public Resolution execute() {
		
		if (this.chartConfiguration!=null) {
			String query = GroovyUtils.getQuerySql4RegionParam(sql,context);
			this.chartConfiguration.setQuery(query);
			this.chartConfiguration.setLegend(" ");
		}
		super.execute();
	}
}