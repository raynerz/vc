package ch.bfh.ti.sed.pm.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import ch.bfh.ti.sed.pm.domain.Factory;
import ch.bfh.ti.sed.pm.domain.EMFactoryImpl;

public class MainLayout extends Composite<VerticalLayout> implements HasComponents, RouterLayout, BeforeEnterObserver {
	private Div childWrapper = new Div();

        public MainLayout() throws Exception {


		initFactory();


		getContent().setSizeFull();

		H1 header = new H1("Version Control System");
		add(header);

		HorizontalLayout mainContent = new HorizontalLayout();
		VerticalLayout menuBar = new VerticalLayout();
		menuBar.setWidth("20%");
		menuBar.add(new RouterLink("Create Version", CreateRepositoryView.class));
		menuBar.add(new RouterLink("Upload Files", UploadFilesView.class));
		//menuBar.add(new RouterLink("Observations", ObservationPeriodView.class));
		//menuBar.add(new RouterLink("Consult Observations", ConsultObservationView.class));
		//menuBar.add(new RouterLink("Patients", PatientView.class));
		//menuBar.add(new RouterLink("Logout", LogoutView.class));


		mainContent.add(menuBar);

		mainContent.add(childWrapper);
		mainContent.setFlexGrow(1, childWrapper);

		add(mainContent);

		H1 footer = new H1("Footer");
		add(footer);

		getContent().setFlexGrow(1, mainContent);
		getContent().setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, header);
		getContent().setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, footer);
		getContent().setHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH, mainContent);
	}


	@Override
	public void showRouterLayoutContent(HasElement content) {
		childWrapper.getElement()
					.appendChild(content.getElement());
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (VaadinSession.getCurrent()
						 .getAttribute("userLoggedIn") == null) {
			VaadinSession.getCurrent()
						 .setAttribute("intendedPath", event.getLocation()
															.getPath());
			event.rerouteTo(LoginView.class);
		}
	}

	public void initFactory() throws Exception {
		if(IInitializer.getInstance().retrieveLoginHandler() == null){
			Factory fac = new EMFactoryImpl();
			IInitializer.getInstance().init(fac);
		}
	}
}
