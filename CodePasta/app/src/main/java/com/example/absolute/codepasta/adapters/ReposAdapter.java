package com.example.absolute.codepasta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absolute.codepasta.R;
import com.example.absolute.codepasta.retrofit.RepositoryData;

import java.util.List;


public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<RepositoryData> data;

    public ReposAdapter(List<RepositoryData> data){
        this.data = data;
    }

    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutItem = R.layout.repository_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutItem, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        RepositoryData repository = data.get(position);
        holder.createdAt.setText(repository.getCreated_at());
        holder.description.setText(repository.getDescription());
        holder.htmlUrl.setText(repository.getHtml_url());
        holder.language.setText(repository.getLanguage());
        holder.name.setText(repository.getName());
        holder.score.setText(String.format("%s", repository.getScore()));
        holder.updatedAt.setText(repository.getUpdated_at());
        holder.watchers.setText(String.format("%d ", repository.getWatchers()));

    }

    @Override
    public int getItemCount() {
        if (data==null) return 0;
        return data.size();
    }

    class ReposViewHolder extends RecyclerView.ViewHolder{

        TextView createdAt, description, htmlUrl, language, name, score, updatedAt, watchers;
        public ReposViewHolder(View itemView) {
            super(itemView);
            createdAt = (TextView) itemView.findViewById(R.id.create_repo_text);
            description = (TextView) itemView.findViewById(R.id.description_text);
            htmlUrl = (TextView) itemView.findViewById(R.id.repo_link_text);
            language = (TextView) itemView.findViewById(R.id.language_text);
            name = (TextView) itemView.findViewById(R.id.repo_name_text);
            score = (TextView) itemView.findViewById(R.id.score_text);
            updatedAt = (TextView) itemView.findViewById(R.id.update_text);
            watchers = (TextView) itemView.findViewById(R.id.watchers_text);
        }
    }
}
