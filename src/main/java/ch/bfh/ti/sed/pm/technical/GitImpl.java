package ch.bfh.ti.sed.pm.technical;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.util.List;

public class GitImpl implements IGit {

    Git git;
    String path;

    public GitImpl(String path) throws GitAPIException {
        git = Git.init().setDirectory( new File(path)).call();
        this.path = path;
    }

    @Override
    public void init(String path) throws GitAPIException {
        if (git == null){
            git = Git.init().setDirectory( new File(path)).call();
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void open(String path) {

    }

    @Override
    public void add(File... files) throws GitAPIException {
        for (File f : files){
            git.add().addFilepattern(f.getName()).call();
        }
        commit("test");
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
    public List<Ref> listBranches() throws GitAPIException {
        return git.branchList().call();
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