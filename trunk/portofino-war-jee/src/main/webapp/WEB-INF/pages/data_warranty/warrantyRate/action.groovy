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
import com.manydesigns.portofino.pageactions.chart.ChartAction4ItsProject
import com.manydesigns.portofino.security.*
import com.manydesigns.portofino.shiro.*
@RequiresPermissions(level = AccessLevel.VIEW)
class MyChartAction extends ChartAction4ItsProject {
    //Automatically generated
	//Write your code here
}