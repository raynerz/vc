package ch.dappen.vc;

import ch.dappen.vc.domain.GitConcrete;
import ch.dappen.vc.domain.IGit;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GitConcreteTest {
    @Test
    void InitializingGitRepoTest() throws GitAPIException, IOException {
        String path = "/tmp/repo1/";

        IGit git = new GitConcrete(path);

        File md = new File(path + "helloWorld.md");
        FileWriter fileWriter = new FileWriter(md);
        fileWriter.write("Hello World");
        fileWriter.close();

        git.add(md);
        git.commit("new commit");

        assert(true);
    }
}
