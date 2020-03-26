package ch.bfh.ti.sed.pm.technical;


import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GitImplTest {
    @Test
    void InitializingGitRepoTest() throws GitAPIException, IOException {
        String path = "/tmp/repo1/";

        IGit git = new GitImpl(path);

        File md = new File(path + "helloWorld.md");
        FileWriter fileWriter = new FileWriter(md);
        fileWriter.write("Hello World");
        fileWriter.close();

        git.add(md);
        git.commit("new commit");

        FileUtils.deleteDirectory(new File(path));

        assert(true);
    }
}