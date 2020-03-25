package ch.dappen.vc.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class GitConcrete implements IGit {

    Git git;

    public GitConcrete(String path) throws GitAPIException {
        git = Git.init().setDirectory( new File(path)).call();
    }

    @Override
    public void init(String path) throws GitAPIException {
        if (git == null){
            git = Git.init().setDirectory( new File(path)).call();
        }
    }

    @Override
    public void open(String path) {

    }

    @Override
    public void add(File... files) throws GitAPIException {
        for (File f : files){
            git.add().addFilepattern(f.getName()).call();
        }
    }

    @Override
    public void commit(String message) throws GitAPIException {
        git.commit().setMessage(message).call();
    }

    @Override
    public void createBranch(String branchName) throws GitAPIException {
        git.branchCreate().setName(branchName).call();
    }

    @Override
    public void checkout(String branchName) throws GitAPIException {
        git.checkout().setName(branchName).call();
    }

    @Override
    public void close() {
        git.close();
    }
}
