package ch.bfh.ti.sed.pm.technical;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.util.List;

public interface IGit {

    void init(String path) throws GitAPIException;

    void open(String path);

    void add(File... files) throws GitAPIException;

    void commit(String message) throws GitAPIException;

    void createBranch(String branchName) throws GitAPIException;

    List<Ref> listBranches() throws GitAPIException;

    void checkout(String branchName) throws GitAPIException;

    void close();

    String getPath();
}