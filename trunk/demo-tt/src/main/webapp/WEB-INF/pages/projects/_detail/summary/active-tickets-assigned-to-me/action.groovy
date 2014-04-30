import com.manydesigns.elements.ElementsThreadLocals
import com.manydesigns.portofino.di.Inject
import com.manydesigns.portofino.modules.DatabaseModule
import com.manydesigns.portofino.pageactions.custom.CustomAction
import com.manydesigns.portofino.persistence.Persistence
import com.manydesigns.portofino.security.AccessLevel
import com.manydesigns.portofino.security.RequiresPermissions
import net.sourceforge.stripes.action.DefaultHandler
import net.sourceforge.stripes.action.ForwardResolution
import net.sourceforge.stripes.action.Resolution
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.hibernate.Session
import org.hibernate.criterion.Order
import org.hibernate.criterion.Restrictions

@RequiresPermissions(level = AccessLevel.VIEW)
class ProjectsActiveTicketsAssignedToMeAction extends CustomAction {

    @Inject(DatabaseModule.PERSISTENCE)
    private Persistence persistence;

    List tickets;

    @DefaultHandler
    public Resolution execute() {
        Object project = ElementsThreadLocals.getOgnlContext().get("project");

        Session session = persistence.getSession("tt");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            tickets = session.createCriteria("tickets")
                    .add(Restrictions.eq("project", project.id))
                    .add(Restrictions.eq("assignee", subject.getPrincipal().id))
                    .add(Restrictions.ne("state", 4L))
                    .addOrder(Order.asc("n"))
                    .list();
        } else {
            tickets = Collections.EMPTY_LIST;
        }

        return new ForwardResolution("/jsp/common/active-tickets-assigned-to-me.jsp");
    }

}