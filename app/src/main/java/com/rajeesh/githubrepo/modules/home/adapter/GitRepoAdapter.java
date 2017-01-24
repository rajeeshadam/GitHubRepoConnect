
package com.rajeesh.githubrepo.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajeesh.githubrepo.R;
import com.rajeesh.githubrepo.mvp.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class GitRepoAdapter extends RecyclerView.Adapter<GitRepoAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Repo> mRepoList = new ArrayList<>();

    public GitRepoAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mRepoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    public void addRepo(List<Repo> Repos) {
        mRepoList.addAll(Repos);
        notifyDataSetChanged();
    }

    public void clearRepo() {
        mRepoList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.textview_title) protected TextView RepoName;
        @Bind(R.id.textview_preview_description) protected TextView Description;

        private Context mContext;
        private Repo mRepo;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Repo repo) {
            mRepo = repo;
            RepoName.setText(repo.getName());
            Description.setText(repo.getDescription());

        }

        @Override
        public void onClick(View v) {
            if (mRepoClickListener != null) {
                mRepoClickListener.onClick(v, mRepo, mRepo.getName(), getAdapterPosition());
            }
        }
    }

    public void setRepoClickListener(OnRepoClickListener listener) {
        mRepoClickListener = listener;
    }

    private OnRepoClickListener mRepoClickListener;

    public interface OnRepoClickListener {

        void onClick(View v, Repo repo,String name, int position);
    }
}
