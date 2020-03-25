package ch.dappen.vc.domain;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public interface IGit {

    void init(String path) throws GitAPIException;

    void open(String path);

    void add(File... files) throws GitAPIException;

    void commit(String message) throws GitAPIException;

    void createBranch(String branchName) throws GitAPIException;

    void checkout(String branchName) throws GitAPIException;

    void close();
}
