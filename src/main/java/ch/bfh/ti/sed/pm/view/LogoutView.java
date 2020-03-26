package ch.bfh.ti.sed.pm.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("logout")
public class LogoutView extends Div implements BeforeEnterObserver {

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		String username = (String) VaadinSession.getCurrent()
												.getAttribute("userLoggedIn");
		/**
		 * Logs out the user in the backend
		 */
		IInitializer.getInstance()
					.retrieveLoginHandler()
					.logout(username);
		VaadinSession.getCurrent()
					 .getSession()
					 .invalidate();
		UI.getCurrent()
		  .getPage()
		  .executeJavaScript("window.location.href=''");
	}
}
