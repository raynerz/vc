package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.technical.IGit;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Route(value = "upload", layout = MainLayout.class)
@PageTitle("Upload Files")
public class UploadFilesView extends Composite<VerticalLayout> implements HasComponents {

	public UploadFilesView() {
		add(new H2("Upload Files to your repository"));

		IGit git = (IGit) VaadinSession.getCurrent().getAttribute("repository");



		MultiFileMemoryBuffer multiFileBuffer = new MultiFileMemoryBuffer();
		Upload upload = new Upload(multiFileBuffer);

		upload.addSucceededListener( event -> {

			Notification.show(event.getFileName()+" "+ event.getMIMEType());


			InputStream stream = multiFileBuffer.getInputStream(event.getFileName());

			File targetFile = new File(git.getPath() + "/" + event.getFileName());

			try {
				FileUtils.copyInputStreamToFile(stream, targetFile);




			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				git.add(targetFile);
			} catch (GitAPIException e) {
				e.printStackTrace();
			}


		});

		add(upload);



	}
}
