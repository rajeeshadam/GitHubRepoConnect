
package com.rajeesh.githubrepo.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajeesh.githubrepo.R;
import com.rajeesh.githubrepo.mvp.model.RepoFIle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class GitRepoDetailAdapter extends RecyclerView.Adapter<GitRepoDetailAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<RepoFIle> mRepoFile = new ArrayList<>();

    public GitRepoDetailAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_file_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mRepoFile.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepoFile.size();
    }

    public void addRepo(List<RepoFIle> RepoFile) {
        mRepoFile.addAll(RepoFile);
        notifyDataSetChanged();
    }

    public void clearRepo() {
        mRepoFile.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder  {

        @Bind(R.id.textview_filename) protected TextView FileName;

        private Context mContext;
        private RepoFIle mRepo;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(RepoFIle repo) {
            mRepo = repo;
            FileName.setText(repo.getFilename());


        }


    }


}
