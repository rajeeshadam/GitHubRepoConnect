package com.rajeesh.githubrepo.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class RepoDetails implements Serializable {

    private List<RepoFIle> files;
    private CommitDetails commit;



    public List<RepoFIle> getFiles() {
        return files;
    }

    public void setFiles(List<RepoFIle> files) {
        this.files = files;
    }

    public CommitDetails getCommit() {
        return commit;
    }

    public void setCommit(CommitDetails commit) {
        this.commit = commit;
    }
}
