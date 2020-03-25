package ch.dappen.vc;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JGitTest {
    @Test
    void creatingARepoTest() throws IOException {

        // The Git-object has a static method to initialize a new repository
        String path = "/tmp/new_repo/";
        try{

            // Creating Git Repo
            Git git = Git.init().setDirectory( new File(path)).call();

            // Creating an example file

            File md = new File(path + "helloWorld.md");
            FileWriter fileWriter = new FileWriter(md);
            fileWriter.write("Hello World");
            fileWriter.close();

            // Adding and commiting the changes

            git.add().addFilepattern(md.getName()).call();
            git.commit().setMessage("My First Java Commit").call();

            // Branching out
            String branch = "v1";
            git.branchCreate().setName(branch).call();

            git.checkout().setName(branch).call();

            // Adding changes in v1
            FileWriter fileWriter2 = new FileWriter(md, true);
            fileWriter2.write("\n I was set in v1");
            fileWriter2.close();

            // Commiting v1
            git.add().addFilepattern(md.getName()).call();
            git.commit().setMessage("My Second Java Commit in V1").call();

            git.close();
            
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        //FileUtils.deleteDirectory(new File(path));
    }

}