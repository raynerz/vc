package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.domain.entities.Patient;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Set;

@Route(value = "patients", layout = MainLayout.class)
//@RouteAlias(value="", layout = MainLayout.class)
@PageTitle("Patients")
public class PatientView extends Composite<VerticalLayout> implements HasComponents, BeforeEnterObserver {


	public PatientView() {

		FormLayout addPatientForm = new FormLayout();
		NumberField insuranceNumberField = new NumberField("Insurance Number", "AHV-Number");
		TextField firstNameField = new TextField("First Name", "John");
		TextField lastNameField = new TextField("Last Name", "Doe");

		addPatientForm.add(insuranceNumberField, firstNameField, lastNameField);

		//Button Bar
		HorizontalLayout actions = new HorizontalLayout();

		//Add Patient Button
		Button addPatientBtn = new Button("Add Patient");
		addPatientBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		actions.add(addPatientBtn);
		addPatientForm.add(actions);
		addPatientForm.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2),
				new FormLayout.ResponsiveStep("40em", 3));


		// Patient grid from https://vaadin.com/docs/v14/flow/components/tutorial-flow-grid.html
		Grid<Patient> patientGrid = new Grid<>();

		//Setting the binders
		String username = (String) VaadinSession.getCurrent()
												.getAttribute("userLoggedIn");
		Set<Patient> patientSet = IInitializer.getInstance()
											  .retrieveDoctor(username)
											  .getPatients();


		//Set the data provider to automatically update the list whenever something changes
		DataProvider dataProvider = DataProvider.ofCollection(patientSet);
		patientGrid.setDataProvider(dataProvider);

		patientGrid.addColumn(Patient::getInsuranceNumber)
				   .setHeader("Insurance Number");
		patientGrid.addColumn(Patient::getName)
				   .setHeader("First Name");
		patientGrid.addColumn(Patient::getSurname)
				   .setHeader("Last Name");

		//Adding all to the view
		add(addPatientForm, patientGrid);

		addPatientBtn.addClickListener(click -> {
			try {
				IInitializer.getInstance()
							.createPatientHandler(username);
			} catch (IllegalArgumentException e) {
				Notification.show("Doctor is not on the database", 3000, Notification.Position.TOP_CENTER);
			}
			// Basic validation, on a later stage we need to make this more solid
			int insuranceNumber = (int) Math.round(insuranceNumberField.getValue());
			String name = firstNameField.getValue()
										.trim();
			String surname = lastNameField.getValue()
										  .trim();

			try {
				IInitializer.getInstance()
							.retrievePatientHandler()
							.addPatient(insuranceNumber, name, surname);
				//Notify the data provider
			} catch (IllegalArgumentException e) {
				Notification.show("Patient already exists", 3000, Notification.Position.TOP_CENTER);
			}

			patientGrid.getDataProvider()
					   .refreshAll();


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
