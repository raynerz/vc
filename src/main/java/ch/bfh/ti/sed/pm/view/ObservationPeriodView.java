package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.domain.entities.Device;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;
import java.util.Set;

@Route(value = "Observations", layout = MainLayout.class)
//@RouteAlias(value="", layout = MainLayout.class)
@PageTitle("Observation Periods")
public class ObservationPeriodView extends Composite<VerticalLayout> implements HasComponents, BeforeEnterObserver {

	public ObservationPeriodView() {
		add(new H2("Observation Periods"));
		FormLayout addPatientForm = new FormLayout();
		//Setting up the date
		LocalDate now = LocalDate.now();
		DatePicker datePicker = new DatePicker("Start Date");
		datePicker.setMin(now);
		datePicker.setMax(now);
		NumberField patientIdField = new NumberField("Patient's Insurance Number", "Enter 911 for testing");
		NumberField deviceIdField = new NumberField("DeviceID Number", "Enter 1 for testing");
		NumberField frequencyField = new NumberField("Frequency", "Period/Frequency in seconds Enter 1 for testing");

		addPatientForm.add(datePicker, patientIdField, deviceIdField, frequencyField);

		//Button Bar
		HorizontalLayout actions = new HorizontalLayout();

		//Add Patient Button
		Button createOPBtn = new Button("Create Observation Period");
		createOPBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		actions.add(createOPBtn);
		addPatientForm.add(actions);
		addPatientForm.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2),
				new FormLayout.ResponsiveStep("40em", 3),
				new FormLayout.ResponsiveStep("40em", 4));

		Grid<Device> devicesListGrid = new Grid<Device>();

		//Setting the binders
		String username = (String) VaadinSession.getCurrent()
												.getAttribute("userLoggedIn");
		Set<Device> deviceSet = IInitializer.getInstance()
											.retrieveDoctor(username)
											.getDevices();

		//Set the data provider to automatically update the list whenever something changes
		DataProvider dataProvider = DataProvider.ofCollection(deviceSet);
		devicesListGrid.setDataProvider(dataProvider);

		devicesListGrid.addColumn(Device::getDevice_id)
					   .setHeader("Devices DB");
		devicesListGrid.addColumn(Device::isAvailable)
					   .setHeader("Availability");
		devicesListGrid.addColumn(Device::getPatient)
					   .setHeader("Patient");
		devicesListGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
										 GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

		FormLayout endObsForm = new FormLayout();
		//Setting the fields
		NumberField patientIdField2 = new NumberField("Patient's Insurance Number", "Enter 911 for testing");
		NumberField deviceIdField2 = new NumberField("DeviceID Number", "Enter 1 for testing");

		endObsForm.add(patientIdField2, deviceIdField2);

		//Button Bar
		HorizontalLayout actions2 = new HorizontalLayout();

		//Add End of Observation Button
		Button endOPBtn = new Button("End Observation Period");
		endOPBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		actions2.add(endOPBtn);
		endObsForm.add(actions2);
		endObsForm.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2));

		createOPBtn.addClickListener(click -> {
			try {
				IInitializer.getInstance()
							.createObservationPeriodHandler(username);
			} catch (IllegalArgumentException e) {
				Notification.show("Doctor is not on the database", 3000, Notification.Position.TOP_CENTER);
			}
			LocalDate startDate = datePicker.getValue();
			int patientId = (int) Math.round(patientIdField.getValue());
			int deviceId = (int) Math.round(deviceIdField.getValue());
			long frequency = Double.valueOf(frequencyField.getValue())
								   .longValue();

			try {
				IInitializer.getInstance()
							.retrieveObservationPeriodHandler()
							.startObservationPeriod(username, patientId, deviceId, startDate, frequency);
				Notification.show("Observation Started", 3000, Notification.Position.TOP_CENTER);
			} catch (NullPointerException e) {
				Notification.show("Patient or device not found", 3000, Notification.Position.TOP_CENTER);
			} catch (IllegalArgumentException e) {
				Notification.show("There has been an error", 3000, Notification.Position.TOP_CENTER);
			}

		});

		add(addPatientForm);

		endOPBtn.addClickListener(click -> {
			try {
				IInitializer.getInstance()
						.retrieveObservationPeriodHandler();
			} catch (IllegalArgumentException e) {
				Notification.show("Doctor is not on the database", 3000, Notification.Position.TOP_CENTER);
			}
			int patientId2 = (int) Math.round(patientIdField2.getValue());
			int deviceId2 = (int) Math.round(deviceIdField2.getValue());

			try {
				IInitializer.getInstance()
						.retrieveObservationPeriodHandler()
						.endObservationPeriod(username, patientId2, deviceId2);
				Notification.show("Observation Ended", 3000, Notification.Position.TOP_CENTER);
			} catch (NullPointerException e) {
				Notification.show("Patient or device not found", 3000, Notification.Position.TOP_CENTER);
			} catch (IllegalArgumentException e) {
				Notification.show("There has been an error", 3000, Notification.Position.TOP_CENTER);
			}

		});
		add(endObsForm, devicesListGrid);
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
