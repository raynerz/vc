package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.domain.entities.ObservationPeriod;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

@Route(value = "consultobservations", layout = MainLayout.class)
@PageTitle("Consult Observations")
public class ConsultObservationView extends Composite<VerticalLayout> implements HasComponents, BeforeEnterObserver {

	public ConsultObservationView() {
		FormLayout searchDeviceForm = new FormLayout();
		NumberField deviceIDField = new NumberField("Device ID", "45251158");

		searchDeviceForm.add(deviceIDField);

		//Button Bar
		HorizontalLayout actions = new HorizontalLayout();

		//Add Device Button
		Button searchDevice = new Button("Search Device");
		searchDevice.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		actions.add(searchDevice);
		searchDeviceForm.add(actions);
		searchDeviceForm.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2),
				new FormLayout.ResponsiveStep("40em", 3));

		add(searchDeviceForm);

		// Device grid from https://vaadin.com/docs/v14/flow/components/tutorial-flow-grid.html
		Grid<ObservationPeriod> obsGrid = new Grid<>(ObservationPeriod.class);


		//Setting the binders
		String username = (String) VaadinSession.getCurrent()
												.getAttribute("userLoggedIn");
		List<ObservationPeriod> obsPeriods = IInitializer.getInstance()
														 .retrieveDoctor(username)
														 .retrieveObservationPeriodList();


		//Set the data provider to automatically update the list whenever something changes
		ListDataProvider<ObservationPeriod> dataProvider = DataProvider.ofCollection(obsPeriods);
		obsGrid.setDataProvider(dataProvider);

		obsGrid.setItems(obsPeriods);
		obsGrid.addColumn("device.measurements")
			   .setHeader("Measurements");
		obsGrid.addColumn(ObservationPeriod::getStartDate)
			   .setHeader("Start Date");
		obsGrid.addColumn(ObservationPeriod::getFrequencyDouble)
			   .setHeader("Frequency");
		obsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
								 GridVariant.LUMO_NO_ROW_BORDERS,
								 GridVariant.LUMO_ROW_STRIPES);
		obsGrid.setHeight("1000px");
		obsGrid.setWidth("900px");


		searchDevice.addClickListener(click -> {
			// Basic validation, on a later stage we need to make this more solid
			int deviceID = (int) Math.round(deviceIDField.getValue());


			try {
				// Retrieves the Device ID that is handling the measurements
				IInitializer.getInstance()
							.retrieveObservationPeriodHandler()
							.consultObservationPeriod(username, deviceID);
				//Notify the data provider
				Notification.show("Measurements retrieved", 3000, Notification.Position.TOP_CENTER);
			} catch (IllegalArgumentException e) {
				Notification.show("Device DOES not exist", 3000, Notification.Position.TOP_CENTER);
			} catch (InterruptedException ie) {
				Notification.show("Measurements could not be retrieved", 3000, Notification.Position.MIDDLE);
			}

			obsGrid.getDataProvider()
				   .refreshAll();
			add(obsGrid);
		});


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

}
