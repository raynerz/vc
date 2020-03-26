package ch.bfh.ti.sed.pm.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Optional;

@Route("login")
public class LoginView extends VerticalLayout {

	public LoginView() {
		/**
		 * Layouts and fields
		 */
		VerticalLayout loginLayout = new VerticalLayout();
		TextField loginField = new TextField();
		loginField.setPlaceholder("email");
		PasswordField pwdField = new PasswordField();
		pwdField.setPlaceholder("password");
		/**
		 * Buttons
		 */
		Button loginBtn = new Button("Login");
		loginBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		loginBtn.addClickShortcut(Key.ENTER);
		loginBtn.addClickListener(click -> {
			String username = loginField.getValue()
										.trim();
			String password = pwdField.getValue()
									  .trim();
			boolean logged = true;
			try {
				IInitializer.getInstance()
							.retrieveLoginHandler()
							.login(username, password);
			} catch (IllegalArgumentException e) {
				// Tell the user he uses a wrong username or password and then exit the statement
				Notification.show("Incorrect username or password", 3000, Notification.Position.TOP_CENTER);
				logged = false;
			}

			if (logged) {
				VaadinSession.getCurrent()
							 .setAttribute("userLoggedIn", username);
				Object intendedPath = VaadinSession.getCurrent()
												   .getAttribute("intendedPath");
				UI.getCurrent()
				  .navigate(Optional.ofNullable(intendedPath)
									.map(Object::toString)
									.orElse(""));

			}
		});

		H1 title = new H1("Version Control");

		loginLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
													title,
													loginField,
													pwdField,
													loginBtn);
		loginLayout.add(title);
		loginLayout.add(loginField);
		loginLayout.add(pwdField);
		loginLayout.add(loginBtn);

		add(loginLayout);

	}
}
