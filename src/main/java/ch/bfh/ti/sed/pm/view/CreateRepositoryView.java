package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.technical.GitImpl;
import ch.bfh.ti.sed.pm.technical.IGit;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import org.eclipse.jgit.api.errors.GitAPIException;

@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("VCS")
public class CreateRepositoryView extends Composite<VerticalLayout> implements HasComponents {

	public CreateRepositoryView() {
		add(new H2("Add a version"));

		FormLayout repoForm = new FormLayout();

		TextField name = new TextField("New Repo", "name");
		TextField newBranch = new TextField("New branch", "branch");


		repoForm.add(name, newBranch);



		HorizontalLayout actions = new HorizontalLayout();

		//Add Patient Button
		Button createBtn = new Button("Create Repo");
		createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		actions.add(createBtn);
		repoForm.add(actions);
		repoForm.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2),
				new FormLayout.ResponsiveStep("40em", 3));

		Button createBranchBtn = new Button("Create branch");
		createBranchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		actions.add(createBranchBtn);







		add(repoForm);

		createBtn.addClickListener(click -> {
			try {
				IGit git = new GitImpl("/tmp/" + name.getValue().trim());


				VaadinSession.getCurrent().setAttribute("repository", git);


			} catch (GitAPIException e) {
				e.printStackTrace();

				Notification.show("There has been an error creating the repository", 3000, Notification.Position.TOP_CENTER);
			}

			});

		createBranchBtn.addClickListener(click ->{

			IGit git = (IGit) VaadinSession.getCurrent().getAttribute("repository");

			try {
				git.createBranch(newBranch.getValue().trim());

			} catch (GitAPIException e) {
				e.printStackTrace();
				Notification.show("There has been an error creating the repository", 3000, Notification.Position.TOP_CENTER);
			}

		});


	}

}
